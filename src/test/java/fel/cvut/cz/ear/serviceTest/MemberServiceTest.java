//package fel.cvut.cz.ear.serviceTest;
//
//import fel.cvut.cz.ear.dao.MemberDao;
//import fel.cvut.cz.ear.enviroment.Generator;
//import fel.cvut.cz.ear.model.Member;
//import fel.cvut.cz.ear.model.Role;
//import fel.cvut.cz.ear.service.MemberService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static java.util.regex.Pattern.matches;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//@ExtendWith(MockitoExtension.class)
//public class MemberServiceTest {
//
//    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Mock
//    private MemberDao memberDaoMock;
//
//    private MemberService sut;
//
//    @BeforeEach
//    public void setUp() {
//        this.sut = new MemberService(memberDaoMock, passwordEncoder);
//    }
//
//    @Test
//    public void persistEncodesUserPassword() {
//        final Member member = Generator.generateMember();
//        final String rawPassword = member.getAddress();
//        sut.persist(member);
//
//        final ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
//        verify(memberDaoMock).save(captor.capture());
//        assertTrue(matches(rawPassword, captor.getValue().getAddress()));
//    }
//
//    @Test
//    public void initializeAdminTest(){
//        final Member member = Generator.generateMember();
//        sut.initializeAdmin(member);
//
//        final ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
//        verify(memberDaoMock).save(captor.capture());
//
//        assertEquals(Role.ADMIN, captor.getValue().getRole());
//
//        // Optional: Check other properties if needed
//        assertEquals(member.getAddress(), captor.getValue().getAddress());
//        // Add other assertions as needed
//    }
//
//
//
//    @Test
//    public void persistCreateClubForMember() {
//        final Member member = Generator.generateMember();
//        member.setClub(Generator.generetaClub());
//        sut.persist(member);
//
//        final ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
//        verify(memberDaoMock).save(captor.capture());
//        assertNotNull(captor.getValue().getClub());
//    }
//
//    @Test
//    public void persistSetsUserRoleToDefaultWhenItIsNotSpecified() {
//        final Member member = Generator.generateMember();
//        sut.persist(member);
//
//        final ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
//        verify(memberDaoMock).save(captor.capture());
//        assertEquals(Role.MEMBER, captor.getValue().getRole());
//    }
//}
