package com.groupdocs.ui.annotation.controller;

import com.google.gson.Gson;
import com.groupdocs.ui.model.request.FileTreeRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationControllerTest {
    MockMvc mvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    AnnotationController controller;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        this.mvc = standaloneSetup(this.controller).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getView()  throws Exception {
        mvc.perform(get("/annotation")).andExpect(status().isOk()).andExpect(view().name("annotation"));
    }

    @Test
    public void loadFileTree() throws Exception {
        FileTreeRequest fileTreeRequest = new FileTreeRequest();
        fileTreeRequest.setPath("");
        mvc.perform(post("/annotation/loadFileTree")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(fileTreeRequest))
        ).andExpect(status().isOk());
    }

    public void loadDocumentDescription() {
    }

    public void loadDocumentPage() {
    }

    public void downloadDocument() {
    }

    public void uploadDocument() {
    }

    public void textCoordinates() {
    }

    public void annotate() {
    }
}