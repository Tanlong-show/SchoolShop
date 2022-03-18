package com.tl.school.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tl.common.entity.Information;
import com.tl.school.mapper.InformationMapper;
import com.tl.school.service.InformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tl
 * @since 2021-12-09
 */
@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

    @Autowired
    InformationMapper informationMapper;

    @Override
    public void insertInformation(Information information) {
        informationMapper.insert(information);
    }

    @Override
    public List<Information> getAllAnnounce() {
        return informationMapper.selectList(Wrappers.<Information>lambdaQuery()
                .isNotNull(Information::getId));
    }
}
