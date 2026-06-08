package com.example.miniprj.repository;

import com.example.miniprj.model.Patient;
import com.example.miniprj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);
}
