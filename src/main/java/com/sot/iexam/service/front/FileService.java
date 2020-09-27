package com.sot.iexam.service.front;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
/**
 * @author Kimbobo
 */
public interface FileService {

    boolean uploadMultipartFile(Map<String, Object> data, MultipartFile file);
}
    