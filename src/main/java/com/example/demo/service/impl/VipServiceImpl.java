package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.VipDao;
import com.example.demo.entity.Vip;
import com.example.demo.service.VipService;
import org.springframework.stereotype.Service;

/**
 * (Vip)表服务实现类
 *
 * @author ldf
 * @since 2021-07-31 17:29:00
 */
@Service("vipService")
public class VipServiceImpl extends ServiceImpl<VipDao, Vip> implements VipService {

}
