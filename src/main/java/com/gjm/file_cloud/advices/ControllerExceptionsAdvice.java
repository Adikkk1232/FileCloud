package com.gjm.file_cloud.advices;

import com.gjm.file_cloud.exceptions.FileDoesntExistException;
import com.gjm.file_cloud.exceptions.NoFilesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionsAdvice {
    @ExceptionHandler(NoFilesException.class)
    public ResponseEntity noFilesExceptionHandler(NoFilesException exception) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(FileDoesntExistException.class)
    public ResponseEntity fileDoesntExistExceptionHandler(FileDoesntExistException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
