package com.scanword.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_scanword_question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScanwordQuestion {
    @Id
    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "scanword_uuid", nullable = false)
    private UUID scanwordUUID;

    @Column(name = "question_uuid", nullable = false)
    private UUID questionUUID;

    @Column(name = "is_passed", nullable = false)
    private Boolean isPassed = false;
}
