package top.sakwya.urms.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import top.sakwya.urms.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    @Select("select * from user_role where uid = #{uid}")
    UserRole[] getByUid(Integer uid);

    @Delete("delete from user_role where uid = #{uid} and rid = #{rid}")
    Integer deleteEntry(Integer uid, Integer rid);
}
