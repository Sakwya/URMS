package top.sakwya.urms.service.impl;

import jakarta.annotation.Resource;
import top.sakwya.urms.entity.RoleMenu;
import top.sakwya.urms.mapper.RoleMenuMapper;
import top.sakwya.urms.service.IRoleMenuService;
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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Integer insertEntry(RoleMenu roleMenu) {
        return roleMenuMapper.insert(roleMenu);
    }

    @Override
    public Integer deleteEntry(Integer rid, Integer mid) {
        return roleMenuMapper.deleteEntry(rid, mid);
    }

    @Override
    public RoleMenu[] getByRid(Integer rid) {
        return roleMenuMapper.getByRid(rid);
    }
}
