package com.qinzi123.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/11/27.
 */
public interface FileService {
	Map<String, String> uploadFile(MultipartFile file, String columnName);
}
