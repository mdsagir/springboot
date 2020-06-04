package com.greenjava.validationexception;

import com.greenjava.validationexception.controller.ItemController;
import com.greenjava.validationexception.controller.ItemService;
import com.greenjava.validationexception.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void itemTest() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/item")
                        .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"box\",\"price\":12}"));
    }

    @Test
    public void itemTestService() throws Exception {

        Mockito.when(itemService.createItem()).thenReturn(new Item(1, "box", 12,12));

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/item-service")
                        .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"box\",\"price\":12}"));
    }

    @Test
    public void itemTestServiceDB() throws Exception {

        Mockito.when(itemService.retrieveAllItems()).thenReturn(
                Arrays.asList(
                        new Item(1, "box", 12, 12))
        );

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .get("/item-db-service")
                        .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"box\",\"price\":12,\"value\":12}]"));
    }

}
