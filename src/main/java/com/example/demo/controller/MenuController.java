package com.example.demo.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.utils.RankUtil.getChangeChildList;

/**
 * (Menu)表控制层
 *
 * @author ldf
 * @since 2021-08-12 11:05:15
 */
@Api(tags = {"菜单管理"})
@RestController
@RequestMapping("menu")
public class MenuController extends ApiController {

  @Resource
  private MenuService menuService;

  @ApiOperation("分页查询所有数据")
  @PostMapping("selectAll")
  public R<?> selectAll(@RequestParam Integer page,
                        @RequestParam Integer rows) {
    Page<Menu> menuPage = menuService.page(new Page<>(page, rows));
    List<Menu> menuList = menuPage.getRecords();
    menuList.forEach(entiy -> {
        String formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entiy.getCreateTime());
        entiy.setDealTime(formatTime);
    });
    ArrayList<Menu> firstMenuList = new ArrayList<>();
    for(int i = 0; i < menuList.size();i++){
      if(menuList.get(i).getBelong().equals("0")){
        firstMenuList.add(menuList.get(i));
      }
    }
    for (Menu menu : firstMenuList){
      List<Menu> childList =  getChangeChildList(String.valueOf(menu.getId()),menuList);
      menu.setChildList(childList);
    }
    return success(firstMenuList);
  }

  @ApiOperation("通过主键查询单条数据")
  @PostMapping("selectOne")
  public R<?> selectOne(@RequestParam Serializable id) {
    return success(this.menuService.getById(id));
  }

  @ApiOperation("新增数据")
  @PostMapping("insert")
  public R<?> insert(@RequestParam String parentId,@RequestParam String menuName,@RequestParam String url,@RequestParam String sort,@RequestParam String outreach,@RequestParam String component,@RequestParam String status) {
    if(!StrUtil.isNotBlank(menuName)){
      return failed("路由名称不能为空");
    }
    if(!StrUtil.isNotBlank(url)){
      return failed("路由地址不能为空");
    }
    if(!StrUtil.isNotBlank(sort)){
      return failed("排序必填");
    }
    if(!StrUtil.isNotBlank(component)){
      return  failed("组件必填");
    }
    Menu menu = new Menu();
    menu.setBelong(parentId);
    menu.setUrl(url);
    menu.setMenuName(menuName);
    menu.setOutreach(outreach);
    menu.setComponent(component);
    menu.setSort(sort);
    menu.setCreateTime(new Date());
    menu.setStatus(status);
    boolean saveSuccess = menuService.save(menu);
    return success(saveSuccess);
  }

  @ApiOperation("修改数据")
  @PostMapping("update")
  public R<?> update(@RequestBody Menu menu) {
    return success(this.menuService.updateById(menu));
  }

  @ApiOperation("删除数据")
  @PostMapping("delete")
  public R<?> delete(@RequestParam Serializable id) {
    return success(this.menuService.removeById(id));
  }
}
