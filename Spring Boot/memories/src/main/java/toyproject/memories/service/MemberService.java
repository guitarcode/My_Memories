package toyproject.memories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.user.Authority;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.MemberSignupDto;
import toyproject.memories.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User signup(MemberSignupDto memberSignupDto){
        if(userRepository.findByName(memberSignupDto.getName()).orElse(null) != null){
            throw new RuntimeException("이미 가입되어있는 유저입니다.");
        }

        User user = new User();
        user.setName(memberSignupDto.getName());
        user.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));
        user.setBirthDate(memberSignupDto.getBirthDate());
        user.setAuthority(Authority.ROLE_USER);
        user.setActivate(true);

        return userRepository.saveMember(user);
    }
}
