package fel.cvut.cz.ear.controller;

import fel.cvut.cz.ear.model.Club;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }
//LOCATION
    @PostMapping
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        return ResponseEntity.ok(clubService.persist(club));
    }


    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<Club> getClubById(@PathVariable Long clubId) {
        Club club = clubService.getClubById(clubId);
        return ResponseEntity.ok(club);
    }
    //LOCATION
    @PutMapping("/{clubId}")
    public ResponseEntity<Club> updateClub(@PathVariable Long clubId, @RequestBody @Valid Club updatedClub) {
        return ResponseEntity.ok(clubService.updateClub(clubId, updatedClub));
    }
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clubId}/members")
    public ResponseEntity<List<Member>> getAllMembers(@PathVariable Long clubId) {
        List<Member> members = clubService.getAllMembers(clubId);
        return ResponseEntity.ok(members);
    }
}