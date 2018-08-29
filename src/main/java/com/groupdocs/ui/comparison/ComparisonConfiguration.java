package com.groupdocs.ui.comparison;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "comparison")
public class ComparisonConfiguration {

    @Value("${filesDirectory}")
    private String filesDirectory;

    @Value("#{new Integer('${preloadPageCount}')}")
    private Integer preloadPageCount;

    @Value("#{new Boolean('${download}')}")
    private Boolean download;

    @Value("#{new Boolean('${upload}')}")
    private Boolean upload;

    @Value("#{new Boolean('${print}')}")
    private Boolean print;

    public String getFilesDirectory() {
        return filesDirectory;
    }

    public void setFilesDirectory(String filesDirectory) {
        this.filesDirectory = filesDirectory;
    }

    public Integer getPreloadPageCount() {
        return preloadPageCount;
    }

    public void setPreloadPageCount(Integer preloadPageCount) {
        this.preloadPageCount = preloadPageCount;
    }

    public Boolean getDownload() {
        return download;
    }

    public void setDownload(Boolean download) {
        this.download = download;
    }

    public Boolean getUpload() {
        return upload;
    }

    public void setUpload(Boolean upload) {
        this.upload = upload;
    }

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

}
