package com.sot.iexam.service.front.impl;


import com.sot.iexam.service.front.FileService;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public boolean uploadMultipartFile(Map<String, Object> data, MultipartFile file) {
        String picWeb = "";
        // 图片保存名
        if (null != file) {
            Map<String, Object> videoFileInfo = UploadUtils.saveMultipartFile(file);
            if ((Integer) videoFileInfo.get("status") > 0) {
                //上传完成
                picWeb = UploadUtils.parseFileUrl(videoFileInfo.get("saveName").toString());
            } else { //上传失败
                data.put("errorMessage", videoFileInfo.get("errorMsg").toString());
            }
            data.put("imgUrl", videoFileInfo.get("saveName").toString());
            data.put("src", picWeb);
        }
        return true;

    }
}
    