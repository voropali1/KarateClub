package fel.cvut.cz.ear.service;


import fel.cvut.cz.ear.Main;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.MemberDTO;
import fel.cvut.cz.ear.model.Role;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    /**
     * Default admin username
     */


    private final MemberService userService;

    private final PlatformTransactionManager txManager;

    @Autowired
    public SystemInitializer(MemberService userService,
                             PlatformTransactionManager txManager) {
        this.userService = userService;
        this.txManager = txManager;
    }

    @PostConstruct
    private void initSystem() {
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
//            generateAdmin();
            return null;
        });
    }

    /**
     * Generates an admin account if it does not already exist.
     */
    private void generateAdmin() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("admin");
        memberDTO.setAddress("admin");
        memberDTO.setPhoneNumber("admin");
        memberDTO.setEmail("admin");
        memberDTO.setPoints(3);
        memberDTO.setLogin("admin");
        memberDTO.setPassword("admin");
        memberDTO.setRole(Role.ADMIN);
        LOG.info("Generated admin user with credentials " + memberDTO.getName() + "/" + memberDTO.getPassword());
        userService.persist(memberDTO); // Предположим, что у вас есть метод userService.persist() для MemberDTO
    }

}