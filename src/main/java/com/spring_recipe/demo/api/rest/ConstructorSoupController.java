package com.spring_recipe.demo.api.rest;

import com.spring_recipe.demo.domain.dto.CreateSoupRequest;
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
public class ConstructorSoupController {
    private final StepService stepService;
    private final RecipeService recipeService;


    @PostMapping("/Recipes")
    @PreAuthorize("hasAuthority('modification')")
    public ResponseEntity<RecipeDto> controllerSoup (@RequestBody CreateSoupRequest soup) throws RecipeAlreadyExistException {
        Recipe recipe=Recipe.builder()
                .name(soup.getName())
                .description(soup.getDescription())
                .image(soup.getImage())
                .userId(soup.getUserId())
                .build();
        Step step = Step.builder()
                .description("Добавить специи по вкусу "+ Arrays.toString(soup.getSpices()))
                .nextId(UUID.fromString("0"))
                .recipeId(recipe.getId())
                .build();
        stepService.createStep(step);

        step = Step.builder()
                .description("Готовить на медленном "+soup.getTime()+" минут")
                .recipeId(recipe.getId())
                .nextId(step.getId())
                .build();
        stepService.createStep(step);
        step = Step.builder()
                .description("Подготовить и добавть в бульон "+ Arrays.toString(soup.getIngredients()))
                .recipeId(recipe.getId())
                .nextId(step.getId())
                .build();
        stepService.createStep(step);
        step = Step.builder()
                .description("Подготовить "+Arrays.toString(soup.getBase())+" в кастрюле")
                .recipeId(recipe.getId())
                .nextId(step.getId())
                .build();

        recipe.setStepId(step.getId());

        return ResponseEntity.ok(recipeService.createRecipe(recipe));

    }
}