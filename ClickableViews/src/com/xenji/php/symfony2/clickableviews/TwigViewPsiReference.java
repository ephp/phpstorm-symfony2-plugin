package com.xenji.php.symfony2.clickableviews;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.ProjectScope;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import com.jetbrains.twig.TwigFile;
import org.jetbrains.annotations.NotNull;

/**
 * Reference implementation for Twig Templates.
 *
 * @author Mario Mueller mario@xenji.com
 * @license MIT
 */
public class TwigViewPsiReference implements PsiReference {

    /**
     * The template string from e.x. a controller
     */
    private StringLiteralExpression templateString;

    /**
     * Project reference, which is needed e.x. for the
     * FilenameIndex.
     */
    private Project project;

    /**
     * Lazy cache for a cleaned string - which means a string without
     * quotations.
     */
    private String cleanString;


    private TextRange textRange;

    /**
     *
     * @param templateString
     * @param project
     */
    public TwigViewPsiReference(final StringLiteralExpression templateString, Project project) {
        this.templateString = templateString;
        this.project = project;
        cleanString = templateString.getText().replace("\"", "").replace("'", "");
        textRange = new TextRange(1, templateString.getTextLength() - 1);
    }

    /**
     * @see com.intellij.psi.PsiReference#getElement()
     * @return the full source element
     */
    @Override
    public PsiElement getElement() {
        return templateString;
    }

    /**
     * @see com.intellij.psi.PsiReference#getRangeInElement()
     * @return the full range incl. quotes.
     */
    @Override
    public TextRange getRangeInElement() {
        return textRange;
    }

    /**
     * @see com.intellij.psi.PsiReference#resolve()
     * @return the resolved template file or null
     */
    @Override
    public PsiElement resolve() {

        final String[] pathElements = cleanString.split(":");

        String base = pathElements[0];
        String ctrl = pathElements[1];
        String viewFileName = pathElements[2];

        final String filename = base + ".php";
        PsiFile[] filesByName = FilenameIndex.getFilesByName(project, filename, ProjectScope.getProjectScope(project));

        if (filesByName.length == 0) {
            return null;
        }

        PsiFile bundleFile = filesByName[0];

        try {
            PsiDirectory bundleDir = bundleFile.getContainingDirectory();
            PsiDirectory resourcesDir = bundleDir.findSubdirectory("Resources");
            PsiDirectory viewsDir = resourcesDir.findSubdirectory("views");

            if (!ctrl.equals(""))
            {
                PsiDirectory ctlDir = viewsDir.findSubdirectory(ctrl);
                return ctlDir.findFile(viewFileName);
            }
            else
            {
                return viewsDir.findFile(viewFileName);
            }

        } catch (NullPointerException npe) {
            return null;
        }
    }

    /**
     * @see com.intellij.psi.PsiReference#getCanonicalText()
     * @return Plain text representation.
     */
    @NotNull
    @Override
    public String getCanonicalText() {
        return this.templateString.getText();
    }

    /**
     * @see com.intellij.psi.PsiReference#handleElementRename(String)
     * @param newElementName
     * @return the new string literal with the new text
     * @throws IncorrectOperationException
     */
    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        ASTNode node = templateString.getNode();
        StringLiteralExpressionImpl se = new StringLiteralExpressionImpl(node);

        String[] parts = templateString.getText().split(":");
        StringBuilder sb = new StringBuilder(parts[0]).append(":").append(parts[1]).append(":");
        sb.append(newElementName);
        se.updateText(sb.toString());
        this.templateString = se;
        return templateString;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return resolve();
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        if (element.getClass().equals(TwigFile.class)) {
            PsiFile templateFile = (PsiFile) element;
            // Controller Dir?
            String ctlDir = templateFile.getParent().getName();
            // xyz.html.twig -> Default -> views -> Resources -> AcmeBundle!
            String bundleDir = templateFile.getParent().getParent().getParent().getParent().getName();
            StringBuilder sb = new StringBuilder(bundleDir).append(":").append(ctlDir).append(":").append(templateFile.getName());
            return cleanString.equals(sb.toString());
        }
        return false;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public boolean isSoft() {
        return true;
    }
}
