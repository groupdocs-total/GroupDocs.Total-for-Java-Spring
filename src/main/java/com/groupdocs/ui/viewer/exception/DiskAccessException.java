package com.groupdocs.ui.viewer.exception;

import com.groupdocs.ui.exception.TotalGroupDocsException;

import java.io.File;

/**
 * Wrapper for disk access exceptions
 */
public class DiskAccessException extends TotalGroupDocsException {
    public static String MESSAGE = "Can not %s. Path is %s";

    public DiskAccessException(String message, File file) {
        this(message, file.getAbsolutePath());
    }

    public DiskAccessException(String message, String path) {
        super(String.format(MESSAGE, message, path));
    }

    public DiskAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
