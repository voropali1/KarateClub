package fel.cvut.cz.ear.serviceTest;

import fel.cvut.cz.ear.dao.TournamentDao;
import fel.cvut.cz.ear.dao.TournamentResultDao;
import fel.cvut.cz.ear.enviroment.Generator;
import fel.cvut.cz.ear.model.Tournament;
import fel.cvut.cz.ear.service.TournamentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceTest {

    @Mock
    private TournamentDao tournamentDaoMock;

    @Mock
    private TournamentResultDao tournamentResultDaoMock;

    @InjectMocks
    private TournamentService sut;

    @Test
    public void createTournamentTest() {
        Tournament tournament = Generator.generateTournament();
        sut.persist(tournament);

        final ArgumentCaptor<Tournament> captor = ArgumentCaptor.forClass(Tournament.class);
        verify(tournamentDaoMock).save(captor.capture());
        assertNotNull(captor.getValue().getName());
    }
}