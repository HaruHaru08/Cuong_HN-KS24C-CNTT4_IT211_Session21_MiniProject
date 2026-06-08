package com.example.miniprj.service;

import com.example.miniprj.model.Appointment;
import com.example.miniprj.model.Doctor;
import com.example.miniprj.model.User;
import com.example.miniprj.repository.AppointmentRepository;
import com.example.miniprj.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAppointmentsForDay(User user, LocalDate date) {
        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        
        return appointmentRepository.findByDoctorAndAppointmentTimeBetween(doctor, startOfDay, endOfDay);
    }
}
