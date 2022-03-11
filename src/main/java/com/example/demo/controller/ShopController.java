package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Shop;
import com.example.demo.service.ShopService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Shop)表控制层
 *
 * @author ldf
 * @since 2022-02-22 16:09:43
 */
@Api(tags = {"商品管理"})
@RestController
@RequestMapping("shop")
public class ShopController extends ApiController {

    @Resource
    private ShopService shopService;

    @ApiOperation("分页查询所有数据")
    @PostMapping("selectAll")
    public R<?> selectAll(@RequestParam Integer page,
                          @RequestParam Integer rows) {
        return success(this.shopService.page(new Page<>(page, rows)));
    }

    @ApiOperation("通过主键查询单条数据")
    @PostMapping("selectOne")
    public R<?> selectOne(@RequestParam Serializable id) {
        return success(this.shopService.getById(id));
    }

    @ApiOperation("新增数据")
    @PostMapping("insert")
    public R<?> insert(@RequestBody Shop shop) {
        return success(this.shopService.save(shop));
    }

    @ApiOperation("修改数据")
    @PostMapping("update")
    public R<?> update(@RequestBody Shop shop) {
        return success(this.shopService.updateById(shop));
    }

    @ApiOperation("删除数据")
    @PostMapping("delete")
    public R<?> delete(@RequestParam Serializable id) {
        return success(this.shopService.removeById(id));
    }
}
