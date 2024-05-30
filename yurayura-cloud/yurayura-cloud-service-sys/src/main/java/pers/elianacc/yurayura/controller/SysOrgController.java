package pers.elianacc.yurayura.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysOrgInsertDto;
import pers.elianacc.yurayura.dto.SysOrgSelectDto;
import pers.elianacc.yurayura.dto.SysOrgUpdateDto;
import pers.elianacc.yurayura.entity.sys.org.SysOrg;
import pers.elianacc.yurayura.service.ISysOrgService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统组织 controller
 *
 * @author ELiaNaCc
 * @since 2024-05-14
 */
@RestController
@RequestMapping("/sys/org")
public class SysOrgController {

    @Autowired
    private ISysOrgService iSysOrgService;

    /**
     * 查询系统组织（根据系统组织id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.org.SysOrg>
     */
    @GetMapping("/getById")
    public ApiResult<SysOrg> getById(IdDto dto) {
        return ApiResult.success("查询成功", iSysOrgService.getById(dto.getId()));
    }

    /**
     * 查询所有系统组织
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.org.SysOrg>>
     */
    @GetMapping("/getAll")
    public ApiResult<List<SysOrg>> getAll() {
        return ApiResult.success("查询成功", iSysOrgService.list());
    }


    /**
     * 分页查询系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.sys.org.SysOrg>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<SysOrg>> getPage(@RequestBody SysOrgSelectDto dto) {
        return ApiResult.success("分页查询成功", iSysOrgService.getPage(dto));
    }

    /**
     * 添加系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysOrgInsertDto dto) {
        SysOrg sysOrg = new SysOrg();
        BeanUtils.copyProperties(dto, sysOrg);
        sysOrg.setOrgCreateTime(LocalDateTime.now());
        iSysOrgService.save(sysOrg);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(@RequestBody SysOrgUpdateDto dto) {
        SysOrg sysOrg = new SysOrg();
        BeanUtils.copyProperties(dto, sysOrg);
        sysOrg.setOrgUpdateTime(LocalDateTime.now());
        iSysOrgService.updateById(sysOrg);
        return ApiResult.success("修改成功");
    }

}

