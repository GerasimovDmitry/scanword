package com.scanword.backend.entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "user_scanword")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScanword {
    @Column(name = "user_uuid")
    private UUID userUUID;

    @Id
    @Column(name = "scanword_uuid", nullable = false)
    private UUID scanwordUUID;

    @Column(name = "count_hints_used", nullable = false)
    private Integer countHintsUsed;

    @Column(name = "score", nullable = false)
    private Integer score;

}
