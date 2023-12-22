package com.spring_recipe.demo.domain.dto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSoupRequest {
    private String name;
    private int userId;
    private String[] base;
    private String description;
    private String image;
    private String[] ingredients;
    private String[] spices;
    private int time;
}
