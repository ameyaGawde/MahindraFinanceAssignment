package com.mtech.assgnproj.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtech.assgnproj.model.Advisor;
import com.mtech.assgnproj.service.AdvisorService;


@SpringBootTest
@AutoConfigureMockMvc
public class AdvisorControllerTest {

	@Mock
	private AdvisorService advisorService;

	@InjectMocks
	private AdvisorController advisorController;

	@Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Test
    public void testAddAdvisor() throws Exception {

        Advisor advisor = new Advisor();
        advisor.setName("John Doe");
        advisor.setPhotoUrl("http://example.com/photo.jpg");

        String advisorJson = objectMapper.writeValueAsString(advisor);

        doNothing().when(advisorService).addAdvisor(any(Advisor.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/advisor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(advisorJson))
                .andExpect(status().isOk());

        verify(advisorService, times(1)).addAdvisor(any(Advisor.class));
    }

}
