package com.spring_recipe.demo.service;
import com.spring_recipe.demo.domain.dto.RecipeDtoOut;
import com.spring_recipe.demo.domain.entity.Recipe;
import com.spring_recipe.demo.domain.entity.Step;
import com.spring_recipe.demo.domain.exceptions.RecipeNotFoundException;
import com.spring_recipe.demo.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;


@Service
@RequiredArgsConstructor
public class StepServiceOutImpl {
    private final StepRepository repository;

    public RecipeDtoOut mapToRecipeDtoOut(Recipe recipe) throws RecipeNotFoundException {
        LinkedList<String> listStr = new LinkedList<String>();

        Step stepForOut= repository.findById(recipe.getStepId()).stream()
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException((recipe.getStepId()).toString()));
        while((stepForOut.getNextId())!= null)
        {
            stepForOut= repository.findById(stepForOut.getNextId()).stream()
                    .findFirst()
                    .orElseThrow(() -> new RecipeNotFoundException("Ты не прав"));
            listStr.add(stepForOut.getDescription()+" "+stepForOut.getId());

        }

        String[] array = listStr.toArray(new String[listStr.size()]);

        return RecipeDtoOut.builder()
                .name(recipe.getName())
                .id(recipe.getId())
                .description(recipe.getDescription())
                .image(recipe.getImage())
                .userId(recipe.getUserId())
                .steps(array)
                .requestTime(LocalDateTime.now())
                .build();
    }


}
    
