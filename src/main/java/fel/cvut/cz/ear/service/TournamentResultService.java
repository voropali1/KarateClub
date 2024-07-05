package fel.cvut.cz.ear.service;

import fel.cvut.cz.ear.dao.MemberDao;
import fel.cvut.cz.ear.dao.TournamentDao;
import fel.cvut.cz.ear.dao.TournamentResultDao;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Tournament;
import fel.cvut.cz.ear.model.TournamentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TournamentResultService {

    private final MemberDao memberDao;
    private final TournamentResultDao tournamentResultDao;
    private final TournamentDao tournamentDao;

    @Autowired
    public TournamentResultService(MemberDao memberDao, TournamentResultDao tournamentResultDao, TournamentDao tournamentDao) {
        this.memberDao = memberDao;
        this.tournamentResultDao = tournamentResultDao;
        this.tournamentDao = tournamentDao;
    }

    @Transactional
    public TournamentResult createTournamentResult(Long tournamentId, Long memberId, Long secondMemberId, int point, int place) {
        Tournament tournament = tournamentDao.getReferenceById(tournamentId);
        Member member = memberDao.getReferenceById(memberId);
        Member secondMember = memberDao.getReferenceById(secondMemberId);

        TournamentResult tournamentResult = new TournamentResult.Builder()
                .tournament(tournament)
                .member(member)
                .secondMember(secondMember)
                .point(point)
                .place(place)
                .build();

        return tournamentResultDao.save(tournamentResult);
    }

    @Transactional
    public void setMemberToPlace(Long tournamentId, int place, Long memberId) {
        TournamentResult tournamentResult = tournamentResultDao.findByTournamentAndPlace(tournamentDao.getReferenceById(tournamentId), place);
        tournamentResult.setMember(memberDao.getReferenceById(memberId));
        persist(tournamentResult);
    }

    @Transactional
    public void setSecondMemberToPlace(Long tournamentId, int place, Long memberId) {
        TournamentResult tournamentResult = tournamentResultDao.findByTournamentAndPlace(tournamentDao.getReferenceById(tournamentId), place);
        tournamentResult.setSecondMember(memberDao.getReferenceById(memberId));
        persist(tournamentResult);
    }

    @Transactional
    public void setTwoMembersToPlace(Long tournamentId, int place, Long memberId, Long secondMemberId) {
        TournamentResult tournamentResult = tournamentResultDao.findByTournamentAndPlace(tournamentDao.getReferenceById(tournamentId), place);
        tournamentResult.setMember(memberDao.getReferenceById(memberId));
        persist(tournamentResult);
        TournamentResult sectournamentResult = tournamentResultDao.findByTournamentAndPlace(tournamentDao.getReferenceById(tournamentId), place);
        sectournamentResult.setSecondMember(memberDao.getReferenceById(secondMemberId));
        persist(sectournamentResult);
    }

    @Transactional
    public void createTournamentResultsArray(List<TournamentResult> tournamentResults, Long tournamentId) {
        for (TournamentResult tournamentResult : tournamentResults) {
            tournamentResult.setTournament(tournamentDao.getReferenceById(tournamentId));
            tournamentResultDao.save(tournamentResult);
        }
    }

    @Transactional
    public TournamentResult findByTournamentAndPlace(Tournament tournament, int place) {
        return tournamentResultDao.findByTournamentAndPlace(tournament, place);
    }

    @Transactional(readOnly = true)
    public List<TournamentResult> getAllTournamentResultsForMember(int memberId) {
        return tournamentResultDao.findAllByMember_Id(memberId);
    }

    @Transactional
    public void persist(TournamentResult tournamentResult) {
        Objects.requireNonNull(tournamentResult);
        tournamentResultDao.save(tournamentResult);
    }

    @Transactional
    public List<TournamentResult> getAllTournamentResults() {
        return tournamentResultDao.findAll();
    }

    @Transactional(readOnly = true)
    public TournamentResult getTournamentResultById(Long tournamentId) {
        return tournamentResultDao.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("TournamentResult not found with id: " + tournamentId));
    }

    @Transactional
    public void updateTournamentResult(Long tournamentId, TournamentResult updatedTournament) {
        TournamentResult tournament = getTournamentResultById(tournamentId);
        tournament.setTournament(updatedTournament.getTournament());
        tournament.setMember(updatedTournament.getMember());
        tournament.setSecondMember(updatedTournament.getSecondMember());
        tournament.setPoint(updatedTournament.getPoint());
        tournament.setPlace(updatedTournament.getPlace());
        tournamentResultDao.save(tournament);
    }

    @Transactional
    public void deleteTournamentResult(Long tournamentResultId) {
        TournamentResult tournamentResult = getTournamentResultById(tournamentResultId);
        tournamentResultDao.delete(tournamentResult);
    }
}
