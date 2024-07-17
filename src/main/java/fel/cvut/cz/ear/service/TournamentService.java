package fel.cvut.cz.ear.service;

import fel.cvut.cz.ear.dao.TournamentDao;
import fel.cvut.cz.ear.dao.TournamentResultDao;
import fel.cvut.cz.ear.model.Club;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Tournament;
import fel.cvut.cz.ear.model.TournamentResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TournamentService {

    private final TournamentDao tournamentDao;
    private final TournamentResultDao tournamentResultDao;

    @Autowired
    public TournamentService(TournamentDao tournamentDao, TournamentResultDao tournamentResultDao) {
        this.tournamentDao = tournamentDao;
        this.tournamentResultDao = tournamentResultDao;
    }


    @CachePut(value = "myCache4", key = "#name + '_' + #date")
    @Transactional
    public void createTournament(String name, Date date) {
        Tournament newTournament = new Tournament();
        newTournament.setName(name);
        newTournament.setDate(date);

        tournamentDao.save(newTournament);
    }

    @Cacheable(value = "myCache4", key = "#memberId")
    @Transactional(readOnly = true)
    public List<TournamentResult> getAllTournamentResultsForMember(int memberId) {
        return tournamentResultDao.findAllByMember_Id(memberId);
    }


    @CachePut(value = "myCache4", key = "#tournament")
    @Transactional
    public Tournament persist(Tournament tournament) {
        Objects.requireNonNull(tournament);

        tournamentDao.save(tournament);
        return tournament;
    }


    @Cacheable("myCache4")
    @Transactional(readOnly = true)
    public List<Tournament> getAllTournaments() {
        return tournamentDao.findAll();
    }

    @Cacheable(value = "myCache4", key = "#tournamentId")
    @Transactional(readOnly = true)
    public Tournament getTournamentById(Long tournamentId) {
            return tournamentDao.findById(tournamentId)
                    .orElseThrow(() -> new RuntimeException("Member not found with id: " + tournamentId));
        }

    @CachePut(value = "myCache4", key = "#tournamentId")
    @Transactional
    public void updateTournament(Long tournamentId, Tournament updatedTournament) {


        Tournament tournament = getTournamentById(tournamentId);
        tournament.setName(updatedTournament.getName());
        tournament.setDate(updatedTournament.getDate());
        tournamentDao.save(tournament);

        }

    @CacheEvict(value = "myCache4", key = "#clubId")
    @Transactional
    public void deleteTournament(Long clubId) {
        Tournament tournament = getTournamentById(clubId);
        List<TournamentResult> members = tournament.getTournamentResults();
        if (members != null) {
            for (TournamentResult member : members) {
                member.setTournament(null);
            }
        }
        tournamentDao.delete(tournament);
    }


}

