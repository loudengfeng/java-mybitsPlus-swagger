package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.annotation.AccessLimit;
import com.example.demo.entity.User;
import com.example.demo.entity.Vip;
import com.example.demo.service.VipService;
import com.example.demo.utils.ExcelUtil;
import com.example.demo.utils.JwtTokenUtilPlusJ;
import com.example.demo.utils.ServiceResult;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (Vip)表控制层
 *
 * @author ldf
 * @since 2021-07-31 17:08:59
 */
@Api(tags = {"会员管理"})
@RestController
@RequestMapping("vip")
public class VipController extends ApiController {

  @Resource
  private VipService vipService;


  @AccessLimit(seconds = 1, maxCount = 10)
  @ApiOperation("分页查询所有数据")
  @PostMapping("selectVip")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", dataTypeClass = String.class),
          @ApiImplicitParam(name = "rows", value = "每页显示多少条", dataType = "Integer", required = true, dataTypeClass = String.class),
          @ApiImplicitParam(name = "name", value = "名字", dataType = "String", required = false, dataTypeClass = String.class),
          @ApiImplicitParam(name = "address", value = "地址", dataType = "String", required = false, dataTypeClass = String.class)
  })
  public ServiceResult selectVip(HttpServletRequest request, @RequestParam Integer page,
                                 @RequestParam Integer rows, @RequestParam(required = false) String name, @RequestParam(required = false) String address) {
    String jwtToken = request.getHeader("Authorization");
    User user = JwtTokenUtilPlusJ.parseToken(jwtToken, User.class);
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String format = df.format(date);
    System.out.println(format);
    Page<Vip> vipPage=new Page<>(page,rows);
    QueryWrapper<Vip> vipQueryWrapper = new QueryWrapper<>();
    vipQueryWrapper.lambda().eq(Vip::getUserid,user.getId());
    if(StrUtil.isNotBlank(name)){
      vipQueryWrapper.lambda().like(Vip::getVipname, name.trim());
    }
    if(StrUtil.isNotBlank(address)) {
      vipQueryWrapper.lambda().like(Vip::getAddress, address.trim());
    }
    vipQueryWrapper.lambda().orderByDesc(Vip::getId);
    vipQueryWrapper.select("id","vipName","create_time","phone","buyNUm","address");
    Page<Vip> pageData = vipService.page(vipPage, vipQueryWrapper);
    ArrayList<HashMap<String, String>> sites = new ArrayList<>();
    Calendar instance = Calendar.getInstance();
    int day = instance.get(Calendar.DATE);
    int Month = instance.get(Calendar.MONTH) + 1;
    int actualMaximum = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
    for (int j=0;j<actualMaximum;j++){
      int aa = j + 1;
      String monthDay = String.valueOf(Month)+ "月"  + String.valueOf(aa) + "日";
      HashMap<String, String> objectObjectHashMap = new HashMap<>();
      if(j < day){
        objectObjectHashMap.put("grade","1");
        objectObjectHashMap.put("date",monthDay);
      }else{
        objectObjectHashMap.put("grade","0");
        objectObjectHashMap.put("date",monthDay);
      }
      sites.add(objectObjectHashMap);
    }
    pageData.getRecords().forEach(t->t.setUserInfo(sites));
    return  ServiceResult.success(pageData,request);
  }

  @ApiOperation("通过主键查询单条数据")
  @PostMapping("selectVipDetail")
  public R<?> selectVipDetail(@RequestParam Serializable id) {
    Vip vipInfo = vipService.getById(id);
    int count = vipService.count();
    vipInfo.setTotal(count);
    return success(vipInfo);
  }

  @ApiOperation("新增数据")
  @PostMapping("addVip")
  public R<?> addVip(HttpServletRequest request,@RequestParam String name,@RequestParam String phone,@RequestParam Integer buyNum,@RequestParam(required = false) String address) {
    User user = JwtTokenUtilPlusJ.parseToken(request.getHeader("Authorization"), User.class);
    Vip vip = new Vip();
    vip.setAddress(address);
    vip.setBuynum(buyNum);
    vip.setPhone(phone);
    vip.setUserid(user.getId());
    vip.setVipname(name);
    vip.setCreateTime(new Date());
    boolean saveSuccess = vipService.save(vip);
    return R.ok(saveSuccess);
  }

    @ApiOperation("新增数据(测试)")
    @PostMapping("addVipTest")
    public R<?> addVipTest(HttpServletRequest request,@RequestBody Vip vipService){
        return R.ok(this.vipService.save(vipService));
    }

  @ApiOperation("修改数据")
  @PostMapping("updateVip")
  public R<?> updateVip(HttpServletRequest request,@RequestParam String name,@RequestParam String phone,@RequestParam Integer buyNum,@RequestParam String address,@RequestParam String id) {
    User user = JwtTokenUtilPlusJ.parseToken(request.getHeader("Authorization"), User.class);
    LambdaQueryWrapper<Vip> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Vip::getUserid,user.getId());
    wrapper.eq(Vip::getId, id);
    Vip vipUserInfo = vipService.getOne(wrapper);
    if(vipUserInfo == null){
      return R.failed("没有这条数据,修改失败");
    }
    vipUserInfo.setVipname(name);
    vipUserInfo.setPhone(phone);
    vipUserInfo.setAddress(address);
    vipUserInfo.setBuynum(buyNum);
    vipUserInfo.setCreateTime(new Date());
    boolean vipUserInfoFlag = vipService.updateById(vipUserInfo);
    return  R.ok(vipUserInfoFlag);
  }

  @ApiOperation("删除数据")
  @PostMapping("deleteVip")
  public R<?> deleteVip(@RequestParam Serializable id) {
    return success(this.vipService.removeById(id));
  }


  @ApiOperation("导出数据")
  @GetMapping("export")
//  @ResponseBody
  public void export(HttpServletRequest request, HttpServletResponse response ,@RequestParam(required = false) String name)
  {
    String jwtToken = request.getHeader("Authorization");
    User user = JwtTokenUtilPlusJ.parseToken(jwtToken, User.class);
    LambdaQueryWrapper<Vip> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Vip::getUserid,user.getId());
    if(StrUtil.isNotBlank(name)){
      wrapper.like(Vip::getVipname,name.trim());
    }
    List<Vip> list = vipService.list(wrapper);
    ExcelUtil<Vip> excelUtil = new ExcelUtil<>(Vip.class);
    //要导出的数据集
    excelUtil.exportExcel(list, "userInformation.xlsx", response);
  }

  @ApiOperation("导入数据")
  @PostMapping("importExcel")
  public R<?> importExcel(HttpServletRequest request,@RequestParam(value = "file") MultipartFile file) throws Exception {
    User user = JwtTokenUtilPlusJ.parseToken(request.getHeader("Authorization"), User.class);
    if(!file.isEmpty()){
      ExcelUtil<Vip> excelUtil = new ExcelUtil<>(Vip.class);
      byte [] byteArr = file.getBytes();
      InputStream inputStream = new ByteArrayInputStream(byteArr);
      final List<Vip> vips = excelUtil.importExcel(inputStream);
      Vip vip = new Vip();
      for(int i = 0; i < vips.size(); i ++){
        vip.setAddress(vips.get(i).getAddress());
        vip.setBuynum(vips.get(i).getBuynum());
        vip.setPhone(vips.get(i).getPhone());
        vip.setVipname(vips.get(i).getVipname());
        vip.setCreateTime(new Date());
        vip.setUserid(user.getId());
        vipService.save(vip);
      }
    }
    return R.ok(null);
  }
}
