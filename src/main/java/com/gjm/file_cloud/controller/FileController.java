package com.gjm.file_cloud.controller;

import com.gjm.file_cloud.entity.File;
import com.gjm.file_cloud.service.FileService;
import com.gjm.file_cloud.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class FileController {
    private FileService fileService;

    @Autowired
    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = {"/", "/home", "/index"}, method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public String addFile(@RequestParam("file") MultipartFile file, Model model) {
        File fileToAdd = null;

        try {
            fileToAdd = new File();
            fileToAdd.setName(file.getName());
            fileToAdd.setBytes(file.getBytes());
        } catch(IOException exc) {
            exc.printStackTrace();
        }

        fileService.addFile(fileToAdd);

        return "addFinal";
    }

    // TODO: Add exception handling if file won't exist with passed name
    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    public String deleteFile(@RequestParam("name") String name, Model model) {
        fileService.deleteFile(name);

        return "deleteFinal";
    }

    // TODO: Add exception handling if file won't exist with passed name
    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> getFileByName(@RequestParam("name") String name) {
        File file = fileService.getFileByName(name);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        httpHeaders.setContentLength(file.getBytes().length);

        return new ResponseEntity<>(file.getBytes(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFileNames", method = RequestMethod.GET)
    public String getFileNames(Model model) {
        List<String> fileNames = fileService.getFileNames();

        model.addAttribute("names", fileNames);

        return "getFileNamesFinal";
    }

    @RequestMapping(value = "/getFilesInZip", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> getAllFilesInZip() {
        byte[] zipArchive = fileService.getAllFilesInZip();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=files.zip");
        httpHeaders.setContentLength(zipArchive.length);

        return new ResponseEntity<>(zipArchive, httpHeaders, HttpStatus.OK);
    }
}
