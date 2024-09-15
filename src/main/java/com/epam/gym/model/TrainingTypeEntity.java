package com.epam.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;

import static com.epam.gym.util.Constants.ALLOCATION_SIZE;

@Entity
@Table(name = "training_type")
@Getter
public class TrainingTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_type_seq")
    @SequenceGenerator(name = "training_type_seq", sequenceName = "training_type_id_seq", allocationSize = ALLOCATION_SIZE)
    private Long id;

    @Column(name = "training_type_name", nullable = false)
    private String trainingTypeName;
}
