package com.spring_recipe.demo.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepDto {
    private String description;
    private UUID recipeId;
    private UUID id;
    private UUID nextId;
    private LocalDateTime requestTime;
}