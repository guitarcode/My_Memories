package toyproject.memories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.Authority;
import toyproject.memories.domain.Member;
import toyproject.memories.dto.MemberSignupDto;
import toyproject.memories.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Member signup(MemberSignupDto memberSignupDto){
        if(memberRepository.findByName(memberSignupDto.getName()).orElse(null) != null){
            throw new RuntimeException("이미 가입되어있는 유저입니다.");
        }

        Member member = new Member();
        member.setName(memberSignupDto.getName());
        member.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));
        member.setBirthDate(memberSignupDto.getBirthDate());
        member.setAuthority(Authority.ROLE_USER);
        member.setActivate(true);

        return memberRepository.saveMember(member);
    }
}
