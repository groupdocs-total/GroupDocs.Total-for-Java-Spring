package com.groupdocs.ui.editor.model;

import com.groupdocs.ui.config.CommonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.groupdocs.ui.config.DefaultDirectories.*;

@Component
public class EditorConfiguration extends CommonConfiguration {

    @Value("${editor.filesDirectory}")
    private String filesDirectory;

    @Value("${editor.fontsDirectory}")
    private String fontsDirectory;

    @PostConstruct
    public void init() {
        this.filesDirectory = StringUtils.isEmpty(this.filesDirectory) ? defaultEditorDirectory() : relativePathToAbsolute(this.filesDirectory);
    }

    public String getFilesDirectory() {
        return filesDirectory;
    }

    public void setFilesDirectory(String filesDirectory) {
        this.filesDirectory = filesDirectory;
    }

    public String getFontsDirectory() {
        return fontsDirectory;
    }

    public void setFontsDirectory(String fontsDirectory) {
        this.fontsDirectory = fontsDirectory;
    }

    @Override
    public String toString() {
        return "EditorConfiguration{" +
                "filesDirectory='" + filesDirectory + '\'' +
                ", fontsDirectory='" + fontsDirectory + '\'' +
                '}';
    }
}
