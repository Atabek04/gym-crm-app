package com.epam.gym.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "training_type")
@Getter
public class TrainingTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_type_seq")
    @SequenceGenerator(name = "training_type_seq", sequenceName = "training_type_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "training_type_name", nullable = false)
    private String trainingTypeName;
}
