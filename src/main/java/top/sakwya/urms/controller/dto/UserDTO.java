package top.sakwya.urms.controller.dto;

import lombok.Data;
import top.sakwya.urms.entity.Menu;
import top.sakwya.urms.entity.Role;

import java.util.List;

/**
 * 接收前端登录请求的参数
 */
@Data
public class UserDTO {
    private String account;
    private String username;
    private String password;
    private String email;
    private String token;
    private List<Role> role;
    private List<Menu> menus;
}
