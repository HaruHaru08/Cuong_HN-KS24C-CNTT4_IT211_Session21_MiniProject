package com.example.miniprj.controller;

import com.example.miniprj.model.Appointment;
import com.example.miniprj.model.User;
import com.example.miniprj.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Appointment> bookAppointment(
            @AuthenticationPrincipal User user,
            @RequestBody Appointment appointment
    ) {
        return ResponseEntity.ok(patientService.bookAppointment(user, appointment));
    }

    @GetMapping("/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Appointment>> getMyHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(patientService.getMyHistory(user));
    }
}
