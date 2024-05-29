package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.dtos.*;
import com.vitulc.fightersapi.app.entities.Users;
import com.vitulc.fightersapi.app.entities.Fighter;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.ConflictException;
import com.vitulc.fightersapi.app.errors.exceptions.NotFoundException;
import com.vitulc.fightersapi.app.repositories.FighterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class FighterService {

    private final FighterRepository fighterRepository;
    private final AuthenticationService authenticationService;
    private final UploadImageService uploadImageService;

    public FighterService(
            FighterRepository fighterRepository,
            AuthenticationService authenticationService,
            UploadImageService uploadImageService) {

        this.fighterRepository = fighterRepository;
        this.authenticationService = authenticationService;
        this.uploadImageService = uploadImageService;
    }

    public ResponseEntity<String> create(FighterDto fighterDto) {

        Users currentUser = authenticationService.getCurrentUser();

        if (fighterRepository.existsByUserAndDocumentIgnoreCase(currentUser, fighterDto.document())) {
            throw new ConflictException("You already have a fighter with this document created");
        }
        var newFighter = new Fighter(fighterDto);

        newFighter.setUser(currentUser);
        fighterRepository.save(newFighter);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fighter created successfully");
    }

    public ResponseEntity<String> setFighterImage(String document, MultipartFile image){

        var fighter = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        String imageUrl = uploadImageService.uploadImage(image);

        if (image.isEmpty()) {
            throw new BadRequestException("Image file is required");
        }

        fighter.setPicture(imageUrl);
        fighterRepository.save(fighter);

        return ResponseEntity.ok( "Fighter photo added successfully");
    }

    public ResponseEntity<List<FighterResponseDto>> getFighters() {
        List<FighterResponseDto> fightersList = fighterRepository.findByUserAndIsDeletedFalse(authenticationService.getCurrentUser())
                .stream().map(FighterResponseDto::new).toList();
        return ResponseEntity.ok(fightersList);
    }

    public ResponseEntity<FighterResponseDto> getFighterByDocument(String document) {
        Fighter fighter = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        FighterResponseDto fighterResponseDto = new FighterResponseDto(fighter);
        return ResponseEntity.ok(fighterResponseDto);
    }

    public ResponseEntity<String> updateFighter(String document, UpdateFighterDto updateFighterDto){

        var fighter = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        if (updateFighterDto.weight() == null
                && updateFighterDto.name() == null
                && updateFighterDto.nickname() == null
                && updateFighterDto.age() == null){
            throw new BadRequestException("Update request contains no fields to update");
        }

        Optional.ofNullable(updateFighterDto.name())
                .ifPresent(fighter::setName);

        Optional.ofNullable(updateFighterDto.nickname())
                .ifPresent(fighter::setNickname);

        Optional.of(updateFighterDto.age())
                .ifPresent(fighter::setAge);

        Optional.ofNullable(updateFighterDto.weight())
                .ifPresent(fighter::setWeight);

        fighterRepository.save(fighter);
        return ResponseEntity.ok("Fighter updated successfully");
    }

    public ResponseEntity<String> deleteFighter(String document) {
        var fighter = fighterRepository.findByUserAndDocumentAndIsDeletedFalse(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        fighter.setDeleted(true);
        fighterRepository.save(fighter);
        return ResponseEntity.ok("Fighter deleted successfully");
    }

    public ResponseEntity<String> restoreFighter(String document) {
        Fighter fighter = fighterRepository.findByUserAndDocument(authenticationService.getCurrentUser(), document)
                .orElseThrow(() -> new NotFoundException("Fighter not found"));

        if (!fighter.getDeleted()){
            throw new BadRequestException("The fighter was not excluded");
        }

        fighter.setDeleted(false);
        fighterRepository.save(fighter);
        return ResponseEntity.ok("Fighter restored successfully");
    }

}
