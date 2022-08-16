package toyproject.memories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.memories.domain.Member;
import toyproject.memories.dto.MemberSignupDto;
import toyproject.memories.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public Result signup(@Valid @RequestBody MemberSignupDto memberSignupDto){
        Member member = memberService.signup(memberSignupDto);
        return new Result(SuccessOrFail.SUCCESS, member);
    }
}
