package fel.cvut.cz.ear.service;


import fel.cvut.cz.ear.model.Club;
import fel.cvut.cz.ear.model.MemberDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Role;
import fel.cvut.cz.ear.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;

import fel.cvut.cz.ear.dao.MemberDao;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberDTO convertToDto(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setName(member.getName());
        memberDTO.setAddress(member.getAddress());
        memberDTO.setPhoneNumber(member.getPhoneNumber());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setBalance(member.getBalance());
        memberDTO.setStatus(member.getStatus());
        memberDTO.setLevel(member.getLevel());
        memberDTO.setPoints(member.getPoints());
        memberDTO.setLogin(member.getLogin());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setRole(member.getRole());
        return memberDTO;
    }

    public Member convertToEntity(MemberDTO memberDTO) {
        if (memberDTO.getName() == null || memberDTO.getPassword() == null) {
            throw new IllegalArgumentException("Name and Password cannot be null");
        }
        if (memberDao.findByLogin(memberDTO.getLogin()) != null) {
            throw new DataIntegrityViolationException("This login already exists");
        }
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setAddress(memberDTO.getAddress());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        member.setEmail(memberDTO.getEmail());
        member.setBalance(memberDTO.getBalance());
        member.setStatus(memberDTO.getStatus());
        member.setLevel(memberDTO.getLevel());
        member.setPoints(memberDTO.getPoints());
        member.setLogin(memberDTO.getLogin());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setRole(memberDTO.getRole());
        return member;
    }

    @Autowired
    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Member> findMemberByLevel(int level) {
        return Optional.ofNullable(memberDao.findByLevel(level))
                .orElse(Collections.emptyList());
    }



    @CachePut(value = "myCache2", key = "#login")
    @Transactional
    public void createMember(String name, String address, String phoneNumber, String email, int level, String login, String password) {
        if (memberDao.findByLogin(login) != null) {
            throw new DataIntegrityViolationException("This login already exists");
        }

        Member newMember = new Member();
        newMember.setName(name);
        newMember.setAddress(address);
        newMember.setPhoneNumber(phoneNumber);
        newMember.setEmail(email);
        newMember.setBalance(0);
        newMember.setStatus(false);
        newMember.setLevel(level);
        newMember.setPoints(0);
        newMember.setLogin(login);
        newMember.setPassword(passwordEncoder.encode(password));

        memberDao.save(newMember);
    }






    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void initializeAdmin(Long memberId) {
        Member admin = memberDao.findById(memberId).orElseThrow();
        admin.setRole(Role.ADMIN);
        memberDao.save(admin);
    }


    @CachePut(value = "myCache2", key = "#member")
    @Transactional
    public void initializeAdmin(Member member) {
        member.setRole(Role.ADMIN);
        memberDao.save(member);
    }

    public boolean authenticateMember(String login, String password) {
        Member user = memberDao.findByLogin(login);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }


    @CacheEvict(value = "myCache2", key = "#memberId")
    @Transactional
    public Member deleteMember(Long memberId) {
//        checkAdminRole();
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            memberDao.delete(member);
            return member;
        }
        return null;
    }

    @Cacheable("myCache2")
    @Transactional(readOnly = true)
    public List<MemberDTO> getAllMembers() {
        return memberDao.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updateMember(Long memberId, @Valid Member updatedMember) {
        Member existingMember = memberDao.findById(memberId).orElse(null);



//            checkPermission(existingMember.getLogin());

            existingMember.setName(updatedMember.getName());
            existingMember.setAddress(updatedMember.getAddress());
            existingMember.setPhoneNumber(updatedMember.getPhoneNumber());
            existingMember.setEmail(updatedMember.getEmail());


            memberDao.save(existingMember);

    }

    @Cacheable(value = "myCache2", key = "#memberId")
    @Transactional(readOnly = true)
    public MemberDTO getMemberById(Long memberId) {
        return memberDao.findById(memberId)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
    }
    private void checkPermission(String memberLogin) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();

        if (!currentLogin.equals(memberLogin) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void blockMember(Long memberId) {
//        checkAdminRole();
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setStatus(false);
            memberDao.save(member);
        }
    }

    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void changeMemberLevel(Long memberId, int newLevel) {
//        checkAdminRole();
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setLevel(newLevel);
            memberDao.save(member);
        }
    }

    public void checkAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access is denied");
        }
    }


    @CachePut(value = "myCache2", key = "#memberDTO")
    @Transactional
    public void persist(MemberDTO memberDTO) {
        Objects.requireNonNull(memberDTO);
        Member member = convertToEntity(memberDTO);
        String password = member.getPassword();
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException(member.getLogin() + "Password cannot be null or empty");
        }
        member.setPassword(passwordEncoder.encode(password)); // Кодирование пароля
        if (member.getRole() == null) {
            member.setRole(Constants.DEFAULT_ROLE);
        }
        memberDao.save(member);
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updateMember(Long memberId, @Valid MemberDTO updatedMemberDTO) {
        Member existingMember = memberDao.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        existingMember.setName(updatedMemberDTO.getName());
        existingMember.setAddress(updatedMemberDTO.getAddress());
        existingMember.setPhoneNumber(updatedMemberDTO.getPhoneNumber());
        existingMember.setEmail(updatedMemberDTO.getEmail());
        memberDao.save(existingMember);
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updateName(Long memberId, @NotBlank String newName) {
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setName(newName);
            memberDao.save(member);
        }
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updateAddress(Long memberId, @NotBlank String newAddress) {
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setAddress(newAddress);
            memberDao.save(member);
        }
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updatePhoneNumber(Long memberId, @Pattern(regexp = "\\d{10}", message = "Chyba") String newPhoneNumber) {
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setPhoneNumber(newPhoneNumber);
            memberDao.save(member);
        }
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updateEmail(Long memberId, @Email String newEmail) {
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setEmail(newEmail);
            memberDao.save(member);
        }
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updatePassword(Long memberId, @NotBlank String newPassword) {
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setPassword(passwordEncoder.encode(newPassword));
            memberDao.save(member);
        }
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updateLogin(Long memberId, String newLogin) {
        Member member = memberDao.findByLogin(newLogin);
        if (member != null) {
            throw new DataIntegrityViolationException("This login already exists");
        }
        member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setLogin(newLogin);
            memberDao.save(member);
        }
    }


    @CachePut(value = "myCache2", key = "#memberId")
    @Transactional
    public void updateLevel(Long memberId, int newLevel) {
//        checkAdminRole();
        Member member = memberDao.findById(memberId).orElse(null);
        if (member != null) {
            member.setLevel(newLevel);
            memberDao.save(member);
        }
    }
}