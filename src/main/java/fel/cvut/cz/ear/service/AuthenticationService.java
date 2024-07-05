package fel.cvut.cz.ear.service;



import fel.cvut.cz.ear.dao.MemberDao;
import fel.cvut.cz.ear.dto.member.AuthenticationRequest;
import fel.cvut.cz.ear.dto.member.AuthenticationResponse;
import fel.cvut.cz.ear.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MemberDao userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        Member user = (Member) userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken((UserDetails) user);
        return new AuthenticationResponse(jwtToken);
    }
}
