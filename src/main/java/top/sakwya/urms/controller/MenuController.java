package top.sakwya.urms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.ArrayUtils;
import top.sakwya.urms.entity.RoleMenu;
import top.sakwya.urms.entity.UserRole;
import top.sakwya.urms.result.Result;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import top.sakwya.urms.service.IMenuService;
import top.sakwya.urms.entity.Menu;

import org.springframework.web.bind.annotation.RestController;
import top.sakwya.urms.service.IRoleMenuService;
import top.sakwya.urms.service.IRoleService;
import top.sakwya.urms.service.IUserRoleService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sakwya
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleMenuService roleMenuService;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return menuService.removeByIds(ids);
    }

    @GetMapping
    public Result findAll() {
        return Result.success(menuService.list());
    }

    @GetMapping("/{uid}")
    public Result findOne(@PathVariable Integer uid) {
        UserRole[] userRoles = userRoleService.getByUid(uid);
        Integer[] mids = new Integer[0];
        for (UserRole ur : userRoles) {
            RoleMenu[] rm = roleMenuService.getByRid(ur.getRid());
            for (RoleMenu r : rm) {
                Integer mid = r.getMid();
                if (ArrayUtils.contains(mids, mid)) {
                    continue;
                } else {
                    mids = (Integer[]) ArrayUtils.add(mids, mid);
                }
            }
        }
        Menu[] menus = new Menu[0];
        for (Integer rid : mids) {
            Menu menu = menuService.getById(rid);
            menus = (Menu[]) ArrayUtils.add(menus, menu);
        }
        return Result.success(menus);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}
