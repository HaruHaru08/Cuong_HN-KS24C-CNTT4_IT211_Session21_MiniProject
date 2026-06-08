package com.example.miniprj.repository;

import com.example.miniprj.model.Appointment;
import com.example.miniprj.model.Doctor;
import com.example.miniprj.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorAndAppointmentTimeBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPatient(Patient patient);
}
