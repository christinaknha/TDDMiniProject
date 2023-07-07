package com.example.TDDMiniProject;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(OrderController.class)
public class TestHTTPRequests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllOrdersApi() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createOrderApi() throws Exception {
        // Create a sample OrderEntity object for testing
        OrderEntity order = new OrderEntity("name", LocalDate.now(), "address", 10.99);

        // Perform the POST request to the "/create" endpoint
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .content(asJsonString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    public void testUpdateApi() throws Exception {
        OrderEntity updatedOrder = new OrderEntity("name2", LocalDate.now(), "address2", 10.99);
//        OrderEntity orderToUpdate = new OrderEntity("name1", LocalDate.now(), "address1", 10.99);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/update-{id}", 1L)
                        .content(asJsonString(updatedOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete-{id}", 1L))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
