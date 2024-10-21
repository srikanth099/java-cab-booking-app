package com.wipro.cabbooking.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.cabbooking.controller.AdminController;
import com.wipro.cabbooking.dto.AdminDTO;
import com.wipro.cabbooking.exception.AdminException;
import com.wipro.cabbooking.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    // Test cases for Admin endpoints

    @Test
    public void testAddAdmin() throws Exception {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(1);
        when(adminService.saveAdmin(any(AdminDTO.class))).thenReturn(adminDTO);

        mockMvc.perform(post("/api/admin/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.adminId", is(1)));
    }

    @Test
    public void testUpdateAdmin() throws Exception {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(1);
        when(adminService.updateAdmin(any(AdminDTO.class))).thenReturn(adminDTO);

        mockMvc.perform(put("/api/admin/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminId", is(1)));
    }

    @Test
    public void testUpdateAdminNotFound() throws Exception {
        AdminDTO adminDTO = new AdminDTO();
        when(adminService.updateAdmin(any(AdminDTO.class))).thenThrow(AdminException.class);

        mockMvc.perform(put("/api/admin/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAdmin() throws Exception {
        doNothing().when(adminService).deleteAdmin(anyInt());

        mockMvc.perform(delete("/api/admin/admins/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteAdminNotFound() throws Exception {
        doThrow(AdminException.class).when(adminService).deleteAdmin(anyInt());

        mockMvc.perform(delete("/api/admin/admins/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAdminById() throws Exception {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(1);
        when(adminService.getAdminById(anyInt())).thenReturn(adminDTO);

        mockMvc.perform(get("/api/admin/admins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminId", is(1)));
    }

    @Test
    public void testGetAdminByIdNotFound() throws Exception {
        when(adminService.getAdminById(anyInt())).thenThrow(AdminException.class);

        mockMvc.perform(get("/api/admin/admins/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllAdmins() throws Exception {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(1);
        List<AdminDTO> adminList = Collections.singletonList(adminDTO);
        when(adminService.getAllAdmins()).thenReturn(adminList);

        mockMvc.perform(get("/api/admin/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adminId", is(1)));
    }

    // Repeat similar tests for Customer, Driver, Cab, and Trip Booking endpoints

}
