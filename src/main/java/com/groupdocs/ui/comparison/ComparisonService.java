package com.groupdocs.ui.comparison;

import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;

import java.util.List;

public interface ComparisonService {

    ComparisonConfiguration getComparisonConfiguration();

    List<FileDescriptionEntity> loadFiles(FileTreeRequest fileTreeRequest);
}
