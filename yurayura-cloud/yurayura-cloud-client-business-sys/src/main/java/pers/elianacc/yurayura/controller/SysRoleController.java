package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysRoleBlockHandler;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.service.SysRoleService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;
import java.util.Map;

/**
 * 系统角色 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/api/sys/role")
@Api(tags = "系统角色API")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<Map<String,Object>>>
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "sys-role-getPage",
            blockHandlerClass = SysRoleBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询系统角色")
    public ApiResult<PageInfo<Map<String, Object>>> getPage(@RequestBody SysRoleSelectDto dto) {
        return sysRoleService.getPage(dto);
    }

    /**
     * 添加系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.roleName"}, autoRelease = false)
    @ApiOperation("添加系统角色")
    public ApiResult<String> insert(@RequestBody SysRoleInsertDto dto) {
        return sysRoleService.insert(dto);
    }

    /**
     * 修改系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改系统角色")
    public ApiResult<String> update(@RequestBody SysRoleUpdateDto dto) {
        return sysRoleService.update(dto);
    }

    /**
     * 查询所有系统角色
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.role.SysRole>>
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统角色")
    public ApiResult<List<SysRole>> getAll() {
        return sysRoleService.getAll();
    }
}
