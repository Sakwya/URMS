package top.sakwya.urms.service.impl;

import jakarta.annotation.Resource;
import top.sakwya.urms.entity.UserRole;
import top.sakwya.urms.mapper.UserRoleMapper;
import top.sakwya.urms.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserRole[] getByUid(Integer uid) {
        return userRoleMapper.getByUid(uid);
    }

    @Override
    public Integer deleteEntry(Integer uid, Integer rid) {
        return userRoleMapper.deleteEntry(uid, rid);
    }

    @Override
    public Integer insertEntry(UserRole userRole) {
        return userRoleMapper.insert(userRole);
    }

}
