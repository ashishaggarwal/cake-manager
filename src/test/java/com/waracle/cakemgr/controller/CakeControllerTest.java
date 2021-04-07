package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.application.CakeManagerApplication;
import com.waracle.cakemgr.dto.Cake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CakeManagerApplication.class})
@AutoConfigureMockMvc(addFilters = false)
public class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRedirectToWebPageForRootEndPoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andExpect(model().attributeExists("cakes"))
            .andExpect(model().attribute("cakes", hasSize(greaterThan(0))))
            .andExpect(model().size(1))
            .andExpect(view().name("cake"))
            .andExpect(model().hasNoErrors());
    }

    @Test
    public void shouldReturnCakesAsJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cakes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].title").exists())
            .andExpect(jsonPath("$[0].desc").exists())
            .andExpect(jsonPath("$[0].image").exists());
    }

    @Test
    public void shouldPostCake() throws Exception {
        Cake cake = cake();
        mockMvc.perform(MockMvcRequestBuilders.post("/cakes")
            .content(new ObjectMapper().writeValueAsString(cake))
            .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value(cake.getTitle()))
            .andExpect(jsonPath("$.desc").value(cake.getDescription()))
            .andExpect(jsonPath("$.image").value(cake.getImage()));
    }

    @Test
    public void shouldReturnAddCakeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/addCake"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andExpect(model().attributeExists("cake"))
            .andExpect(view().name("addCake"))
            .andExpect(model().hasNoErrors());
    }

    @Test
    public void shouldSaveCakeFromAddCakeForm() throws Exception {
        Cake cake = cake();
        mockMvc.perform(MockMvcRequestBuilders.post("/addCake")
            .contentType(APPLICATION_FORM_URLENCODED_VALUE)
            .param("title", cake.getTitle())
            .param("description", cake.getDescription())
            .param("image", cake.getImage()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    private Cake cake() {
        return Cake.builder()
            .title("creamy cake")
            .description("its full of cream")
            .image("willBeAddedLater")
            .build();
    }
}