package com.example.miniprj.service;

import com.example.miniprj.model.User;
import com.example.miniprj.repository.DoctorRepository;
import com.example.miniprj.repository.MedicineRepository;
import com.example.miniprj.repository.PatientRepository;
import com.example.miniprj.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLockUser_Success() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("baduser").active(true).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.lockUser(userId);

        assertFalse(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    void testLockUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.lockUser(1L));
    }
}
