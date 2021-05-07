package kr.mapo.eco100.controller.v1.shop;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mapo.eco100.controller.v1.shop.dto.Shop;
import kr.mapo.eco100.service.ShopService;
import lombok.RequiredArgsConstructor;

@Api(value = "제로웨이스트 샵 정보 조회하기")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService service;

    @ApiOperation(value = "모두 불러오기")
    @GetMapping("/shops")
    public ResponseEntity<List<Shop>> getShoplist() {

        return ResponseEntity.ok(service.getShoplist());
    }
}
