package com.example.demo.utils;
//package com.ciih.authcenter.client.util.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lenovo
 */
@Slf4j
@Component
public class JwtTokenUtilPlusJ {
  /**
   * 盐
   */
  public static String SECRET = "authcenter";
  private static final long EXPIRATION = 28800000L;

  /*生成token*/
  public static <T> String generateToken(T t) {
    Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    Date now = new Date();
    Map<String, Object> map = new HashMap<>();
    map.put("alg", "HS256");
    map.put("typ", "JWT");
    JWTCreator.Builder token = JWT.create()
      .withHeader(map)
      .withExpiresAt(expireDate)
      .withIssuedAt(now)
      .withNotBefore(now);

    token.withClaim("data",JSON.toJSONString(t));
    return token.sign(Algorithm.HMAC256(SECRET));
  }

  /*解析token*/
  public static <T> T parseToken(String token, Class<T> aclass) {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
    DecodedJWT jwt = verifier.verify(token);
    Map<String, Claim> claims = jwt.getClaims();
    String string = claims.get("data").asString();
    T t = JSON.parseObject(string, aclass);
    log.info("解析Token的内容:" + t);
    return t;
  }

//    public static void main(String[] args) {
//        ArrayList<Map<String,Object>> list = new ArrayList<>();
//        HashMap<String, Object> hashMap = new HashMap<>();
//        HashMap<String, Object> hashMap1 = new HashMap<>();
//        hashMap.put("name", "怕科技的首付款");
//        hashMap.put("nam1e", "怕科技的首付款");
//        hashMap.put("na1me", "怕科技的首付款");
//        hashMap.put("na2me", "怕科技的首付款");
//        hashMap.put("na3me", "怕科技的首付款");
//        hashMap.put("na4me", "怕科技的首付款");
//        hashMap1.put("pas1sword", "水电煤气");
//        hashMap1.put("pas3sword", "水电煤气");
//        hashMap1.put("pass2word", "水电煤气");
//        hashMap1.put("pa4ssword", "水电煤气");
//        hashMap1.put("pa57ssword", "水电煤气");
//        hashMap1.put("passw8ord", "水电煤气");
//        list.add(hashMap);
//        list.add(hashMap1);
//        String s = generateToken(list);
//        System.out.println(s);
//        ArrayList arrayList = parseToken(s, ArrayList.class);
//        System.out.println(arrayList);
//    }
}
