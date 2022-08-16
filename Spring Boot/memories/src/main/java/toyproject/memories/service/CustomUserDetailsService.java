package toyproject.memories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import toyproject.memories.domain.Member;
import toyproject.memories.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String name) {
        return memberRepository.findByName(name)
                .map(member -> createUser(name, member))
                .orElseThrow(() -> new UsernameNotFoundException(name + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, Member member) {
        if (!member.isActivate()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User(member.getName(),
                member.getPassword(),
                Collections.singleton(grantedAuthority));
    }
}
