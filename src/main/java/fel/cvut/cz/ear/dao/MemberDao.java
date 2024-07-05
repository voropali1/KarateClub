package fel.cvut.cz.ear.dao;

import fel.cvut.cz.ear.model.Member;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import fel.cvut.cz.ear.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {

    List<Member> findByLevel(int level);

    Member findByLogin(String login);

    Member findByName(String name);

    Optional<Object> findByEmail(String email);
}