package fel.cvut.cz.ear.service;

import fel.cvut.cz.ear.dao.MemberDao;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberDao.findByLogin(username);

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                getAuthoritiesFromRoles(user.getRole())
        );
    }


    private static Set<SimpleGrantedAuthority> getAuthoritiesFromRoles(Role role) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            authorities.add(new SimpleGrantedAuthority(role.name()));


        return authorities;
    }
}
