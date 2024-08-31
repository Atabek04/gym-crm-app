package com.gymcrm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    private Integer id;
    private Integer userId;
    private TrainingType specialization;
}
