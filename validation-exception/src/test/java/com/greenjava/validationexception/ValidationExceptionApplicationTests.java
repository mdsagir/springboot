package com.greenjava.validationexception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenjava.validationexception.request.Student;
import com.greenjava.validationexception.service.HomeService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.greenjava.validationexception.controller.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@SpringBootTest
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = HomeController.class, secure = false)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@RunWith( SpringRunner.class )
//@SpringBootTest
//@TestPropertySource(locations="classpath:application.properties")

//@TestPropertySource(locations="classpath:application.properties")
//@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ValidationExceptionApplicationTests {


    private MockMvc mockMvc;

    @Autowired
    private HomeService homeService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testHomeController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/docker"))
                .andExpect(status().isOk())
                .andExpect(content().string("docker"));
    }

    @Test
    public void testHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.*", Matchers.hasSize(1)));

    }

    @Test
    public void testService() {
        homeService.homeService();
    }

    @Test
    public void testRequestBody() throws Exception {

        Student student=new Student(1,"sagir");
        mockMvc.perform(post("/post-student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(student))
                .header("X-ID","123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(1)));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
