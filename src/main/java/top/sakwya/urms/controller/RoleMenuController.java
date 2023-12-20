package top.sakwya.urms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.sakwya.urms.result.Result;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import top.sakwya.urms.service.IRoleMenuService;
import top.sakwya.urms.entity.RoleMenu;

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
@RequestMapping("//role-menu")
public class RoleMenuController {

    @Resource
    private IRoleMenuService roleMenuService;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody RoleMenu roleMenu) {
        return Result.success(roleMenuService.saveOrUpdate(roleMenu));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleMenuService.removeById(id));
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return roleMenuService.removeByIds(ids);
    }

    @GetMapping
    public Result findAll() {
        return Result.success(roleMenuService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleMenuService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(roleMenuService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

}