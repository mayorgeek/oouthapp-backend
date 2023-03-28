package com.kaptain.oouthapp.oouthapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "lab_tests")
public class LabTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "doctor_id")
    private String doctorId;
    @Column(name = "test_received")
    private String testReceived;
    @Column(name = "suggested_medicine")
    private String suggestedMedicine;
    @Column(name = "result")
    private String result;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}