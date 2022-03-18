package com.tl.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tl.common.entity.Information;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tl
 * @since 2021-12-09
 */
public interface InformationService extends IService<Information> {

    public void insertInformation(Information information);

    public List<Information> getAllAnnounce();


}
