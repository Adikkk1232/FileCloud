package com.gjm.file_cloud.service;

import com.gjm.file_cloud.dao.FileDao;
import com.gjm.file_cloud.entity.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FileServiceImplTest {
    @Mock
    private FileDao fileDaoMock;

    private FileService fileServiceMock;

    private File file;

    // TODO: write tests for getFileNames() and getAllFilesInZip() which will test the logic not only Mocks, need @Autowired dao for this
//    @Autowired
//    private FileDao fileDaoReal;
//
//    private FileService fileServiceReal;

    @Before
    public void setUp() throws Exception {
        fileServiceMock = new FileServiceImpl(fileDaoMock);
//        fileServiceReal = new FileServiceImpl(fileDaoReal);

        file = new File();
        file.setBytes(null);
        file.setName("Example_file");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addFile() {
        fileServiceMock.addFile(file);

        Mockito.verify(fileDaoMock, Mockito.times(1))
                .save(file);
    }

    @Test
    public void deleteFile() {
        fileServiceMock.deleteFile(file.getName());

        Mockito.verify(fileDaoMock, Mockito.times(1))
                .deleteFileByName(file.getName());
    }

    @Test
    public void getFileByName() {
        fileServiceMock.getFileByName(file.getName());

        Mockito.verify(fileDaoMock, Mockito.times(1))
                .findFileByName(file.getName());
    }

    @Test
    public void getFileNames() {
        fileServiceMock.getFileNames();

        Mockito.verify(fileDaoMock, Mockito.times(1))
                .findAll();

//        fileServiceReal.addFile(file);
//
//        List<String> fileNames = fileServiceReal.getFileNames();
//
//        Assert.assertEquals(fileNames.size(), 1);
//        Assert.assertEquals(fileNames.get(0), file.getName());
    }

    @Test
    public void getAllFilesInZip() {
        fileServiceMock.getAllFilesInZip();

        Mockito.verify(fileDaoMock, Mockito.times(1))
                .findAll();
    }
}