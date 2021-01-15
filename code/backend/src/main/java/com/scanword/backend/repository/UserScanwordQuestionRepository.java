package com.scanword.backend.repository;

import com.scanword.backend.entity.UserScanwordQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserScanwordQuestionRepository extends JpaRepository<UserScanwordQuestion, UUID> {
    UserScanwordQuestion findByQuestionUUID(UUID questionUUID);

    int countByScanwordUUID(UUID scanwordUUID);
}
