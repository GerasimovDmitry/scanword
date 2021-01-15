package com.scanword.backend.service;

import com.scanword.backend.entity.UserScanword;
import com.scanword.backend.entity.models.ScanwordModel;
import com.scanword.backend.repository.UserScanwordQuestionRepository;
import com.scanword.backend.repository.UserScanwordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserScanwordRepositoryService {
    private UserScanwordRepository repository;

    @Autowired
    public UserScanwordRepositoryService(UserScanwordRepository repository) {
        this.repository = repository;
    }

    public void saveUserScanword(UUID userId, ScanwordModel scanword) {
        UserScanword userScanword = new UserScanword();
        userScanword.setUserUUID(userId);
        userScanword.setScanwordUUID(scanword.getId());
        userScanword.setScore(0);
        userScanword.setCountHintsUsed(0);
        repository.saveAndFlush(userScanword);
    }

    public UserScanword getUserScanwordByUserIdAndScanwordId(UUID userId, UUID scanwordId) {
        return repository.findByUserUUIDAndScanwordUUID(userId, scanwordId);
    }
}
