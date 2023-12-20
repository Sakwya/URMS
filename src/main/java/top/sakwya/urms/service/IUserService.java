package top.sakwya.urms.service;

import top.sakwya.urms.controller.dto.UserDTO;
import top.sakwya.urms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
public interface IUserService extends IService<User> {
    int register(User user);
    UserDTO login(User user);
}
