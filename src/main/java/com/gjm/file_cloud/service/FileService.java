package com.gjm.file_cloud.service;

import com.gjm.file_cloud.entity.File;

import java.util.List;

public interface FileService {
    void addFile(File file);
    void deleteFile(String name);
    File getFileByName(String name);
    List<String> getFileNames();

    byte[] getAllFilesInZip();
}
