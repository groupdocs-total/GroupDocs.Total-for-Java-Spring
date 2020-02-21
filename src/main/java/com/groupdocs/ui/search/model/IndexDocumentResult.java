package com.groupdocs.ui.search.model;

import com.groupdocs.ui.model.response.LoadDocumentEntity;

public class IndexDocumentResult extends LoadDocumentEntity {

    private String indexStatus;

    public String getIndexStatus() {
        return indexStatus;
    }

    public void setIndexStatus(String indexStatus) {
        this.indexStatus = indexStatus;
    }
}
