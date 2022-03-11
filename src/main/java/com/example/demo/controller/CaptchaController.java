package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.demo.core.lang.Base64;
import com.example.demo.redis.RedisCache;
import com.example.demo.utils.IdUtil;
import com.example.demo.utils.VerifyCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Api(tags = {"验证码"})
@RestController
public class CaptchaController
{
    @Autowired
    private RedisCache redisCache;

  @ApiOperation(value = "验证码", httpMethod = "GET")
    @GetMapping("/captchaImage")
    public R register() throws IOException {
      // 生成随机字串
      String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
      // 唯一标识
      String uuid = IdUtil.simpleUUID();
      String verifyKey = "captcha_codes:" + uuid;
      redisCache.setCacheObject(verifyKey, verifyCode, 2, TimeUnit.MINUTES);
      // 生成图片
      int w = 111, h = 36;
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      VerifyCodeUtil.outputImage(w,h,stream,verifyCode);
      HashMap<String, String> returnData = new HashMap<String, String>();
      returnData.put("uuid",uuid);
      returnData.put("img", Base64.encode(stream.toByteArray()));
      return R.ok(returnData);
    }
}
