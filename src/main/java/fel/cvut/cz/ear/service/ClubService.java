package fel.cvut.cz.ear.service;

import fel.cvut.cz.ear.dao.ClubDao;
import fel.cvut.cz.ear.model.Club;
import fel.cvut.cz.ear.model.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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


    @CachePut(value = "myCache", key = "#club")
    @Transactional
    public void createClub(Club club) {
        clubDao.save(club);
    }

    @Cacheable("myCache")
    @Transactional(readOnly = true)
    public List<Club> getAllClubs() {
        return clubDao.findAll();
    }

    @Cacheable(value = "myCache", key = "#clubId")
    @Transactional(readOnly = true)
    public Club getClubById(Long clubId) {
        return clubDao.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found with id: " + clubId));
    }

    @CachePut(value = "myCache", key = "#clubId")
    @Transactional
    public Club updateClub(Long clubId, Club updatedClub) {
        Club existingClub = getClubById(clubId);
        existingClub.setName(updatedClub.getName());
        existingClub.setAddress(updatedClub.getAddress());
        clubDao.save(existingClub);
        return existingClub;
    }

    @CacheEvict(value = "myCache", key = "#clubId")
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

    @CachePut(value = "myCache", key = "#club")
    @Transactional
    public Club persist(Club club) {
        Objects.requireNonNull(club);
        clubDao.save(club);
        return club;
    }
    @Cacheable(value = "myCache", key = "#clubId")
    @Transactional(readOnly = true)
    public List<Member> getAllMembers(Long clubId) {
        Club club = getClubById(clubId);
        return club.getMembers();
    }
}