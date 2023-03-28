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
@Table(name = "treatments")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "doctor_id")
    private String doctorId;
    @Column(name = "doctor_name")
    private String doctorName;
    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "treatment_id")
    private String treatmentId;

    @Column(name = "drugs")
    private String drugs;

    @Column(name = "preferred_date")
    private String preferredDate;
    @Column(name = "preferred_time_slot")
    private String preferredTimeSlot;
    @Column(name = "time_slot_status")
    private Integer timeSlotStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "udpated_at")
    private Timestamp updatedAt;

}