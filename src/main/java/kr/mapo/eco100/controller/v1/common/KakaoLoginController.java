package kr.mapo.eco100.controller.v1.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoLoginController {
    
    @GetMapping("/code")
    public String response(
            @RequestParam("code") String code
    ) {
        System.out.println(code);
        return code;
    }
}
