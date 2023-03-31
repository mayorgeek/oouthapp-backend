package com.kaptain.oouthapp.oouthapp.repository;

import com.kaptain.oouthapp.oouthapp.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByPatientId(String patientId);
}