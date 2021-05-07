package kr.mapo.eco100.controller.v1.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Shop {
    private Integer id;
    private String name;
    private String address;
    private String phone_num;
    private String running_info;
    private String web_url;
    private Float latitude;
    private Float longitude;
    private String img_url;
    private String logo_url;
}
