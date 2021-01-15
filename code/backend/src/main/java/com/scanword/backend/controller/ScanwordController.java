package com.scanword.backend.controller;

import com.scanword.backend.entity.Media;
import com.scanword.backend.entity.Scanword;
import com.scanword.backend.entity.models.BriefScanword;
import com.scanword.backend.entity.models.ScanwordModel;
import com.scanword.backend.entity.models.ScanwordUserModel;
import com.scanword.backend.service.DictionaryRepositoryService;
import com.scanword.backend.service.ScanwordRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/scanword")
public class ScanwordController {
    private ScanwordRepositoryService scanwordRepositoryService;

    public ScanwordController(ScanwordRepositoryService scanwordRepositoryService) {
        this.scanwordRepositoryService = scanwordRepositoryService;
    }

    @PostMapping("/save/super")
    public void saveScanwordByAdmin(@RequestBody ScanwordModel scanword) {
        try {
            scanwordRepositoryService.checkName(scanword.getName());

        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Сканворд с таким именем уже существует");
        }

        scanwordRepositoryService.saveScanwordByAdmin(scanword);
    }

    @PostMapping("/save")
    public void saveScanwordByUser(@RequestBody ScanwordModel scanword) {
    }

    @PostMapping("/check")
    public ScanwordModel checkScanword(@RequestBody ScanwordModel scanword) {
        return null;
    }

    @GetMapping("/open")
    public ScanwordUserModel getScanwordByIdForUser(@RequestParam UUID id) {

        return scanwordRepositoryService.getScanwordForUser(id);
    }

    @GetMapping("/open/super")
    public ScanwordModel getScanwordByIdForAdmin(@RequestParam UUID id) {

        return scanwordRepositoryService.getScanwordForAdmin(id);
    }

    @GetMapping("/all")
    public List<BriefScanword> getListScanword() {
        return scanwordRepositoryService.getBriefScanwords();
    }
}
