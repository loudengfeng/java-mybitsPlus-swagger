package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.ShopDao;
import com.example.demo.entity.Shop;
import com.example.demo.service.ShopService;
import org.springframework.stereotype.Service;

/**
 * (Shop)表服务实现类
 *
 * @author ldf
 * @since 2022-02-22 16:09:43
 */
@Service("shopService")
public class ShopServiceImpl extends ServiceImpl<ShopDao, Shop> implements ShopService {

}
