package kr.mapo.eco100.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mapo.eco100.controller.v1.shop.dto.Shop;
import kr.mapo.eco100.repository.ShopRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository repository;
    
    public List<Shop> getShoplist() {
        return repository.getShoplist();
    }
}
