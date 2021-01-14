package com.scanword.backend.entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "scanword")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scanword {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "dictionary_uuid", nullable = false)
    private UUID dictionaryUUID;

}
