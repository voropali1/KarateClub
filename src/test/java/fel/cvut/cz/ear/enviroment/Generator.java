package fel.cvut.cz.ear.enviroment;

import fel.cvut.cz.ear.model.*;
import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static int randomInt(int max) {
        return RAND.nextInt(max);
    }


    public static int randomInt(int min, int max) {
        assert min >= 0;
        assert min < max;

        int result;
        do {
            result = randomInt(max);
        } while (result < min);
        return result;
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }
    public static Member generateMember() {
        final Member member = new Member();
        member.setName("Name" + randomInt());
        member.setAddress("LastName" + randomInt());
        member.setPhoneNumber("+" + randomInt());
        member.setEmail("username" + randomInt() + "@kbss.felk.cvut.cz");
//        member.setBalance(randomInt());
//        member.setStatus(randomBoolean());
        member.setLevel(randomInt(0, 20));
        member.setPoints(randomInt(0, 100));
        member.setLogin("Login" + randomInt());
        member.setPassword(Integer.toString(randomInt()));
        return member;
    }
    public static TournamentResult generateTournamentResult(){
        Tournament tournament = Generator.generateTournament();

        TournamentResult tournamentResult = new TournamentResult();
        tournamentResult.setTournament(tournament);
        tournamentResult.setPoint(10);
        tournamentResult.setPlace(2);
        return tournamentResult;
    }
    public static Payment generatePayment(){
        LocalDateTime localDateTime = LocalDateTime.now();

        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Payment payment = new Payment();
        payment.setPaymentDate(date);
        payment.setAmount(randomInt(1, 10000));
        payment.setPaymentMethod("method" + randomInt());
        payment.setMember(generateMember());
        return payment;
    }

    public static Payment generatePayment(Member member){
        LocalDateTime localDateTime = LocalDateTime.now();

        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Payment payment = new Payment();
        payment.setPaymentDate(date);
        payment.setAmount(randomInt());
        payment.setPaymentMethod("method" + randomInt());
        payment.setMember(member);
        return payment;
    }

    public static Club generetaClub(){
        final Club club = new Club();
        club.setName("name" + randomInt());
        club.setAddress("addres" + randomInt());
        return club;
    }
    public static Tournament generateTournament() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        final Tournament p = new Tournament();
        p.setName("Tournament " + randomInt());
        p.setDate(date);
        return p;
    }
}
