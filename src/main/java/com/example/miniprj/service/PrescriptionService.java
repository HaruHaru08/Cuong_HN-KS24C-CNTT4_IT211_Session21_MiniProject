package com.example.miniprj.service;

import com.example.miniprj.model.Prescription;
import com.example.miniprj.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        prescription.setCreatedAt(LocalDateTime.now());
        // In a real scenario, we'd validate that the doctor is the one assigned to the appointment
        return prescriptionRepository.save(prescription);
    }
}
