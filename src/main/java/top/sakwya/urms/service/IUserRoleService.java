package top.sakwya.urms.service;

import top.sakwya.urms.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
public interface IUserRoleService extends IService<UserRole> {
    UserRole[] getByUid(Integer uid);
    Integer deleteEntry(Integer uid, Integer rid);

    Integer insertEntry(UserRole userRole);
}
