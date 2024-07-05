package fel.cvut.cz.ear.serviceTest;

import fel.cvut.cz.ear.dao.ClubDao;
import fel.cvut.cz.ear.dao.MemberDao;
import fel.cvut.cz.ear.enviroment.Generator;
import fel.cvut.cz.ear.model.Club;
import fel.cvut.cz.ear.service.ClubService;
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
public class ClubServiceTest {

    @Mock
    private MemberDao memberDaoMock;

    @Mock
    private ClubDao clubDaoMock;

    @InjectMocks
    private ClubService sut;

    @Test
    public void createClubTest() {
        Club club = Generator.generetaClub();
        sut.persist(club);

        final ArgumentCaptor<Club> captor = ArgumentCaptor.forClass(Club.class);
        verify(clubDaoMock).save(captor.capture());
        assertNotNull(captor.getValue().getName());
    }
}