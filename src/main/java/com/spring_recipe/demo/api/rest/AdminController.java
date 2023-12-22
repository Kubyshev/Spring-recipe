package com.spring_recipe.demo.api.rest;

import com.spring_recipe.demo.domain.dto.CreateAppUserRequest;
import com.spring_recipe.demo.domain.dto.RecipeDto;
import com.spring_recipe.demo.domain.entity.Recipe;
import com.spring_recipe.demo.domain.entity.Step;
import com.spring_recipe.demo.domain.dto.StepDto;
import com.spring_recipe.demo.domain.exceptions.RecipeAlreadyExistException;
import com.spring_recipe.demo.domain.exceptions.RecipeNotFoundException;
import com.spring_recipe.demo.service.UserDetailsServiceImpl;
import com.spring_recipe.demo.service.interfaces.RecipeService;
import com.spring_recipe.demo.service.interfaces.StepService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;



@RestController
@RequiredArgsConstructor
public class AdminController {
    private final StepService stepService;
    private final RecipeService recipeService;
    private final UserDetailsServiceImpl userDetailsService;

    @DeleteMapping("/Admin/Recipes/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity deleteRecipe(@PathVariable String id) throws RecipeNotFoundException{
        return ResponseEntity.ok()
            .body(format("Recipe with id= %s deleted", id));   
    }

    @PatchMapping("/Admin/Recipes")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<RecipeDto> updateRecipe(@RequestBody Recipe Recipe) throws RecipeNotFoundException{
        return ResponseEntity.ok(recipeService.updateRecipe(Recipe));
    }

    @DeleteMapping("/Admin/Steps/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity deleteStep(@PathVariable String id) throws RecipeNotFoundException {
        return ResponseEntity.ok()
            .body(format("Step with id= %s deleted", id));
    }

    @PatchMapping("/Admin/Steps")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<StepDto> updateStep(@RequestBody Step Step) throws RecipeNotFoundException{
        return ResponseEntity.ok(stepService.updateStep(Step));
    }

    @PostMapping("/Admin/User")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity addUser(@RequestBody CreateAppUserRequest user) throws RecipeAlreadyExistException {
        return ResponseEntity.ok(userDetailsService.addUser(user));
    }

    @GetMapping("/Admin/User/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity findUserById(@PathVariable int id) throws RecipeNotFoundException {
        return ResponseEntity.ok(userDetailsService.findById(id));
    }

    @GetMapping("/Admin/User/@{email}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity findUserByEmail(@PathVariable String email) throws RecipeNotFoundException {
        return ResponseEntity.ok(userDetailsService.findByEmail(email));
    }

    @PatchMapping("/Admin/User/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody CreateAppUserRequest user) throws RecipeAlreadyExistException, RecipeNotFoundException {
        return ResponseEntity.ok(userDetailsService.updateUser(user, id));
    }
    
}