package top.sakwya.urms.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import top.sakwya.urms.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Delete("delete from role_menu where rid = #{rid} and mid = #{mid}")
    Integer deleteEntry(Integer rid, Integer mid);
    @Select("select * from role_menu where rid = #{rid}")
    RoleMenu[] getByRid(Integer rid);
}
