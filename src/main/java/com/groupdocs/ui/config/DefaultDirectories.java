package com.groupdocs.ui.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class DefaultDirectories {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDirectories.class);

    public static final String LIC = ".lic";
    public static final String LICENSES = "Licenses";
    public static final String DOCUMENT_SAMPLES = "DocumentSamples";
    public static final String VIEWER = "Viewer";
    public static final String SIGNATURE = "Signature";
    public static final String COMPARISON = "Comparison";
    public static final String ANNOTATION = "Annotation";
    public static final String CONVERSION = "Conversion";
    public static final String EDITOR = "Editor";

    public static String defaultLicenseDirectory() {
        Path defaultLicFolder = FileSystems.getDefault().getPath(LICENSES).toAbsolutePath();
        File licFolder = defaultLicFolder.toFile();
        if (licFolder.exists()) {
            Path defaultLicFile = getDefaultLicFile(licFolder);
            if (defaultLicFile != null) {
                return defaultLicFile.toString();
            }
        }
        licFolder.mkdirs();
        logger.info("License file path is incorrect, application launched in trial mode");
        return "";
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

    public static String defaultConversionDirectory() { return getDefaultFilesDir(CONVERSION); }

    public static String defaultEditorDirectory() {
        return getDefaultFilesDir(EDITOR);
    }

    public static String getDefaultFilesDir(String folder) {
        String dir = DOCUMENT_SAMPLES + File.separator + folder;
        Path path = FileSystems.getDefault().getPath(dir).toAbsolutePath();
        makeDirs(path);
        return path.toString();
    }

    public static void makeDirs(Path path) {
        try {
            Files.createDirectories(path);
        } catch (FileAlreadyExistsException ex) {
            // it is ok
        } catch (IOException e) {
            logger.error("Exception occurred while creating directories");
        }
    }

    public static String relativePathToAbsolute(String path) {
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();

        if (StringUtils.isEmpty(path)) {
            return FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        }

        for (Path root : rootDirectories) {
            if (path.startsWith(root.toString())) {
                makeDirs(Paths.get(path));
                return path;
            }
        }

        Path absolutePath = FileSystems.getDefault().getPath(path).toAbsolutePath();
        makeDirs(absolutePath);
        return absolutePath.toString();
    }

    public static boolean isAbsolutePath(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }

        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path root : rootDirectories) {
            if (path.startsWith(root.toString())) {
                return true;
            }
        }
        return false;
    }

    public static Path getDefaultLicFile(File licFolder) {
        for (File file : licFolder.listFiles()) {
            if (file.getName().endsWith(LIC)) {
                return FileSystems.getDefault().getPath(LICENSES + File.separator + file.getName()).toAbsolutePath();
            }
        }
        return null;
    }
}
