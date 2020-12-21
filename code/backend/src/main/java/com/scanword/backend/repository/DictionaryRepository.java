package com.scanword.backend.repository;

import com.scanword.backend.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {
    @Query(value = "SELECT * FROM dictionary WHERE uuid = :dictUUID", nativeQuery = true)
    Dictionary findByUUID(@Param("dictUUID")UUID dictUUID);
}
