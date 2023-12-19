package top.sakwya.urms.service.impl;

import top.sakwya.urms.entity.User;
import top.sakwya.urms.mapper.UserMapper;
import top.sakwya.urms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
