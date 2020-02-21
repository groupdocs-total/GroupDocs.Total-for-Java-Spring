package com.groupdocs.ui.search.config;

import com.groupdocs.ui.config.CommonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.groupdocs.ui.config.DefaultDirectories.*;

@Component
public class SearchConfiguration extends CommonConfiguration {

    @Value("${search.filesDirectory}")
    private String filesDirectory;

    @Value("${search.defaultDocument}")
    private String defaultDocument;

    @Value("${search.fontsDirectory}")
    private String fontsDirectory;

    @PostConstruct
    public void init() {
        this.filesDirectory = StringUtils.isEmpty(this.filesDirectory) ? defaultSearchDirectory() : relativePathToAbsolute(this.filesDirectory);
    }

    public String getFilesDirectory() {
        return filesDirectory;
    }

    public void setFilesDirectory(String filesDirectory) {
        this.filesDirectory = filesDirectory;
    }

    public String getDefaultDocument() {
        return defaultDocument;
    }

    public void setDefaultDocument(String defaultDocument) {
        this.defaultDocument = defaultDocument;
    }

    public String getFontsDirectory() {
        return fontsDirectory;
    }

    public void setFontsDirectory(String fontsDirectory) {
        this.fontsDirectory = fontsDirectory;
    }

    @Override
    public String toString() {
        return super.toString() +
                "SearchConfiguration{" +
                "filesDirectory='" + filesDirectory + '\'' +
                ", defaultDocument='" + defaultDocument + '\'' +
                ", fontsDirectory='" + fontsDirectory + '\'' +
                '}';
    }
}
