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
 * 前端控制器
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
        return Result.success(userRoleService.insertEntry(userRole));
    }

    @PostMapping("/del")
    public Result delete(@RequestBody UserRole userRole) {
        return Result.success(userRoleService.deleteEntry(userRole.getUid(), userRole.getRid()));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userRoleService.list());
    }

    @GetMapping("/{uid}")
    public Result findOne(@PathVariable Integer uid) {
        return Result.success(userRoleService.getByUid(uid));
    }

}
