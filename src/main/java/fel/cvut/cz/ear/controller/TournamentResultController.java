package fel.cvut.cz.ear.controller;

import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Tournament;
import fel.cvut.cz.ear.model.TournamentResult;
import fel.cvut.cz.ear.service.MemberService;
import fel.cvut.cz.ear.service.TournamentResultService;
import fel.cvut.cz.ear.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tournamentResults")
public class TournamentResultController {

    private final TournamentResultService tournamentResultService;
    private final TournamentService tournamentService;
    private final MemberService memberService;

    @Autowired
    public TournamentResultController(TournamentResultService tournamentResultService, TournamentService tournamentService, MemberService memberService) {
        this.tournamentResultService = tournamentResultService;
        this.tournamentService = tournamentService;
        this.memberService = memberService;
    }



    //LOCATION
    @PostMapping("/create/{tournamentId}")
    public ResponseEntity<Void> createTournamentResult(@PathVariable Long tournamentId,
                                                       @RequestParam Long memberId,
                                                       @RequestParam Long secondMemberId,
                                                       @RequestParam int point,
                                                       @RequestParam int place,
                                                       UriComponentsBuilder uriBuilder) {

        TournamentResult createdResult = tournamentResultService.createTournamentResult(tournamentId, memberId, secondMemberId, point, place);

        UriComponents uriComponents = uriBuilder.path("/tournamentResults/{id}").buildAndExpand(createdResult.getId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/create/{tournamentId}/array")
    public ResponseEntity<Void> createTournamentResultArray(@PathVariable Long tournamentId,@RequestBody ArrayList<TournamentResult> tournamentResultArrayList ) {
        tournamentResultService.createTournamentResultsArray(tournamentResultArrayList, tournamentId);
        return ResponseEntity.ok().build();
    }
    //LOCATION
    @GetMapping
    public ResponseEntity<List<TournamentResult>> getAllTournamentResults() {
        List<TournamentResult> tournamentResults = tournamentResultService.getAllTournamentResults();
        return ResponseEntity.ok(tournamentResults);
    }

    @GetMapping("/{tournamentResultId}")
    public ResponseEntity<TournamentResult> getTournamentResultById(@PathVariable Long tournamentResultId) {
        TournamentResult tournamentResult = tournamentResultService.getTournamentResultById(tournamentResultId);
        return ResponseEntity.ok(tournamentResult);
    }

    @PutMapping("/{tournamentResultId}")
    public ResponseEntity<Void> updateTournamentResult(@PathVariable Long tournamentResultId, @RequestBody @Valid TournamentResult updatedTournamentResult) {
        tournamentResultService.updateTournamentResult(tournamentResultId, updatedTournamentResult);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{tournamentResultId}")
    public ResponseEntity<Void> deleteTournamentResult(@PathVariable Long tournamentResultId) {
        tournamentResultService.deleteTournamentResult(tournamentResultId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/recordMembers/main/{tournamentId}/{placeId}")
    public ResponseEntity<Void> setMemberToTournamentResult(@PathVariable Long tournamentId, @PathVariable int placeId, @RequestBody Long memberIds) {
        tournamentResultService.setMemberToPlace(tournamentId, placeId, memberIds);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/recordMembers/second/{tournamentId}/{placeId}")
    public ResponseEntity<Void> setSecondMemberToTournamentResult(@PathVariable Long tournamentId, @PathVariable int placeId, @RequestBody Long memberIds) {
        tournamentResultService.setSecondMemberToPlace(tournamentId, placeId, memberIds);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/recordMembers/two/{tournamentId}/{placeId}")
    public ResponseEntity<Void> setFirstAndSecondMemberToTournamentResult(@PathVariable Long tournamentId, @PathVariable int placeId, @RequestBody List<Long> memberIds) {
        tournamentResultService.setTwoMembersToPlace(tournamentId, placeId, memberIds.get(0), memberIds.get(1));
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/recordMembers/{tournamentId}")
//    public ResponseEntity<Void> setMembersToTournamentResult(@PathVariable Long tournamentId, @RequestBody List<Long> memberIds) {
//        Tournament tournament = tournamentService.getTournamentById(tournamentId);
//        List<TournamentResult> tournamentResults = tournamentResultService.getAllTournamentResults();
//
//        if (memberIds.size() != tournamentResults.size()) {
//            return ResponseEntity.badRequest().build();
//        }
//        for (int i = 0; i < tournamentResults.size(); i++) {
//            TournamentResult result = tournamentResults.get(i);
//            Long memberId = memberIds.get(i);
//            Member member = memberService.getMemberById(memberId);
//            result.setMember(member);
//            tournamentResultService.persist(result);
//        }
//        return ResponseEntity.ok().build();
//    }
@GetMapping("/findByTournamentAndPlace")
public ResponseEntity<TournamentResult> findByTournamentAndPlace(@RequestParam Long tournamentId,
                                                                 @RequestParam int place) {
    Tournament tournament = tournamentService.getTournamentById(tournamentId);
    TournamentResult tournamentResult = tournamentResultService.findByTournamentAndPlace(tournament, place);

    if (tournamentResult == null) {
        return ResponseEntity.notFound().build();
    } else {
        return ResponseEntity.ok(tournamentResult);
    }
}

    @GetMapping("/tournamentResultsForMember")
    public ResponseEntity<List<TournamentResult>> getAllTournamentResultsForMember(@RequestParam int memberId) {
        List<TournamentResult> tournamentResults = tournamentResultService.getAllTournamentResultsForMember(memberId);
        return ResponseEntity.ok(tournamentResults);
    }


}