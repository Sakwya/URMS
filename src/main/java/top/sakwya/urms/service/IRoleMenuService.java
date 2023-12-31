package top.sakwya.urms.service;

import top.sakwya.urms.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    Integer insertEntry(RoleMenu roleMenu);

    Integer deleteEntry(Integer rid, Integer mid);

    RoleMenu[] getByRid(Integer rid);
}
