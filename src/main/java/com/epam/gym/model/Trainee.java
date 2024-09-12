package com.epam.gym.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainee_seq")
    @SequenceGenerator(name = "trainee_seq", sequenceName = "trainee_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();
}