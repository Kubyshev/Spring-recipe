package com.spring_recipe.demo.api.rest;

import com.spring_recipe.demo.domain.dto.CreatePorridgeRequest;
import com.spring_recipe.demo.domain.dto.RecipeDto;
import com.spring_recipe.demo.domain.entity.Recipe;
import com.spring_recipe.demo.domain.entity.Step;
import com.spring_recipe.demo.domain.exceptions.RecipeAlreadyExistException;
import com.spring_recipe.demo.service.interfaces.RecipeService;
import com.spring_recipe.demo.service.interfaces.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
public class ConstructorPorridgeController {
    private final StepService stepService;
    private final RecipeService recipeService;


    @PostMapping("/constructor/porridge")
    @PreAuthorize("hasAuthority('modification')")
    public ResponseEntity<RecipeDto> controllerPorridge (@RequestBody CreatePorridgeRequest porridge) throws RecipeAlreadyExistException {
        Recipe recipe=Recipe.builder()
                .name(porridge.getName())
                .description(porridge.getDescription())
                .image(porridge.getImage())
                .userId(porridge.getUserId())
                .build();
        Step step = Step.builder()
                .description("Добавить масло, соль , сахар по вкусу "+ Arrays.toString(porridge.getIngredients()))
                .recipeId(recipe.getId())
                .build();
        stepService.createStep(step);

        step = Step.builder()
                .description("Готовить "+porridge.getTime()+" минут")
                .recipeId(recipe.getId())
                .nextId(step.getId())
                .build();
        stepService.createStep(step);
        step = Step.builder()
                .description("Залить крупу "+ porridge.getBase())
                .recipeId(recipe.getId())
                .nextId(step.getId())
                .build();
        stepService.createStep(step);
        step = Step.builder()
                .description("Насыпать "+porridge.getCereal()+" в кастрюлю")
                .recipeId(recipe.getId())
                .nextId(step.getId())
                .build();
        stepService.createStep(step);

        recipe.setStepId(step.getId());

        return ResponseEntity.ok(recipeService.createRecipe(recipe));

    }
}