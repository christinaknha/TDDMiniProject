package com.example.TDDMiniProject;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class TestHTTPRequests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllOrdersApi() throws Exception{
        OrderEntity order = new OrderEntity("hanhi", LocalDate.now(), "123 Anywhere St", 10.00);
        Mockito.when(orderService.getAllOrders()).thenReturn(List.of(order));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createOrderApi() throws Exception {
        OrderEntity order = new OrderEntity("hanhi", LocalDate.now(), "123 Anywhere St", 10.00);
        Mockito.when(orderService.saveOrder(Mockito.any())).thenReturn(order);

        // Perform the POST request to the "/create" endpoint
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .content(asJsonString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateApi() throws Exception {
        OrderEntity updatedOrder = new OrderEntity("C. Ha", LocalDate.now(), "456 Somewhere Way", 20.00);
        Mockito.when(orderService.updateOrder(Mockito.any(), Mockito.any())).thenReturn(Optional.of(updatedOrder));


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/update/{id}", 1L)
                        .content(asJsonString(updatedOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteApi() throws Exception {
        Mockito.when(orderService.deleteOrder(Mockito.any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete/{id}", 1L))
                .andExpect(status().isOk());
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

    @Test
    public void emptyCustomerNameField() throws Exception{
       OrderEntity orderToAdd= new OrderEntity("", LocalDate.now(), "address1", 10.99);
       Mockito.when(orderService.saveOrder(Mockito.any())).thenReturn(orderToAdd);


        mockMvc.perform(MockMvcRequestBuilders
                .post("/create")
                .content(asJsonString(orderToAdd))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void emptyAddressField() throws Exception{
        OrderEntity orderToAdd= new OrderEntity("name", LocalDate.now(), "", 10.99);
        Mockito.when(orderService.saveOrder(Mockito.any())).thenReturn(orderToAdd);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .content(asJsonString(orderToAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void lessThanZeroTotalField() throws Exception{
        OrderEntity orderToAdd= new OrderEntity("name", LocalDate.now(), "address", -2.99);
        Mockito.when(orderService.saveOrder(Mockito.any())).thenReturn(orderToAdd);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .content(asJsonString(orderToAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNonExistantApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/delete/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateNonExistantApi() throws Exception {
        OrderEntity updatedOrder = new OrderEntity("C. Ha", LocalDate.now(), "456 Somewhere Way", 20.00);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/update/{id}", 1L)
                        .content(asJsonString(updatedOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
