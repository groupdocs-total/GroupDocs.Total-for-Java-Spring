package com.groupdocs.ui.config;

import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class DefaultDirectories {
    public static final String LIC = "GroupDocs.Total.Java.lic";
    public static final String LICENSES = "Licenses";
    public static final String DOCUMENT_SAMPLES = "DocumentSamples";
    public static final String VIEWER = "Viewer";
    public static final String SIGNATURE = "Signature";
    public static final String COMPARISON = "Comparison";
    public static final String ANNOTATION = "Annotation";

    public static String defaultLicenseDirectory() {
        Path defaultLic = FileSystems.getDefault().getPath(LICENSES + File.separator + LIC).toAbsolutePath();
        makeDirs(defaultLic.toFile());
        return defaultLic.toString();
    }

    public static String defaultViewerDirectory() {
        return getDefaultFilesDir(VIEWER);
    }

    public static String defaultSignatureDirectory() {
        return getDefaultFilesDir(SIGNATURE);
    }

    public static String defaultComparisonDirectory() {
        return getDefaultFilesDir(COMPARISON);
    }

    public static String defaultAnnotationDirectory() {
        return getDefaultFilesDir(ANNOTATION);
    }

    public static String getDefaultFilesDir(String folder) {
        String dir = DOCUMENT_SAMPLES + File.separator + folder;
        Path path = FileSystems.getDefault().getPath(dir).toAbsolutePath();
        makeDirs(path.toFile());
        return path.toString();
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
                    makeDirs(new File(path));
                    return path;
                }
            }
        }

        Path absolutePath = FileSystems.getDefault().getPath(path).toAbsolutePath();
        makeDirs(absolutePath.toFile());
        return absolutePath.toString();
    }
}
