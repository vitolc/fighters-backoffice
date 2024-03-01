package com.vitulc.fightersapi.app.controllers;

import com.vitulc.fightersapi.app.dtos.UpdateFighterDto;
import com.vitulc.fightersapi.app.services.FighterService;
import com.vitulc.fightersapi.app.dtos.FighterDto;
import com.vitulc.fightersapi.app.dtos.FighterResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fighter")
public class FighterController {

    private final FighterService fighterService;


    public FighterController(FighterService fighterService) {
        this.fighterService = fighterService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid FighterDto fighterDto) {
        return fighterService.create(fighterDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FighterResponseDto>> getFighters() {
        return fighterService.getFighters();
    }

    @GetMapping("/{document}")
    public ResponseEntity<FighterResponseDto> getFighterByDocument(@PathVariable String document) {
        return fighterService.getFighterByDocument(document);
    }

    @PutMapping("/{document}")
    public ResponseEntity<String> updateFighter(@PathVariable String document, @RequestBody @Valid UpdateFighterDto updateFighterDto) {
        return fighterService.updateFighter(document, updateFighterDto);
    }

    @DeleteMapping("/{document}")
    public ResponseEntity<String> deleteFighter(@PathVariable String document) {
        return fighterService.deleteFighter(document);
    }

    @PutMapping("/{document}/restore")
    public ResponseEntity<String> restoreFighter(@PathVariable String document) {
        return fighterService.restoreFighter(document);
    }
}
