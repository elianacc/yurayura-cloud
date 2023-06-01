package pers.elianacc.yurayura.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dao.SysDictMapper;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.service.ISysDictService;
import pers.elianacc.yurayura.util.RedisUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统数据字典 service impl
 *
 * @author ELiaNaCc
 * @since 2020-03-24
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public PageInfo<SysDict> getPage(SysDictSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysDict> sysDictList = sysDictMapper
                .selectList(Wrappers.<SysDict>lambdaQuery()
                .apply(!ObjectUtils.isEmpty(dto.getDictCode()), "instr(dict_code, {0}) > 0", dto.getDictCode())
                .eq(!ObjectUtils.isEmpty(dto.getDictStatus()), SysDict::getDictStatus, dto.getDictStatus())
                .orderByAsc(SysDict::getDictCode, SysDict::getDictSeq));
        return new PageInfo<>(sysDictList, 5);
    }

    @Override
    public String insert(SysDictInsertDto dto) {
        String warn = "";
        List<SysDict> sysDictList = sysDictMapper
                .selectList(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getDictCode, dto.getDictCode())
                .eq(SysDict::getDictVal, dto.getDictVal()));
        if (sysDictList.isEmpty()) {
            SysDict sysDict = new SysDict();
            BeanUtils.copyProperties(dto, sysDict);
            sysDictMapper.insert(sysDict);
            if (sysDict.getDictStatus().intValue() == EnableStatusEnum.ENABLE.getStatusId()) {
                // 插入字典记录到redis
                RedisUtil.lSet(sysDict.getDictCode(), sysDict);
            }
        } else {
            warn = "此字典编码对应字典值已存在";
        }
        return warn;
    }

    @Override
    public void deleteBatchByIds(IdsDto dto) {
        dto.getIds().forEach(id -> {
            SysDict sysDict = sysDictMapper.selectById(id);
            // 删除redis中的字典记录
            RedisUtil.lRemove(sysDict.getDictCode(), 0, sysDict);
        });
        sysDictMapper.deleteBatchIds(dto.getIds());
    }

    @Override
    public String update(SysDictUpdateDto dto) {
        String warn = "";
        // 原字典记录
        SysDict oldSysDict = sysDictMapper.selectById(dto.getId());
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dto, sysDict);
        sysDictMapper.updateById(sysDict);
        // 先从redis删除原字典记录
        RedisUtil.lRemove(oldSysDict.getDictCode(), 0, oldSysDict);
        if (sysDict.getDictStatus().intValue() == EnableStatusEnum.ENABLE.getStatusId()) {
            sysDict.setDictCode(oldSysDict.getDictCode());
            sysDict.setDictVal(oldSysDict.getDictVal());
            // 再插入新的字典记录到redis
            RedisUtil.lSet(oldSysDict.getDictCode(), sysDict);
        }
        return warn;
    }

    @Override
    public List<SysDict> getByDictCode(String dictCode) {
        // 判断是否存在这个字典编码key对应的字典记录在redis，存在则直接获取，不存在从数据库查询
        if (RedisUtil.hasKey(dictCode) && !RedisUtil.lGet(dictCode, 0, -1).isEmpty()) {
            List<Object> objList = RedisUtil.lGet(dictCode, 0, -1);
            List<SysDict> sysDictList = JSON.parseArray(JSON.toJSONString(objList), SysDict.class);
            return sysDictList.stream().sorted(Comparator.comparing(SysDict::getDictSeq)).collect(Collectors.toList());
        }
        return sysDictMapper
                .selectList(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getDictCode, dictCode)
                .eq(SysDict::getDictStatus, EnableStatusEnum.ENABLE.getStatusId())
                .orderByAsc(SysDict::getDictSeq));
    }

    @Override
    public List<SysDict> getAll() {
        Set<String> keys = RedisUtil.getListKey();
        List<SysDict> sysDictList = new ArrayList<>();
        if (!keys.isEmpty()) {
            keys.forEach(key -> {
                if (!key.contains("yurayura-cloud-client-business-session") && !key.contains("lock4j")) {
                    List<Object> objList = RedisUtil.lGet(key, 0, -1);
                    List<SysDict> sysDictListForKey = JSON.parseArray(JSON.toJSONString(objList), SysDict.class);
                    sysDictList.addAll(sysDictListForKey);
                }
            });
        }
        return sysDictList;
    }
}
