package com.example.miniprj.repository;

import com.example.miniprj.model.Doctor;
import com.example.miniprj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);
}
