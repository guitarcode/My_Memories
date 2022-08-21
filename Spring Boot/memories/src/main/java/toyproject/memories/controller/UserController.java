package toyproject.memories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.MemberSignupDto;
import toyproject.memories.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;

    @PostMapping("/member")
    public Result signup(@Valid @RequestBody MemberSignupDto memberSignupDto){
        User user = memberService.signup(memberSignupDto);
        return new Result(resultEnum.SUCCESS, user);
    }
}
