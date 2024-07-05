package fel.cvut.cz.ear.controller;

import fel.cvut.cz.ear.model.Tournament;
import fel.cvut.cz.ear.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
//LOCATION

    @PostMapping("/create")
    public ResponseEntity<Void> createTournament(@RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        tournamentService.createTournament(name, date);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(tournamentService.getTournamentById(tournamentId));
    }

    @PutMapping("/{tournamentId}")
    public ResponseEntity<Void> updateTournament(@PathVariable Long tournamentId, @RequestBody @Valid Tournament updatedTournament) {
        tournamentService.updateTournament(tournamentId, updatedTournament);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tournamentId}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long tournamentId) {
        tournamentService.deleteTournament(tournamentId);
        return ResponseEntity.ok().build();
    }
}