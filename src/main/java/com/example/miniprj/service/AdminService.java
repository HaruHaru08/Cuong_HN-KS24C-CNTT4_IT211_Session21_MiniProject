package com.example.miniprj.service;

import com.example.miniprj.model.*;
import com.example.miniprj.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;

    // --- Doctor Management ---
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        log.info("Admin creating new doctor: {}", doctor.getUser().getUsername());
        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setSpecialization(doctorDetails.getSpecialization());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
        log.info("Admin deleted doctor with id: {}", id);
    }

    // --- Patient Management ---
    public List<Patient> getAllPatients() {
        return patientRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Transactional
    public Patient createPatient(Patient patient) {
        log.info("Admin creating new patient: {}", patient.getUser().getUsername());
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setAddress(patientDetails.getAddress());
        patient.setPhoneNumber(patientDetails.getPhoneNumber());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
        log.info("Admin deleted patient with id: {}", id);
    }

    // --- Medicine Management ---
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Transactional
    public Medicine saveMedicine(Medicine medicine) {
        log.info("Admin saving medicine: {}", medicine.getName());
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    // --- Security & Session Control ---
    @Transactional
    public void lockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        userRepository.save(user);
        log.info("SECURITY ALERT: Admin locked user account: {}. All associated sessions are now invalid.", user.getUsername());
    }
}
