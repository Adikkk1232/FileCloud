package com.gjm.file_cloud.exceptions;

public class NoFilesException extends RuntimeException {
    public NoFilesException(String desc) {
        super(desc);
    }
}
