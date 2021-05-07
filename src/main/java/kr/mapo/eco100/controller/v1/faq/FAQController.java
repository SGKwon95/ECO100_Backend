package kr.mapo.eco100.controller.v1.faq;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mapo.eco100.controller.v1.faq.dto.FAQ;
import kr.mapo.eco100.controller.v1.faq.dto.FAQJeju;
import kr.mapo.eco100.service.FAQService;
import lombok.RequiredArgsConstructor;

@Api(value = "FAQ 정보 조회하기")
@RestController("/FAQ")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService service;
    
    @ApiOperation(value = "FAQ 제주")
    @GetMapping("/faq/jeju")
    public ResponseEntity<List<FAQJeju>> getFAQJeju() {

        return ResponseEntity.ok(service.getFAQlistJeju());
    }

    @ApiOperation(value = "FAQ")
    @GetMapping("/faq")
    public ResponseEntity<List<FAQ>> getFAQ() {

        return ResponseEntity.ok(service.getFAQlist());
    }
}
