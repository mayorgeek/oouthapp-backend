package com.kaptain.oouthapp.oouthapp.repository;

import com.kaptain.oouthapp.oouthapp.entities.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    Drug findByReference(String reference);
}