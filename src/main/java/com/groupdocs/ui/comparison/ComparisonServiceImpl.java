package com.groupdocs.ui.comparison;

import com.google.common.collect.Ordering;
import com.groupdocs.comparison.common.license.License;
import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.groupdocs.ui.util.Utils.FILE_NAME_COMPARATOR;
import static com.groupdocs.ui.util.Utils.FILE_TYPE_COMPARATOR;

@Service
public class ComparisonServiceImpl implements ComparisonService {

    private static final Logger logger = LoggerFactory.getLogger(ComparisonServiceImpl.class);

    @Autowired
    private ComparisonConfiguration comparisonConfiguration;
    @Autowired
    private GlobalConfiguration globalConfiguration;

    /**
     * Initializing fields after creating configuration objects
     */
    @PostConstruct
    public void init() {
        // set GroupDocs license
        try {
            License license = new License();
            license.setLicense(globalConfiguration.getApplication().getLicensePath());
        } catch (Throwable exc) {
            logger.error("Can not verify Comparison license!");
        }
    }

    @Override
    public ComparisonConfiguration getComparisonConfiguration() {
        return comparisonConfiguration;
    }

    public List<FileDescriptionEntity> loadFiles(FileTreeRequest fileTreeRequest) {
        String currentPath = fileTreeRequest.getPath();
        if (StringUtils.isEmpty(currentPath)) {
            currentPath = comparisonConfiguration.getFilesDirectory();
        } else {
            currentPath = String.format("%s%s%s", comparisonConfiguration.getFilesDirectory(), File.separator, currentPath);
        }
        File directory = new File(currentPath);
        List<FileDescriptionEntity> fileList = new ArrayList<>();
        List<File> filesList = Arrays.asList(directory.listFiles());
        try {
            // sort list of files and folders
            filesList = Ordering.from(FILE_TYPE_COMPARATOR).compound(FILE_NAME_COMPARATOR).sortedCopy(filesList);
            for (File file : filesList) {
                // check if current file/folder is hidden
                if (file.isHidden()) {
                    // ignore current file and skip to next one
                    continue;
                } else {
                    FileDescriptionEntity fileDescription = getFileDescriptionEntity(file);
                    // add object to array list
                    fileList.add(fileDescription);
                }
            }
            return fileList;
        } catch (Exception ex) {
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    /**
     * Create file description
     *
     * @param file file
     * @return signature file description
     */
    private FileDescriptionEntity getFileDescriptionEntity(File file) {
        FileDescriptionEntity fileDescription = new FileDescriptionEntity();
        fileDescription.setGuid(file.getAbsolutePath());
        fileDescription.setName(file.getName());
        // set is directory true/false
        fileDescription.setDirectory(file.isDirectory());
        // set file size
        fileDescription.setSize(file.length());
        return fileDescription;
    }
}
