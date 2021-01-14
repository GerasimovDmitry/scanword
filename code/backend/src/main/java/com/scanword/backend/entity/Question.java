package com.scanword.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private String type;
}
