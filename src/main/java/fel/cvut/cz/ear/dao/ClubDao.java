package fel.cvut.cz.ear.dao;

import fel.cvut.cz.ear.model.Club;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Notification;
import org.springframework.stereotype.Repository;

import fel.cvut.cz.ear.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubDao extends JpaRepository<Club, Long> {
}