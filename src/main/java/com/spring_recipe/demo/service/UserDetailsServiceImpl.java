package com.spring_recipe.demo.service;

import com.spring_recipe.demo.domain.dto.AppUserDto;
import com.spring_recipe.demo.domain.dto.CreateAppUserRequest;
import com.spring_recipe.demo.domain.dto.UserDetailsImpl;
import com.spring_recipe.demo.domain.entity.ApplicationUser;
import com.spring_recipe.demo.domain.exceptions.RecipeAlreadyExistException;
import com.spring_recipe.demo.domain.exceptions.RecipeNotFoundException;
import com.spring_recipe.demo.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;

import static com.spring_recipe.demo.util.AppUserMappingUtil.mapToAppUserFromRequest;
import static com.spring_recipe.demo.util.AppUserMappingUtil.mapToAppUserDto;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserDetailsImpl.fromApplicationUser(
                repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"))
        );
    }

    public UserDetails loadUserById(int id) throws UsernameNotFoundException {
        return UserDetailsImpl.fromApplicationUser(
                repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!"))
        );
    }

    public AppUserDto findById(int id){
        return mapToAppUserDto(
            repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!")));
    }

    public AppUserDto findByEmail(String email){
        return mapToAppUserDto(repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!")));
    }
    
    public AppUserDto addUser(CreateAppUserRequest request) throws RecipeAlreadyExistException{
        if (!repository.existsByEmail(request.getEmail())) {
            return mapToAppUserDto(repository.save(mapToAppUserFromRequest(request)));
        }
        throw new RecipeAlreadyExistException(request.getEmail());
        
    }

    public AppUserDto updateUser(CreateAppUserRequest request,int id) throws RecipeNotFoundException{
        repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        ApplicationUser newUser = mapToAppUserFromRequest(request);
        newUser.setId(id);
        return mapToAppUserDto(repository.save(newUser));
    }
}