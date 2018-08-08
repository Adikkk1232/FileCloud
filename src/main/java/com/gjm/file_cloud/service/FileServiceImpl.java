package com.gjm.file_cloud.service;

import com.gjm.file_cloud.dao.FileDao;
import com.gjm.file_cloud.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {
    private FileDao fileDao;

    @Autowired
    public FileServiceImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public void addFile(File file) {
        fileDao.save(file);
    }

    @Override
    public void deleteFile(String name) {
        fileDao.deleteFileByName(name);
    }

    @Override
    public File getFileByName(String name) {
        Optional<File> result = fileDao.findFileByName(name);

        return result.orElse(null);
    }

    @Override
    public List<String> getFileNames() {
        List<String> names = new LinkedList<>();

        for(File file : fileDao.findAll()) {
            names.add(file.getName());
        }

        return names;
    }

    @Override
    public byte[] getAllFilesInZip() {
        List<File> files = fileDao.findAll();

        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.setComment("Compressed all files!");

            for(File file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);
                zos.write(file.getBytes());
            }

            zos.finish();
            return bos.toByteArray();
        } catch(IOException exc) {
            exc.printStackTrace();
        }

        return null;
    }
}
