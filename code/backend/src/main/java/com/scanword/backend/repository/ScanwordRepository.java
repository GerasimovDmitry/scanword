package com.scanword.backend.repository;

import com.scanword.backend.entity.Scanword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScanwordRepository extends JpaRepository<Scanword, UUID> {
}
