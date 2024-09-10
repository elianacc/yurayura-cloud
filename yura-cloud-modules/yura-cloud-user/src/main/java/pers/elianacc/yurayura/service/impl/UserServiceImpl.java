package pers.elianacc.yurayura.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dao.UserMapper;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.UserSelectDTO;
import pers.elianacc.yurayura.dto.UserUpdateStatusDTO;
import pers.elianacc.yurayura.entity.User;
import pers.elianacc.yurayura.service.IUserService;

import java.util.List;

/**
 * 用户 service impl
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${yurayura.default-upload.user-avatar}")
    private String defaultUplUserAvatar;

    @Override
    public PageInfo<User> getPage(UserSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<User> userList = userMapper
                .selectList(Wrappers.<User>lambdaQuery()
                        .select(User.class, i -> !i.getColumn().equals("user_password"))
                        .nested(!ObjectUtils.isEmpty(dto.getUserNameKeyword()), i -> i
                                .apply("instr(user_name, {0}) > 0", dto.getUserNameKeyword())
                                .or()
                                .apply("instr(user_nickname, {0}) > 0", dto.getUserNameKeyword())
                        )
                        .eq(!ObjectUtils.isEmpty(dto.getUserSex()), User::getUserSex, dto.getUserSex())
                        .eq(!ObjectUtils.isEmpty(dto.getUserStatus()), User::getUserStatus, dto.getUserStatus())
                        .eq(!ObjectUtils.isEmpty(dto.getUserPhoneNumber())
                                , User::getUserPhoneNumber, dto.getUserPhoneNumber())
                        .orderByDesc(User::getId));
        PageInfo<User> pageInfo = new PageInfo<>(userList, 5);
        Assert.isTrue(pageInfo.getTotal() != 0, "查询不到数据");
        return pageInfo;
    }

    @Override
    public void updateStatus(UserUpdateStatusDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUserStatus(dto.getUserStatus());
        userMapper.updateById(user);
    }

    @Override
    public void updateAvatarDefault(IdDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUserAvatarUrl(defaultUplUserAvatar);
        userMapper.updateById(user);
    }

}
