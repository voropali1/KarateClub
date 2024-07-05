package fel.cvut.cz.ear.dao;

import fel.cvut.cz.ear.Main;
import fel.cvut.cz.ear.enviroment.Generator;

import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.service.SystemInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ComponentScan(basePackageClasses = Main.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class)})
@ActiveProfiles("test")
public class MemberDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MemberDao sut;

    @Test
    public void findAllReturnsAllUsers() {
        Member user;
        for(int i = 0; i < 5; i++) {
            user = Generator.generateMember();
            em.persist(user);
        }

        final List<Member> result = sut.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    public void findByUsernameReturnsPersonWithMatchingUsername() {
        final Member user = Generator.generateMember();
        em.persist(user);

        final Member result = sut.findByLogin(user.getLogin());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void findByNameReturnsNullForUnknownName() {
        assertNull(sut.findByName("unknownUsername"));
    }



    @Test
    public void findByName() {
        Member member = Generator.generateMember();
        member.setName("123");
        sut.save(member);
        assertEquals("123",sut.findByName("123").getName());
    }
}
