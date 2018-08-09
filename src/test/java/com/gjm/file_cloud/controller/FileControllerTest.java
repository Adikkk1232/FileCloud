package com.gjm.file_cloud.controller;

import com.gjm.file_cloud.service.FileServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@WebMvcTest
@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {
    private MockMvc fileControllerMock;

    @Mock
    private FileServiceImpl fileService;

    @Before
    public void setUp() throws Exception {
        FileController fileController = new FileController(fileService);

        fileControllerMock = MockMvcBuilders.standaloneSetup(fileController)
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void home() throws Exception {
        fileControllerMock.perform(
                get("/")
        ).andExpect(
                view()
                .name("index")
        );

        fileControllerMock.perform(
                get("/home")
        ).andExpect(
                view()
                .name("index")
        );
    }

    @Test
    public void addFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[0]);

        fileControllerMock.perform(
                multipart("/addFile")
                .file(file)
        ).andExpect(
                view()
                .name("addFinal")
        );
    }

    @Test
    public void deleteFile() throws Exception {
        fileControllerMock.perform(
                get("/deleteFile?name=exampleName")
        ).andExpect(
                view()
                .name("deleteFinal")
        );
    }

    @Test
    public void getFileByName() throws Exception {
        MvcResult response = fileControllerMock.perform(
                get("/getFile?name=exampleName")    // file doesn't exist
        ).andReturn();

        assertEquals(response.getResponse().getContentLength(), 0);
    }

    @Test
    public void getFileNames() throws Exception {
        fileControllerMock.perform(
                get("/getFileNames")
        ).andExpect(
                view()
                .name("getFileNamesFinal")
        );
    }

    @Test
    public void getAllFilesInZip() throws Exception {
        MvcResult response = fileControllerMock.perform(
                get("/getAllFilesInZip")    // file doesn't exist
        ).andReturn();

        assertEquals(response.getResponse().getContentLength(), 0);
    }
}