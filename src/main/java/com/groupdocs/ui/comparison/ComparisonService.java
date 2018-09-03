package com.groupdocs.ui.comparison;

import com.groupdocs.ui.comparison.model.request.CompareRequest;
import com.groupdocs.ui.comparison.model.response.CompareResultResponse;
import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;

import java.io.InputStream;
import java.util.List;

public interface ComparisonService {

    ComparisonConfiguration getComparisonConfiguration();

    List<FileDescriptionEntity> loadFiles(FileTreeRequest fileTreeRequest);

    CompareResultResponse compare(CompareRequest compareRequest);

    CompareResultResponse compareFiles(InputStream firstContent, String firstUrl, InputStream secondContent, String secondUrl);
}
