package com.xenji.php.symfony2.clickableroutes;

import com.intellij.codeInsight.template.zencoding.tokens.StringLiteralToken;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.templateLanguages.OuterLanguageElement;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;
import com.jetbrains.php.lang.patterns.PhpPatterns;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.twig.*;

/**
 * Reference Contributor implementation for Routes
 *
 * @author Mario Mueller mario@xenji.com
 * @license MIT
 */
public class RoutePsiReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {

        PsiElementPattern.Capture<PsiElement> psiElementCapture = PhpPatterns
                .psiElement(TwigTokenTypes.IDENTIFIER).withLanguage(TwigLanguage.INSTANCE);
        registrar.registerReferenceProvider(psiElementCapture, new RoutePsiReferenceProvider(),PsiReferenceRegistrar.DEFAULT_PRIORITY);

    }
}
