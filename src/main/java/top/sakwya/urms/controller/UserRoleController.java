package top.sakwya.urms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.sakwya.urms.result.Result;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import top.sakwya.urms.service.IUserRoleService;
import top.sakwya.urms.entity.UserRole;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    @Resource
    private IUserRoleService userRoleService;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody UserRole userRole) {
        return Result.success(userRoleService.saveOrUpdate(userRole));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userRoleService.removeById(id));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userRoleService.removeByIds(ids);
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userRoleService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userRoleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userRoleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

}
