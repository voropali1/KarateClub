package fel.cvut.cz.ear.service;

import fel.cvut.cz.ear.dao.MemberDao;
import fel.cvut.cz.ear.dao.PaymentDao;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
public class PaymentService {

    private final MemberDao memberDao;
    private final PaymentDao paymentDao;

    @Autowired
    public PaymentService(MemberDao memberDao, PaymentDao paymentDao) {
        this.memberDao = memberDao;
        this.paymentDao = paymentDao;
    }

//    @Transactional
//    public boolean topUpBalanceByCard(Member member, double amount) {
//        try {
//            if (member != null) {
//                LocalDateTime localDateTime = LocalDateTime.now();
//                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//
//                Payment payment = new Payment();
//                payment.setMember(member);
//                payment.setPaymentMethod("Card");
//                payment.setAmount(amount);
//                payment.setPaymentDate(date);
//
//                double currentBalance = member.getBalance();
//                double newBalance = currentBalance + amount;
//                member.setBalance(newBalance);
//
//                memberDao.save(member);
//                paymentDao.save(payment);
//                return true;
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    @Transactional
    public void persist(Payment payment) {
        Objects.requireNonNull(payment);
        paymentDao.save(payment);
    }
}