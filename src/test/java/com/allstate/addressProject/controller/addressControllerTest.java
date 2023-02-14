package com.allstate.addressProject.controller;

import com.allstate.addressProject.entity.Address;
import com.allstate.addressProject.service.AddressBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = addressController.class)
class addressControllerTest {

    @MockBean
    private AddressBookService addressBookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Address> addressList;

    @BeforeEach
    public void setup(){
        this.addressList = new ArrayList<>();

        Address address1 = new Address();
        address1.setId(1);
        address1.setFirstName("John");
        address1.setLastName("Smith");
        address1.setAddress("S1 4SD");

        Address address2 = new Address();
        address2.setId(1);
        address2.setFirstName("Chandana");
        address2.setLastName("Kalyadapu");
        address2.setAddress("S1 4SD");

        this.addressList.add(address1);
        this.addressList.add(address2);
    }

    @Test
    void addAddressToAddressBook() throws Exception {
        Address address1 = new Address();
        address1.setId(1);
        address1.setFirstName("John");
        address1.setLastName("Smith");
        address1.setPhoneNumber("9591114009");
        address1.setAddress("S1 4SD");

        when(addressBookService.addAddress(Mockito.any(Address.class)))
                .thenReturn(address1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/addAddress")
                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(address1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        when(addressBookService.addAddress(Mockito.any(Address.class)))
                .thenReturn(null);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/api/v1/addAddress")
                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(address1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        MockHttpServletResponse response1 = result1.getResponse();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response1.getStatus());
    }

    @Test
    void deleteAddress() throws Exception {
        when(addressBookService.removeAddress(1))
                .thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteAddress/{id}", 1))
                    .andExpect(status().isOk());

        when(addressBookService.removeAddress(3))
                .thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteAddress/{id}", 3))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAddressList() throws Exception {
        given(addressBookService.getAddressList()).willReturn(addressList);
        this.mockMvc.perform(get("/api/v1/getAddressList"))
                .andExpect(status().isOk());
    }

    @Test
    void modifyAddress() throws Exception {
        Address address1 = new Address();
        address1.setId(1);
        address1.setFirstName("James");
        address1.setLastName("Smith");
        address1.setPhoneNumber("9591114009");
        address1.setAddress("S1 4SD");

        when(addressBookService.modifyAddress(Mockito.any(Address.class)))
                .thenReturn(address1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .put("/api/v1/modifyAddress")
                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(address1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        MockHttpServletResponse response1 = result1.getResponse();
        assertEquals(HttpStatus.OK.value(), response1.getStatus());

        when(addressBookService.modifyAddress(Mockito.any(Address.class)))
                .thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/modifyAddress")
                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(address1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
