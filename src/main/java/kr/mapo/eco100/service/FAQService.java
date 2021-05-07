package kr.mapo.eco100.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mapo.eco100.controller.v1.faq.dto.FAQ;
import kr.mapo.eco100.controller.v1.faq.dto.FAQJeju;
import kr.mapo.eco100.repository.FAQRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository repository;

    public List<FAQJeju> getFAQlistJeju() {
        return repository.getFAQlistJeju();
    }

    public List<FAQ> getFAQlist() {
        return repository.getFAQlist();
    }
}
