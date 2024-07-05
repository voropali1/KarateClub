package fel.cvut.cz.ear.serviceTest;

import fel.cvut.cz.ear.enviroment.Generator;
import fel.cvut.cz.ear.dao.MemberDao;
import fel.cvut.cz.ear.dao.PaymentDao;
import fel.cvut.cz.ear.model.*;
import fel.cvut.cz.ear.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private MemberDao memberDaoMock;

    @Mock
    private PaymentDao paymentDaoMock;

    @InjectMocks
    private PaymentService sut;

    @Test
    public void createClubTest() {
        Payment payment = Generator.generatePayment();
        sut.persist(payment);

        final ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentDaoMock).save(captor.capture());
        assertNotNull(captor.getValue().getAmount());
    }

//    @Test
//    public void topUpBalanceTest() {
//        Member member = Generator.generateMember();
//        double money = member.getBalance();
//
//        assertTrue(sut.topUpBalanceByCard(member, 10));
//    }
}