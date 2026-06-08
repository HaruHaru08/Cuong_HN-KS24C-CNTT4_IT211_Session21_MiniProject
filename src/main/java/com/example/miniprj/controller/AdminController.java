package com.example.miniprj.controller;

import com.example.miniprj.model.Doctor;
import com.example.miniprj.model.Medicine;
import com.example.miniprj.model.Patient;
import com.example.miniprj.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // --- Doctor Endpoints ---
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(adminService.getAllDoctors());
    }

    @PostMapping("/doctors")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(adminService.createDoctor(doctor), HttpStatus.CREATED);
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(adminService.updateDoctor(id, doctor));
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    // --- Patient Endpoints ---
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(adminService.getAllPatients());
    }

    @PostMapping("/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(adminService.createPatient(patient), HttpStatus.CREATED);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        adminService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    // --- Medicine Endpoints ---
    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        return ResponseEntity.ok(adminService.getAllMedicines());
    }

    @PostMapping("/medicines")
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        return new ResponseEntity<>(adminService.saveMedicine(medicine), HttpStatus.CREATED);
    }

    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        adminService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }

    // --- Advanced Security ---
    @PostMapping("/users/{id}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable Long id) {
        adminService.lockUser(id);
        return ResponseEntity.ok().build();
    }
}
