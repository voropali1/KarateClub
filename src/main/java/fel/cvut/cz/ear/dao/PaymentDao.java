package fel.cvut.cz.ear.dao;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Payment;
import org.springframework.stereotype.Repository;

import fel.cvut.cz.ear.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Long> {
}