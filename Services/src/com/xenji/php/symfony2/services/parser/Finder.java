package com.xenji.php.symfony2.services.parser;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.xml.XmlFile;
import org.apache.tools.ant.types.selectors.TypeSelector;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mario
 * Date: 05.06.12
 * Time: 08:05
 * To change this template use File | Settings | File Templates.
 */
public class Finder {

    private Project project;

    public Finder(Project project) {
        this.project = project;
    }
/*
    public List<PsiElement> findServiceXmlFiles()
    {
        // First step, search all that have the right name.
        PsiElement[] fileList = FilenameIndex.getFilesByName(project, "service.xml", ProjectScope.getProjectScope(project));

        // secondly every xml file that has a container tag with a number of services

        Collection<VirtualFile> virtualFileCollection = FileTypeIndex.getFiles(XmlFileType.INSTANCE, ProjectScope.getProjectScope(project));
    }
    */
}
