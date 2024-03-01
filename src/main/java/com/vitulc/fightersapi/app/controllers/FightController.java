package com.vitulc.fightersapi.app.controllers;

import com.vitulc.fightersapi.app.dtos.FightDto;
import com.vitulc.fightersapi.app.dtos.FightsHistoryDto;
import com.vitulc.fightersapi.app.dtos.FightWinnerDto;
import com.vitulc.fightersapi.app.services.FightService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fights")
public class FightController {

    private final FightService fightService;

    public FightController(FightService fightService) {
        this.fightService = fightService;
    }

    @PostMapping()
    public ResponseEntity<String> createFight(@RequestBody @Valid FightDto fightDto) {
        return fightService.create(fightDto);
    }

    @PostMapping("/winner")
    public ResponseEntity<String> setWinnerFighter(@RequestBody @Valid FightWinnerDto fightWinnerDto) {
        return fightService.setWinnerFighter(fightWinnerDto);
    }

    @GetMapping("/history")
    public ResponseEntity<List<FightsHistoryDto>> getAllFights() {
        return fightService.getAllFightsByUser();
    }


    @GetMapping("/history/fighters")
    public ResponseEntity<List<FightsHistoryDto>> getFightHistoryByFighters(
            @RequestParam( "fighterOneDocument") String fighterOneDocument,
            @RequestParam("fighterTwoDocument") String fighterTwoDocument) {
        return fightService.getAllFightsBetweenTwoFighters(fighterOneDocument, fighterTwoDocument);
    }
}
