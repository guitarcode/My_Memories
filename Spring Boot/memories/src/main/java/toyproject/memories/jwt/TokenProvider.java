package toyproject.memories.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds;

    private Key key;

    //토큰 프로바이더 생성자
    //secret키와 토큰 유효시간을 초기화해주는 것을 보아 토큰에 정보를 부여하고 인코딩 해주기 위한 클래스 생성자이다.
    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInseconds){
            this.secret = secret;
            this.tokenValidityInMilliseconds = tokenValidityInseconds * 1000;
    }

    //토큰 프로바이더 클래스가 생성되어 빈에 등록된 후 key 변수를 할당하기 위한 Override
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    //Authentication 객체를 인자로 받아 jwt 토큰을 만든다.
    //Authentication 은 로그인 정보, 즉 사용자의 정보라고 볼 수 있다. 그러므로 Authority 정보도 해당 객체에 담겨있을 것이다.
    //유저 정보와 Authority 정보를 담아 토큰을 만드는 것인 것 같다.
    public String createToken(Authentication authentication){
        System.out.println("토큰을 생성합니다.");

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    //token을 인자로 받아 디코딩하여 유저 객체를 생성하고 해당 유저가 어떤 Authorrity를 가지고 있는지 확인한다.
    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println("토큰으로부터 권한정보를 불러옵니다.");

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User prinsipal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(prinsipal, token, authorities);
    }

    //토큰의 유효성을 검증하고 발생하는 예외에 대해 예외처리를 한다.
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println("토큰이 유효합니다.");
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}

