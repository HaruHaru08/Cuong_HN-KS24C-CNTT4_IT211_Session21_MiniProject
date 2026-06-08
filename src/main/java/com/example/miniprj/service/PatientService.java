package com.example.miniprj.service;

import com.example.miniprj.model.Appointment;
import com.example.miniprj.model.Patient;
import com.example.miniprj.model.Prescription;
import com.example.miniprj.model.User;
import com.example.miniprj.repository.AppointmentRepository;
import com.example.miniprj.repository.PatientRepository;
import com.example.miniprj.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public Appointment bookAppointment(User user, Appointment appointment) {
        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        appointment.setPatient(patient);
        appointment.setStatus("SCHEDULED");
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getMyHistory(User user) {
        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        return appointmentRepository.findByPatient(patient);
    }
}
