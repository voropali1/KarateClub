package fel.cvut.cz.ear.serviceTest;

import fel.cvut.cz.ear.dao.MemberDao;
import fel.cvut.cz.ear.dao.TournamentResultDao;
import fel.cvut.cz.ear.enviroment.Generator;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.TournamentResult;
import fel.cvut.cz.ear.service.TournamentResultService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TournamentResultServiceTest {

    @Mock
    private TournamentResultDao tournamentResultDaoMock;

    @Mock
    private MemberDao memberDaoMock;

    @InjectMocks
    private TournamentResultService sut;

    @Test
    public void createTournamentResultsTest() {
        TournamentResult tournamentResult = Generator.generateTournamentResult();
        sut.persist(tournamentResult);

        final ArgumentCaptor<TournamentResult> captor = ArgumentCaptor.forClass(TournamentResult.class);
        verify(tournamentResultDaoMock).save(captor.capture());
        assertNotNull(captor.getValue().getTournament());
    }

    @Test
    public void createTournamentResultsTestThatMemberIsNull() {
        TournamentResult tournamentResult = Generator.generateTournamentResult();
        sut.persist(tournamentResult);

        final ArgumentCaptor<TournamentResult> captor = ArgumentCaptor.forClass(TournamentResult.class);
        verify(tournamentResultDaoMock).save(captor.capture());
        assertNull(captor.getValue().getMember());
    }

    @Test
    public void addMemberToTournamentResult() {
        TournamentResult tournamentResult = Generator.generateTournamentResult();
        tournamentResult = addMember(tournamentResult);
        sut.persist(tournamentResult);

        final ArgumentCaptor<TournamentResult> captor = ArgumentCaptor.forClass(TournamentResult.class);
        verify(tournamentResultDaoMock).save(captor.capture());
        assertNotNull(captor.getValue().getMember());
    }

    public TournamentResult addMember(TournamentResult tournamentResult) {
        Member member = Generator.generateMember();
        member.setPoints(member.getPoints() + tournamentResult.getPoint());
        tournamentResult.setMember(member);
        return tournamentResult;
    }
}