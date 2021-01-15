package com.scanword.backend.repository;

import com.scanword.backend.entity.ScanwordQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScanwordQuestionRepository extends JpaRepository<ScanwordQuestion, UUID> {
    ScanwordQuestion findByQuestionUUID(UUID questionUUID);
}
