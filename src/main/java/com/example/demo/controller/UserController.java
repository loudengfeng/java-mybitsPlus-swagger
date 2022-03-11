package com.example.demo.controller;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.demo.entity.User;
import com.example.demo.redis.RedisCache;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtTokenUtilPlusJ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * (User)表控制层
 *
 * @author ldf
 * @since 2021-07-09 14:02:25
 */
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("user")
public class UserController extends ApiController {

  @Resource
  private UserService userService;
  @Resource
  private RedisCache redisCache;

  @ApiOperation("注册")
  @PostMapping("register")
  public R register(@RequestParam Integer phone, @RequestParam String password) {
    User phoneData = userService.lambdaQuery().eq(User::getPhone, phone).one();
    if(phoneData != null){
      return R.failed("手机号码已经注册过了");
    }
    User user = new User();
    user.setPhone(phone);
    user.setPsd(password);
    user.setCreate_time(new Date());
    boolean save = userService.save(user);
    return R.ok(save);
  }

  @PostMapping("login")
  @ApiOperation(value = "登录", httpMethod = "POST")
  @ApiImplicitParams({
    @ApiImplicitParam(name="phone",value="手机号码",dataType="int",required=true,paramType = "query"),
    @ApiImplicitParam(name="password",value="密码",dataType="String",required=true,paramType = "query"),
    @ApiImplicitParam(name="uuid",value="uuid",dataType="String",required=true,paramType = "query"),
    @ApiImplicitParam(name="captchaImage",value="验证码",dataType="String",required=true,paramType = "query")
  })
  public R<Object> login(@RequestParam Integer phone, @RequestParam String password,@RequestParam String uuid, @RequestParam String captchaImage) {
    User userInfo = userService.lambdaQuery().eq(User::getPhone, phone).one();
    if(userInfo == null){
      return R.failed("用户不存在");
    }
    if(!password.equals(userInfo.getPsd())){
      return R.failed("密码不正确");
    }
    if(captchaImage == null){
      return R.failed("请输入验证码");
    }
    try{
      String verifyKey = "captcha_codes:" + uuid;
      String captcha = redisCache.getCacheObject(verifyKey);
      redisCache.deleteObject(verifyKey);
      boolean isCaptcha = captchaImage.equalsIgnoreCase(captcha);
      if(!isCaptcha){
        return R.failed("验证码不正确");
      }
      String token = JwtTokenUtilPlusJ.generateToken(userInfo);
      String tokenCode = "token" + token;
      redisCache.setCacheObject(token, tokenCode, 2, TimeUnit.MINUTES);
      return R.ok(token);
    }catch (Exception e){
      e.printStackTrace();
      return R.failed("验证码有误");
    }
  }

  @ApiOperation("修改用户信息")
  @PostMapping("updateUser")
  public R updateUser(HttpServletRequest request,@RequestParam String oldPassword, @RequestParam String password){
    String jwtToken = request.getHeader("Authorization");
    User user = JwtTokenUtilPlusJ.parseToken(jwtToken, User.class);
    boolean isPsw = oldPassword.equals(user.getPsd());
    if(!isPsw){
      return R.failed("与原来的密码不一致");
    }
    user.setPsd(password);
    boolean updateOk = userService.updateById(user);
    return R.ok(updateOk);
  }


  @ApiOperation("获取用户信息")
  @PostMapping("getUserInfo")
  public R getUserInfo(HttpServletRequest request) {
    String jwtToken = request.getHeader("Authorization");
    User user = JwtTokenUtilPlusJ.parseToken(jwtToken, User.class);
    return R.ok(user);
  }
}
