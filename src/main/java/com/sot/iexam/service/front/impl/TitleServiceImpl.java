package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.titleMapper;
import com.sot.iexam.DO.title;
import com.sot.iexam.service.front.TitleService;
import com.sot.iexam.util.Timmer;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Kimbobo
 */
@Service
public class TitleServiceImpl implements TitleService {

    @Autowired
    titleMapper titleMapper;


    @Override
    public Map getTitleList(Integer page, Integer size, title title) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        List<title> list= titleMapper.getTitleList(title);

        for (title item:list){
            item.setPhotoPath(UploadUtils.parseFileUrl(item.getPhotoPath()));
        }

        m.put("data", list);
        m.put("count", titleMapper.getCount(title));
        return m;
    }

    @Override
    public title getTitleByTitleId(Integer titleId) {
        return titleMapper.getTitleByTitleId(titleId);
    }

    @Override
    public boolean addTitle(title title) {
        title.setCreateTime(Timmer.now());
        title.setModifyTime(Timmer.now());
        titleMapper.insert(title);
        return false;
    }

    @Override
    public void updateTitle(title title) {
        title.setModifyTime(Timmer.now());
        titleMapper.update(title);
    }

    @Override
    public void deleteTitle(Integer titleId) {
        title tit=titleMapper.getTitleByTitleId(titleId);
        tit.setStatus(0);
        titleMapper.update(tit);
    }
}
    