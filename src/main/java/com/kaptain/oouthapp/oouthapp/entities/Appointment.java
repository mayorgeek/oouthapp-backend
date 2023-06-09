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
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;
    @Column(name = "patient_id", nullable = false)
    private String patientId;
    @Column(name = "preferred_date", nullable = false)
    private String preferredDate;
    @Column(name = "preferred_time", nullable = false)
    private String preferredTime;

    @Column(name = "prescription", nullable = true)
    private String prescription;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}