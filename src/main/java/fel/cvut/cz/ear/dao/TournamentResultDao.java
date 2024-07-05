package fel.cvut.cz.ear.dao;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Tournament;
import fel.cvut.cz.ear.model.TournamentResult;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import fel.cvut.cz.ear.model.TournamentResult;
import jakarta.persistence.TypedQuery;
import java.util.List;

import fel.cvut.cz.ear.model.TournamentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentResultDao extends JpaRepository<TournamentResult, Long> {
    List<TournamentResult> findAllByMember_Id(int memberId);

    TournamentResult findByTournamentAndPlace(Tournament tournament, int place);

    Integer findPointsByTournamentAndPlace(Tournament tournament, int place);
}