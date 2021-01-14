package com.scanword.backend.repository;

import com.scanword.backend.entity.Dictionary;
import com.scanword.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query(value = "SELECT * FROM question WHERE uuid = :questionUUID", nativeQuery = true)
    Question findByUUID(@Param("questionUUID")UUID questionUUID);
}
