package com.groupdocs.ui.config;

import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class DefaultDirectories {
    public static final String LIC = "GroupDocs.Total.Java.lic";
    public static final String LICENSES = "Licenses";
    public static final String DOCUMENT_SAMPLES = "DocumentSamples";
    public static final String DEFAULT_LIC = FileSystems.getDefault().getPath(LICENSES + File.separator + LIC).toAbsolutePath().toString();
    public static final String DEFAULT_FILES = FileSystems.getDefault().getPath(DOCUMENT_SAMPLES).toAbsolutePath().toString();

    static {
        makeDirs(FileSystems.getDefault().getPath(LICENSES).toAbsolutePath().toFile());
        makeDirs(FileSystems.getDefault().getPath(DOCUMENT_SAMPLES).toAbsolutePath().toFile());
    }

    private static void makeDirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String relativePathToAbsolute(String path) {
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();

        if (!StringUtils.isEmpty(path)) {
            for (Path root : rootDirectories) {
                if (path.startsWith(root.toString())) {
                    return path;
                }
            }
        }

        return FileSystems.getDefault().getPath(path).toAbsolutePath().toString();
    }
}
