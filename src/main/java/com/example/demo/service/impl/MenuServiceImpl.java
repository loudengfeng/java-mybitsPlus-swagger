package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.MenuDao;
import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * (Menu)表服务实现类
 *
 * @author ldf
 * @since 2021-08-12 11:05:15
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

}
