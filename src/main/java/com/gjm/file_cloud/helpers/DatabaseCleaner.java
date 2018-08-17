package com.gjm.file_cloud.helpers;

import com.gjm.file_cloud.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseCleaner {
    private FileDao fileDao;

    @Autowired
    public DatabaseCleaner(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @PostConstruct
    public void databaseCleaner() {
        boolean cleanDatabase = true;

        if(cleanDatabase) {
            fileDao.deleteAll();
        }
    }
}
