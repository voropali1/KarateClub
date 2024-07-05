package fel.cvut.cz.ear.dao;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Tournament;
import org.springframework.stereotype.Repository;

import fel.cvut.cz.ear.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentDao extends JpaRepository<Tournament, Long> {
}