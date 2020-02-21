package com.groupdocs.ui.search.service;

import com.groupdocs.search.Index;
import com.groupdocs.search.common.IndexStatus;
import com.groupdocs.search.licensing.License;
import com.groupdocs.search.results.FoundDocument;
import com.groupdocs.search.results.SearchResult;
import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.request.LoadDocumentRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import com.groupdocs.ui.search.config.SearchConfiguration;
import com.groupdocs.ui.search.model.IndexDocumentResult;
import com.groupdocs.ui.search.model.SearchDocumentResult;
import com.groupdocs.ui.search.model.SearchRequest;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private final GlobalConfiguration globalConfiguration;

    private final SearchConfiguration searchConfiguration;

    public SearchServiceImpl(GlobalConfiguration globalConfiguration, SearchConfiguration searchConfiguration) {
        this.globalConfiguration = globalConfiguration;
        this.searchConfiguration = searchConfiguration;
    }

    @PostConstruct
    public void init() {
        try {
            // set GroupDocs license
            License license = new License();
            license.setLicense(globalConfiguration.getApplication().getLicensePath());
        } catch (Throwable exc) {
            logger.error("Can not verify Search license!");
        }
    }

    @Override
    public SearchConfiguration getSearchConfiguration() {
        return searchConfiguration;
    }

    @Override
    public List<FileDescriptionEntity> getFileList(FileTreeRequest fileTreeRequest) {
        String path = fileTreeRequest.getPath();
        if (StringUtils.isEmpty(path)) {
            path = searchConfiguration.getFilesDirectory();
        }
        try {
            File directory = new File(path);
            List<File> filesList = Arrays.asList(directory.listFiles());
            List<FileDescriptionEntity> fileList = getFileDescriptionEntities(filesList);
            return fileList;
        } catch (Exception ex) {
            logger.error("Exception in getting file list", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    @Override
    public IndexDocumentResult getDocumentDescription(LoadDocumentRequest loadDocumentRequest) {
        Index index = new Index(searchConfiguration.getFilesDirectory());

        index.add(loadDocumentRequest.getGuid());

        int indexStatus = index.getIndexInfo().getIndexStatus();

        IndexDocumentResult searchDocumentResult = new IndexDocumentResult();
        searchDocumentResult.setGuid(loadDocumentRequest.getGuid());
        searchDocumentResult.setIndexStatus(getStatus(indexStatus));

        return searchDocumentResult;
    }

    @Override
    public List<SearchDocumentResult> search(SearchRequest searchRequest) {
        Index index = new Index(searchConfiguration.getFilesDirectory());

        index.add(searchRequest.getGuids());

        int indexStatus = index.getIndexInfo().getIndexStatus();

        List<SearchDocumentResult> results = new ArrayList<>();
        if (IndexStatus.Ready == indexStatus) {
            SearchResult search = index.search(searchRequest.getQuery());

            Iterator<FoundDocument> iterator = search.iterator();
            while (iterator.hasNext()) {
                FoundDocument next = iterator.next();
                SearchDocumentResult searchDocumentResult = new SearchDocumentResult();
                searchDocumentResult.setFoundFields(next.getFoundFields());
                searchDocumentResult.setFilePath(next.getDocumentInfo().getFilePath());

                results.add(searchDocumentResult);
            }
        }

        return results;
    }

    private String getStatus(int indexStatus) {
        switch (indexStatus) {
            case IndexStatus.NotStarted:
                return "NotStarted";
            case IndexStatus.Failed:
                return "Failed";
            case IndexStatus.InProgress:
                return "InProgress";
            case IndexStatus.LicenseRestrictionFinished:
                return "LicenseRestrictionFinished";
            case IndexStatus.Ready:
                return "Ready";
            default:
                return "";
        }
    }

    private List<FileDescriptionEntity> getFileDescriptionEntities(List<File> filesList) {
        List<FileDescriptionEntity> fileList = new ArrayList<>();
        for (File file : filesList) {
            String guid = file.getAbsolutePath();
            String extension = FilenameUtils.getExtension(guid);
            if (file.isDirectory() || !StringUtils.isEmpty(extension)) {
                FileDescriptionEntity fileDescription = new FileDescriptionEntity();
                fileDescription.setGuid(guid);
                fileDescription.setName(file.getName());
                fileDescription.setDirectory(file.isDirectory());
                fileDescription.setSize(file.length());
                fileList.add(fileDescription);
            }
        }
        return fileList;
    }

}
