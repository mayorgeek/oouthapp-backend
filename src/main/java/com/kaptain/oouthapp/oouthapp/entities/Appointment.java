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

//    private String doctorId;
    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "preferred_date")
    private String preferredDate;
    @Column(name = "preferred_time")
    private String preferredTime;
//    @Column(name = "iuc_number")
//    private String iucNumber;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}