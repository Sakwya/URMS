package top.sakwya.urms.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.sakwya.urms.controller.dto.UserDTO;
import top.sakwya.urms.entity.User;
import top.sakwya.urms.result.Result;
import top.sakwya.urms.result.ResultCode;
import top.sakwya.urms.service.IUserService;

import java.util.Map;

@RestController
public class LoginController {
    @Resource
    IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        UserDTO dto = userService.login(user);
        return Result.success(dto);
    }

    @PostMapping(value = "/register",consumes = "application/json")
    public Result register(@RequestBody(required = false) User user) {
        return switch (userService.register(user)) {
            case 0 -> Result.error(ResultCode.CODE_400, "请正确填写所有字段。");
            case 1 -> Result.success("注册成功");
            case 2 -> Result.error(ResultCode.CODE_600, "账户已存在。");
            default -> Result.error(ResultCode.CODE_600, "未知错误。");
        };
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public Result register(@RequestParam Map<String, String> formData) {
        if (formData == null || formData.isEmpty()) {
            return Result.error(ResultCode.CODE_400, "请正确填写所有字段。");
        }
        System.out.println(formData.toString());
        User user = new User();
        user.setAccount(formData.get("account"));
        user.setUsername(formData.get("username"));
        user.setPassword(formData.get("password"));
        user.setEmail(formData.get("email"));
        return switch (userService.register(user)) {
            case 0 -> Result.error(ResultCode.CODE_400, "请正确填写所有字段。");
            case 1 -> Result.success("注册成功");
            case 2 -> Result.error(ResultCode.CODE_600, "账户已存在。");
            default -> Result.error(ResultCode.CODE_600, "未知错误。");
        };
    }
}


