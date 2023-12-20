package top.sakwya.urms.mapper;

import org.apache.ibatis.annotations.Select;
import top.sakwya.urms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("Select * from user where account = #{account}")
    User findByAccount(String account);

    @Select("Select * from user where account = #{account} and password = #{password}")
    User getByAccountAndPassword(String account, String password);
}
