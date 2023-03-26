package com.sb.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.sb.bean.User;
import com.sb.config.common.Common;
import com.sb.config.common.GlobalResult;
import com.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

/**
 * author: dyq
 * Time: 2023/3/18
 * description: 描述
 */
@RestController
public class AuthController {

    @Autowired
    private Producer producer;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/captcha")
    public GlobalResult captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();
        BufferedImage image = producer.createImage(code);
        //测试
//        key = "aaaaa";
//        code = "11111";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str+encoder.encode(outputStream.toByteArray());

        try {
            redisTemplate.opsForHash().put(Common.CAPTCHA_KEY,key,code);
            redisTemplate.expire(Common.CAPTCHA_KEY,120, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return GlobalResult.success(
                MapUtil.builder()
                    .put("token",key)
                    .put("captchaImg",base64Img)
                    .build());
    }

    /**
     * 获取用户信息接口
     * @param principal
     * @return
     */
    @GetMapping("/userInfo")
    public GlobalResult getUserInfo(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        return GlobalResult.success(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("city",user.getCity())
                .put("email",user.getEmail())
                .put("lastLogin",user.getLastLogin())
                .map()
        );
    }
}
