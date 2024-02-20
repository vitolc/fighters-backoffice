package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.UpdateFighterDto;
import com.vitulc.fightersapi.app.entities.CategoryGroup;
import com.vitulc.fightersapi.app.entities.Users;
import com.vitulc.fightersapi.app.dtos.FighterDto;
import com.vitulc.fightersapi.app.dtos.FighterResponseDto;
import com.vitulc.fightersapi.app.entities.Category;
import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.ConflictException;
import com.vitulc.fightersapi.app.errors.exceptions.ForbiddenException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.CategoryGroupRepository;
import com.vitulc.fightersapi.app.repositories.FighterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FighterService {

    private final FighterRepository fighterRepository;
    private final CategoryService categoryService;
    private final AuthenticationService authenticationService;
    private final CategoryGroupRepository categoryGroupRepository;

    public FighterService(
            FighterRepository fighterRepository,
            CategoryService categoryService,
            AuthenticationService authenticationService,
            CategoryGroupRepository categoryGroupRepository) {

        this.fighterRepository = fighterRepository;
        this.categoryService = categoryService;
        this.authenticationService = authenticationService;
        this.categoryGroupRepository = categoryGroupRepository;
    }

    public ResponseEntity<String> create(FighterDto fighterDto) {

        Users currentUser = authenticationService.getCurrentUser();

        if (fighterRepository.existsByUserAndDocumentIgnoreCase(currentUser, fighterDto.document())) {
            throw new ConflictException("You already have a fighter with this document created");
        }

        var newFighter = new Fighter(fighterDto);

        var categoryGroup = categoryGroupRepository.findByUserOrUserIsNullAndName(currentUser, fighterDto.categoryGroup())
                .orElseThrow(() -> new BadRequestException("This category group does not exist"));


        newFighter.setCategory(categoryService.getCategoryByWeight(
                categoryService.getCategoriesByUserAndGroup(currentUser, categoryGroup), newFighter.getWeight()));

        newFighter.setUser(currentUser);
        fighterRepository.save(newFighter);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fighter created successfully");
    }

    public ResponseEntity<List<FighterResponseDto>> getFighters() {
        List<FighterResponseDto> fightersList = authenticationService.getCurrentUser().getFighters()
                .stream().map(FighterResponseDto::new).toList();
        return ResponseEntity.ok(fightersList);
    }

    public ResponseEntity<FighterResponseDto> getFighterById(String document) {
        Fighter fighter = fighterRepository.findByUserAndDocumentIgnoreCase(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        FighterResponseDto fighterResponseDto = new FighterResponseDto(fighter);
        return ResponseEntity.ok(fighterResponseDto);
    }

    public ResponseEntity<String> updateFighter(String document, UpdateFighterDto updateFighterDto){

        Fighter fighter = fighterRepository.findByUserAndDocumentIgnoreCase(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        fighter.setName(updateFighterDto.name());
        fighter.setNickname(updateFighterDto.nickname());
        fighter.setAge(updateFighterDto.age());
        fighter.setWeight(updateFighterDto.weight());

        CategoryGroup categoryGroup = categoryGroupRepository.findByUserOrUserIsNullAndName(authenticationService.getCurrentUser(), updateFighterDto.categoryGroup())
                .orElseThrow(() -> new BadRequestException("This category group does not exist"));

        fighter.setCategory(categoryService.getCategoryByWeight(
                categoryService.getCategoriesByUserAndGroup(authenticationService.getCurrentUser(), categoryGroup), fighter.getWeight()));

        fighterRepository.save(fighter);
        return ResponseEntity.ok("Fighter updated successfully");
    }

    public ResponseEntity<String> deleteFighter(String document) {
        Fighter fighter = fighterRepository.findByUserAndDocumentIgnoreCase(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        fighterRepository.delete(fighter);
        return ResponseEntity.ok("Fighter deleted successfully");
    }
}
