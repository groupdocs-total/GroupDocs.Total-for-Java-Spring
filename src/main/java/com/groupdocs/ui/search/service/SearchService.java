package com.groupdocs.ui.search.service;

import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.request.LoadDocumentRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import com.groupdocs.ui.search.config.SearchConfiguration;
import com.groupdocs.ui.search.model.IndexDocumentResult;
import com.groupdocs.ui.search.model.SearchDocumentResult;
import com.groupdocs.ui.search.model.SearchRequest;

import java.util.List;

public interface SearchService {
    SearchConfiguration getSearchConfiguration();

    List<FileDescriptionEntity> getFileList(FileTreeRequest fileTreeRequest);

    IndexDocumentResult getDocumentDescription(LoadDocumentRequest loadDocumentRequest);

    List<SearchDocumentResult> search(SearchRequest searchRequest);
}
