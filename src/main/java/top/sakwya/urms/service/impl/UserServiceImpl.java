package top.sakwya.urms.service.impl;

import jakarta.annotation.Resource;
import org.springframework.web.util.HtmlUtils;
import top.sakwya.urms.result.Result;
import top.sakwya.urms.result.ResultCode;
import top.sakwya.urms.controller.dto.UserDTO;
import top.sakwya.urms.entity.User;
import top.sakwya.urms.exception.ServiceException;
import top.sakwya.urms.mapper.UserMapper;
import top.sakwya.urms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.sakwya.urms.utils.TokenUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    public boolean isExist(String account) {
        User user = userMapper.findByAccount(account);
        return null != user;
    }

    public User findByAccount(String account) {
        return userMapper.findByAccount(account);
    }

    public User get(String username, String password) {
        return userMapper.getByAccountAndPassword(username, password);
    }

    public String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array to signum representation
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    @Override
    public int register(User user) {
        String account = user.getAccount();
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        if (account == null || username == null || password == null || email == null || account.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            return 0;
        }
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        account = HtmlUtils.htmlEscape(account);
        user.setAccount(account);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
//        user.setEnabled(true);
        if (account.isEmpty() || username.isEmpty() || email.isEmpty()) {
            return 0;
        }
        if (isExist(account)) {
            return 2;
        }
        String encodedPassword = hash(password);
        user.setPassword(encodedPassword);
        userMapper.insert(user);
        return 1;
    }

    @Override
    public UserDTO login(User user) {
        String account = user.getAccount();
        String password = user.getPassword();
        account = HtmlUtils.htmlEscape(account);
        password = HtmlUtils.htmlEscape(password);
        String encodedPassword = hash(password);
        if (userMapper.getByAccountAndPassword(account, encodedPassword) != null) {
            user = userMapper.findByAccount(account);
            String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
            UserDTO userDTO;
            userDTO = new UserDTO();
            userDTO.setAccount(user.getAccount());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            userDTO.setToken(token);
            return userDTO;
        } else {
            throw new ServiceException(ResultCode.CODE_600, "用户名或密码错误");
        }

    }
}
