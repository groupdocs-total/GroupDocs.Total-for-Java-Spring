package com.groupdocs.ui.search.model;

import com.groupdocs.search.results.FoundDocumentField;

public class SearchDocumentResult {

    private FoundDocumentField[] foundFields;
    private String filePath;

    public void setFoundFields(FoundDocumentField[] foundFields) {
        this.foundFields = foundFields;
    }

    public FoundDocumentField[] getFoundFields() {
        return foundFields;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
