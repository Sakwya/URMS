package top.sakwya.urms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.util.HtmlUtils;
import top.sakwya.urms.result.Result;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import top.sakwya.urms.result.ResultCode;
import top.sakwya.urms.service.IUserService;
import top.sakwya.urms.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    //新增或者更新
    @PostMapping(consumes = "application/json")
    public Result save(@RequestBody User user) {
        String username = user.getUsername();
        String password = hash(user.getPassword());
        String account = user.getAccount();
        String email = user.getAccount();
        username = HtmlUtils.htmlEscape(username);
        account = HtmlUtils.htmlEscape(account);
        email= HtmlUtils.htmlEscape(email);
        user.setUsername(username);
        user.setAccount(account);
        user.setPassword(password);
        user.setEmail(email);
        return Result.success(userService.saveOrUpdate(user));
    }

    @PostMapping(consumes = "multipart/form-data")
    public Result save(@RequestParam Map<String, String> formData) {
        if (formData == null || formData.isEmpty()) {
            return Result.error(ResultCode.CODE_400, "请正确填写所有字段。");
        }
        String username = formData.get("username");
        String account = formData.get("account");
        String email = formData.get("email");
        User user = new User();
        user.setPassword(hash(formData.get("password")));
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        account = HtmlUtils.htmlEscape(account);
        user.setAccount(account);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
        return Result.success(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
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
}
