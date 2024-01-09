package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.ISubmitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("")
public class UtilController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource
    ISubmitService ISubmitService;

    @Autowired
    private JavaMailSender emailSender;
    @GetMapping("send-email")
    public Result sendEmail(@RequestParam String email) throws MessagingException {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        try {
            stringRedisTemplate.opsForValue().set(email, String.valueOf(code), 10, TimeUnit.MINUTES);   // 10分钟过期
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        MimeMessage message = emailSender.createMimeMessage();  // 创建邮件
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("123123@163.com");
        helper.setTo(email);
        helper.setSubject("验证码已发送，请查收");
        helper.setText(STR."感谢您使用此平台，您的验证码为：<b>\{code}</b><br>请在十分钟内验证，验证码打死也不要告诉别人哦", true);
        try {
            emailSender.send(message);
        } catch (Exception e) {
            return Result.error("发送失败");
        }
        return Result.success();
    }

    @PostMapping("submit")
    @Operation(summary = "代码提交")
    public Result submit(@RequestHeader String token, @RequestParam String problemIdentity, @RequestBody String code){
        return ISubmitService.submit(token, problemIdentity, code);
    }
}
