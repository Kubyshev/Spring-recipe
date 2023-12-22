package com.spring_recipe.demo.util;

import com.spring_recipe.demo.domain.dto.CreateStepRequest;
import com.spring_recipe.demo.domain.dto.StepDto;
import com.spring_recipe.demo.domain.entity.Step;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class StepMappingUtil {

    public static StepDto mapToStepDto(Step step) {
        return StepDto.builder()
                .id(step.getId())
                .description(step.getDescription())
                .recipeId(step.getRecipeId())
                .nextId(step.getNextId())
                .requestTime(LocalDateTime.now())
                .build();
    }

    public static Step mapToStepFromRequest(CreateStepRequest request) {
        return Step.builder()
                .description(request.getDescription())
                .recipeId(request.getRecipeId())
                .nextId(request.getNextId())
                .build();
    }
}