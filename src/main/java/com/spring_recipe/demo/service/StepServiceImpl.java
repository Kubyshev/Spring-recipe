package com.spring_recipe.demo.service;

import com.spring_recipe.demo.domain.dto.CreateStepRequest;
import com.spring_recipe.demo.domain.dto.StepDto;
import com.spring_recipe.demo.domain.entity.Step;
import com.spring_recipe.demo.domain.exceptions.RecipeAlreadyExistException;
import com.spring_recipe.demo.domain.exceptions.RecipeNotFoundException;
import com.spring_recipe.demo.repository.StepRepository;
import com.spring_recipe.demo.service.interfaces.StepService;
import com.spring_recipe.demo.util.StepMappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.spring_recipe.demo.util.StepMappingUtil.mapToStepDto;
import static com.spring_recipe.demo.util.StepMappingUtil.mapToStepFromRequest;

@Service
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {

    private final StepRepository repository;

    @Override
    public StepDto getStepById(String id) throws RecipeNotFoundException {
        return repository.findById(UUID.fromString(id)).stream()
                .map(StepMappingUtil::mapToStepDto)
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    @Transactional
    public StepDto createStep(CreateStepRequest request) throws RecipeAlreadyExistException {
        Step step = mapToStepFromRequest(request);
        if (!repository.existsById(step.getId())) {
            return mapToStepDto(repository.save(step));
        }
        throw new RecipeAlreadyExistException(step.getId().toString());
    }
    

    @Override
    @Transactional
    public StepDto createStep(Step step) throws RecipeAlreadyExistException{
        if (!repository.existsById(step.getId())) {
            return mapToStepDto(repository.save(step));
        }
        throw new RecipeAlreadyExistException(step.getId().toString());
    }

    @Override
    @Transactional
    public StepDto updateStep(Step step){
        return mapToStepDto(repository.save(step));
    }

    @Override
    public void deleteStep(String id) {
        repository.deleteById(UUID.fromString(id));
    }
}