package top.sakwya.urms.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
@Getter
@Setter
@TableName("role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}
