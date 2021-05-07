package kr.mapo.eco100.controller.v1.faq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FAQJeju {
    private Integer id;
    private String type;
    private String name;
    private String details;
    private String precautions;
}
