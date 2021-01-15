package com.scanword.backend.repository;

import com.scanword.backend.entity.UserScanword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserScanwordRepository extends JpaRepository<UserScanword, UUID> {
    UserScanword findByUserUUIDAndScanwordUUID(UUID userUUID, UUID scanwordUUID);
}
