package com.groupdocs.ui.editor.service;

import com.groupdocs.editor.EditorHandler;
import com.groupdocs.editor.InputHtmlDocument;
import com.groupdocs.editor.license.License;
import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.editor.model.EditorConfiguration;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.request.LoadDocumentRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import com.groupdocs.ui.model.response.LoadDocumentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EditorServiceImpl implements EditorService {
    private static final Logger logger = LoggerFactory.getLogger(EditorServiceImpl.class);
    public static final String PDF = "pdf";

    @Autowired
    private GlobalConfiguration globalConfiguration;
    @Autowired
    private EditorConfiguration editorConfiguration;

    /**
     * Initializing fields after creating configuration objects
     */
    @PostConstruct
    public void init() {
        setLicense();
    }

    private void setLicense() {
        try {
            // set GroupDocs license
            License license = new License();
            license.setLicense(globalConfiguration.getApplication().getLicensePath());
        } catch (Throwable throwable) {
            logger.error("Can not verify Editor license!");
        }
    }

    @Override
    public List<FileDescriptionEntity> getFileList(String path) {
        if (StringUtils.isEmpty(path)) {
            path = editorConfiguration.getFilesDirectory();
        }
        try {
            File directory = new File(path);
            List<File> filesList = Arrays.asList(directory.listFiles());

            List<FileDescriptionEntity> fileList = new ArrayList<>();
            for (File file : filesList) {
                FileDescriptionEntity fileDescription = new FileDescriptionEntity();
                fileDescription.setGuid(file.getAbsolutePath());
                fileDescription.setName(file.getName());
                fileDescription.setDirectory(file.isDirectory());
                fileDescription.setSize(file.length());
                fileList.add(fileDescription);
            }
            return fileList;
        } catch (Exception ex) {
            logger.error("Exception in getting file list", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    @Override
    public EditorConfiguration getEditorConfiguration() {
        return editorConfiguration;
    }

    @Override
    public LoadDocumentEntity loadDocument(LoadDocumentRequest loadDocumentRequest) {
        LoadDocumentEntity doc = new LoadDocumentEntity();
        try {
            InputHtmlDocument inputHtmlDocument = EditorHandler.toHtml(new FileInputStream(loadDocumentRequest.getGuid()));
            inputHtmlDocument.getContent();
        } catch (Exception ex) {
            logger.error("Exception in loading document");
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
        return doc;
    }
}
