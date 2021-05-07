package kr.mapo.eco100.controller.v1.faq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FAQ {
    private Integer id;
    private String category;
    private String question;
    private String answer;
}
