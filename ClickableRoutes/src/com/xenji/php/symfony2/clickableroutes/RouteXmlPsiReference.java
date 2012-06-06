package com.xenji.php.symfony2.clickableroutes;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: mario
 * Date: 06.06.12
 * Time: 08:02
 * To change this template use File | Settings | File Templates.
 */
public class RouteXmlPsiReference implements PsiReference {
    @Override
    public PsiElement getElement() {
        return null;
    }

    @Override
    public TextRange getRangeInElement() {
        return null;
    }

    @Override
    public PsiElement resolve() {
        return null;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return null;
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return null;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return null;
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        return false;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
