package top.sakwya.urms.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang.StringUtils;
import top.sakwya.urms.entity.User;
import top.sakwya.urms.service.IUserService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Component
public class TokenUtils {
    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    /**
     * 生成Token
     *
     * @return Token秘钥
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 24)) // 24小时token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

//    /*
//     * 获取当前登录的用户信息
//     * @return user对象
//     */
//    public static User getCurrentUser() {
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            String token = request.getHeader("Authorization");
//            System.out.println(token);
//            if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
//                token = token.substring(7); // 去掉 "Bearer " 前缀
//                System.out.println(token);
//                String userId = JWT.decode(token).getAudience().get(0);
//                return staticUserService.getById(Integer.valueOf(userId));
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//        System.out.println("没有获取到用户信息");
//        return null;
//    }
}
