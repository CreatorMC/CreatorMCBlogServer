package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.creator.constants.EmailConstants;
import com.creator.constants.SystemConstants;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.ImageRandom;
import com.creator.domain.entity.LoginUser;
import com.creator.domain.entity.User;
import com.creator.domain.vo.UserAdminLoginVo;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.service.ImageRandomService;
import com.creator.service.LoginService;
import com.creator.service.UserService;
import com.creator.utils.JwtUtil;
import com.creator.utils.RandomUtils;
import com.creator.utils.RedisCache;
import com.creator.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@SuppressWarnings({"rawtypes", "DuplicatedCode"})
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ImageRandomService imageRandomService;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    String emailServer;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否通过
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }
        //获取userid，生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.LOGIN_ADMIN_KEY + userId, loginUser);
        //把token和userinfo封装返回
        UserAdminLoginVo vo = new UserAdminLoginVo(jwt);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        redisCache.deleteObject(SystemConstants.LOGIN_ADMIN_KEY + SecurityUtils.getUserId());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRandomImg(String userAgent) {
        //获取设备是电脑还是手机
        boolean isMobile = Pattern.matches(SystemConstants.REGEX_DEVICE_IS_MOBILE, userAgent);
        String type = isMobile ? SystemConstants.IMAGE_RANDOM_TYPE_MOBILE : SystemConstants.IMAGE_RANDOM_TYPE_PC;
        //获取所有参与随机显示的图片的链接
        List<ImageRandom> imageRandoms = imageRandomService.listShowImg(type);
        int index = (int) (Math.random() * imageRandoms.size());
        if(index >= imageRandoms.size()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.IMAGE_RANDOM_NULL);
        }
        return ResponseResult.okResult(imageRandoms.get(index).getUrl());
    }

    @Override
    public ResponseResult sendEmailVCode(String email) {
        try {
            //检查邮箱是否存在
            long count = userService.count(new LambdaQueryWrapper<User>()
                    .eq(User::getEmail, email)
            );
            if(count <= 0) {
                //邮箱不存在，说明未被注册过
                return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_IS_NULL);
            }
            //邮箱存在，继续下面的流程
            //生成验证码
            String vCode = RandomUtils.generateVCode();
            //创建邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            //标题
            helper.setSubject(EmailConstants.EMAIL_CODE_TITLE + vCode);
            //内容
            helper.setText(String.format(EmailConstants.EMAIL_CODE_CONTENT, vCode),true);
            //邮件接收者
            helper.setTo(email);
            //邮件发送者，必须和配置文件里的一样，不然授权码匹配不上
            helper.setFrom(emailServer);
            mailSender.send(mimeMessage);
            //存到 Redis 中
            String uuid = UUID.randomUUID().toString();
            redisCache.setCacheObject(uuid, vCode, EmailConstants.EMAIL_CODE_TIME, TimeUnit.SECONDS);
            return ResponseResult.okResult(uuid);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

