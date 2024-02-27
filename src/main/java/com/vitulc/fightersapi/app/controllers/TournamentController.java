package com.vitulc.fightersapi.app.controllers;

import com.vitulc.fightersapi.app.dtos.TournamentDto;
import com.vitulc.fightersapi.app.dtos.TournamentFighterDto;
import com.vitulc.fightersapi.app.dtos.TournamentWinnerDto;
import com.vitulc.fightersapi.app.services.TournamentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tournament")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create (@RequestBody @Valid TournamentDto tournamentDto){
        return tournamentService.create(tournamentDto);
    }

    @PostMapping("/addFighters")
    public ResponseEntity<String> addFightersInTournament(@RequestBody @Valid TournamentFighterDto tournamentFighterDto) {
        return tournamentService.addFightersInTournament(tournamentFighterDto);
    }

    @PostMapping("/winner")
    public ResponseEntity<String> setWinnerTournament(@RequestBody @Valid TournamentWinnerDto tournamentWinnerDto) {
        return tournamentService.setWinnerTournament(tournamentWinnerDto);
    }
}
