package com.gjm.file_cloud.controller;

import com.gjm.file_cloud.entity.File;
import com.gjm.file_cloud.service.FileService;
import com.gjm.file_cloud.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {
    private FileService fileService;

    @Autowired
    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity addFile(@RequestParam("file") MultipartFile file) {
        File fileToAdd = null;

        try {
            fileToAdd = new File();
            fileToAdd.setName(file.getOriginalFilename());
            fileToAdd.setBytes(file.getBytes());
        } catch(IOException exc) {
            exc.printStackTrace();
        }

        fileService.addFile(fileToAdd);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(file.getOriginalFilename());
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ResponseEntity getFile(@RequestParam("name") String name) {
        File fetchedFile = fileService.getFileByName(name);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fetchedFile);
    }

    @RequestMapping(value = "/file", method = RequestMethod.DELETE)
    public ResponseEntity deleteFile(@RequestParam("name") String name) {
        fileService.deleteFile(name);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(name);
    }

    @RequestMapping(value = "/files/names", method = RequestMethod.GET)
    public ResponseEntity getFileNames() {
        List<String> fileNames = fileService.getFileNames();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileNames);
    }

    @RequestMapping(value = "/files/zip", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getAllFilesInZip() {
        byte[] zipArchive = fileService.getAllFilesInZip();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(zipArchive);
    }
}
