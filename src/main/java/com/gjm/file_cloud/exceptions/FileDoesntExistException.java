package com.gjm.file_cloud.exceptions;

public class FileDoesntExistException extends RuntimeException {
    public FileDoesntExistException(String desc) {
        super(desc);
    }
}
