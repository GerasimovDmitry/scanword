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
@Table(name = "scanword_question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanwordQuestion {
    @Id
    @Column(name = "question_uuid", nullable = false)
    private UUID questionUUID;

    @Column(name = "scanword_uuid", nullable = false)
    private UUID scanwordUUID;

    @Column(name = "orientation", nullable = false)
    private String orientation;

    @Column(name = "location", nullable = false)
    private String location;
}
