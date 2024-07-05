package fel.cvut.cz.ear.controller;

import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.MemberDTO;
import fel.cvut.cz.ear.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<MemberDTO>> getAllClubs() {
        List<MemberDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @Secured("hasRole('ROLE_ADMIN')")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long memberId) {
        MemberDTO member = memberService.getMemberById(memberId);
        return ResponseEntity.ok(member);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/level/{level}")
    public ResponseEntity<List<MemberDTO>> getMembersByLevel(@PathVariable int level) {
        List<Member> members = memberService.findMemberByLevel(level);
        List<MemberDTO> memberDTOs = members.stream()
                .map(memberService::convertToDto) // Предположим, что у вас есть метод convertToDto()
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberDTO memberDTO) {
        memberService.persist(memberDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createMemberr(@RequestBody MemberDTO memberDTO) {
        if (memberDTO.getPassword() == null || memberDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null or empty");
        }
        memberService.persist(memberDTO);
        return ResponseEntity.ok("Member created successfully");
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @Valid @RequestBody MemberDTO updatedMemberDTO) {
        memberService.updateMember(memberId, updatedMemberDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/initializeAdmin/{memberId}")
    public ResponseEntity<Void> initializeAdmin(@PathVariable Long memberId) {
        memberService.initializeAdmin(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/block/{memberId}")
    public ResponseEntity<Void> blockMember(@PathVariable Long memberId) {
        memberService.blockMember(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeLevel/{memberId}/{newLevel}")
    public ResponseEntity<Void> changeMemberLevel(@PathVariable Long memberId, @PathVariable int newLevel) {
        memberService.changeMemberLevel(memberId, newLevel);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateName/{memberId}/{newName}")
    public ResponseEntity<Void> updateName(@PathVariable Long memberId, @PathVariable String newName) {
        memberService.updateName(memberId, newName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateAddress/{memberId}/{newAddress}")
    public ResponseEntity<Void> updateAddress(@PathVariable Long memberId, @PathVariable String newAddress) {
        memberService.updateAddress(memberId, newAddress);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updatePhoneNumber/{memberId}/{newPhoneNumber}")
    public ResponseEntity<Void> updatePhoneNumber(@PathVariable Long memberId, @PathVariable String newPhoneNumber) {
        memberService.updatePhoneNumber(memberId, newPhoneNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateEmail/{memberId}/{newEmail}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long memberId, @PathVariable String newEmail) {
        memberService.updateEmail(memberId, newEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updatePassword/{memberId}/{newPassword}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long memberId, @PathVariable String newPassword) {
        memberService.updatePassword(memberId, newPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateLogin/{memberId}/{newLogin}")
    public ResponseEntity<Void> updateLogin(@PathVariable Long memberId, @PathVariable String newLogin) {
        memberService.updateLogin(memberId, newLogin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateLevel/{memberId}/{newLevel}")
    public ResponseEntity<Void> updateLevel(@PathVariable Long memberId, @PathVariable int newLevel) {
        memberService.updateLevel(memberId, newLevel);
        return ResponseEntity.ok().build();
    }
}