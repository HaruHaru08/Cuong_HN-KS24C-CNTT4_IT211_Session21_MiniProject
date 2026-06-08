package com.example.miniprj.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private String dosage; // e.g., 2 times a day
    private Integer quantity;
}
