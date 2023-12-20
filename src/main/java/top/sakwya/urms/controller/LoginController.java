package top.sakwya.urms.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sakwya.urms.controller.dto.UserDTO;
import top.sakwya.urms.entity.User;
import top.sakwya.urms.result.Result;
import top.sakwya.urms.result.ResultCode;
import top.sakwya.urms.service.IUserService;

@RestController
public class LoginController {
    @Resource
    IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        UserDTO dto = userService.login(user);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return switch (userService.register(user)) {
            case 0 -> Result.error(ResultCode.CODE_400, "请正确填写所有字段。");
            case 1 -> Result.success("注册成功");
            case 2 -> Result.error(ResultCode.CODE_600, "账户已存在。");
            default -> Result.error(ResultCode.CODE_600, "未知错误。");
        };
    }
}


