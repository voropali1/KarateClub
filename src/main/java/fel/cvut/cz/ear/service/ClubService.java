package fel.cvut.cz.ear.service;

import fel.cvut.cz.ear.dao.ClubDao;
import fel.cvut.cz.ear.model.Club;
import fel.cvut.cz.ear.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ClubService {

    private final ClubDao clubDao;

    @Autowired
    public ClubService(ClubDao clubDao) {
        this.clubDao = clubDao;
    }


    @Transactional
    public void createClub(Club club) {
        clubDao.save(club);
    }

    @Transactional(readOnly = true)
    public List<Club> getAllClubs() {
        return clubDao.findAll();
    }

    @Transactional(readOnly = true)
    public Club getClubById(Long clubId) {
        return clubDao.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));
    }

    @Transactional
    public Club updateClub(Long clubId, Club updatedClub) {
        Club existingClub = getClubById(clubId);
        existingClub.setName(updatedClub.getName());
        existingClub.setAddress(updatedClub.getAddress());
        clubDao.save(existingClub);
        return existingClub;
    }

    @Transactional
    public void deleteClub(Long clubId) {
        Club club = getClubById(clubId);
        List<Member> members = club.getMembers();
        if (members != null) {
            for (Member member : members) {
                member.setClub(null);
            }
        }
        clubDao.delete(club);
    }

    @Transactional
    public Club persist(Club club) {
        Objects.requireNonNull(club);
        clubDao.save(club);
        return club;
    }
    @Transactional(readOnly = true)
    public List<Member> getAllMembers(Long clubId) {
        Club club = getClubById(clubId);
        return club.getMembers();
    }
}