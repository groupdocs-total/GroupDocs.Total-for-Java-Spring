package com.groupdocs.ui.comparison;

import com.groupdocs.ui.config.CommonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ComparisonConfiguration extends CommonConfiguration {

    @Value("${comparison.filesDirectory}")
    private String filesDirectory;

    @Value("${comparison.resultDirectory}")
    private String resultDirectory;

    public String getFilesDirectory() {
        return filesDirectory;
    }

    public void setFilesDirectory(String filesDirectory) {
        this.filesDirectory = filesDirectory;
    }

    public String getResultDirectory() {
        return resultDirectory;
    }

    public void setResultDirectory(String resultDirectory) {
        this.resultDirectory = resultDirectory;
    }

    @Override
    public String toString() {
        return super.toString() +
                "ComparisonConfiguration{" +
                "filesDirectory='" + filesDirectory + '\'' +
                ", resultDirectory='" + resultDirectory + '\'' +
                '}';
    }
}
