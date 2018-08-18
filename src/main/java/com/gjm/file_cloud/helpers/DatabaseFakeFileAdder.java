package com.gjm.file_cloud.helpers;

import com.gjm.file_cloud.dao.FileDao;
import com.gjm.file_cloud.entity.File;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseFakeFileAdder {
    private FileDao fileDao;

    @Autowired
    public DatabaseFakeFileAdder(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @PostConstruct
    public void addFakeFile() {
        boolean addFakeFile = true;

        if(addFakeFile) {
            File file = new File();

            file.setName("Fake file.txt");
            try(ByteOutputStream bos = new ByteOutputStream()) {
                bos.writeAsAscii("hello from gjm side!\n");
                file.setBytes(bos.toByteArray());
            }

            fileDao.save(file);
        }
    }
}
