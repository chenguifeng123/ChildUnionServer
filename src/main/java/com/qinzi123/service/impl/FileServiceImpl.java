package com.qinzi123.service.impl;

import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.FileService;
import com.qinzi123.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/11/27.
 */
@Service
public class FileServiceImpl implements FileService {

	@Value("${web.upload-path}")
	public String currentPath;

	private String getPath(){
		//return FileService.class.getResource("/").getPath();
		return currentPath;
	}

	public Map<String, String> uploadFile(MultipartFile file, String columnName) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("field", columnName);
		if(!file.isEmpty()){
			try {
				String localPath = getPath();//String.format("%sstatic", getPath());
				String fileName = String.format("/file/%d_%s", System.currentTimeMillis(), file.getOriginalFilename());
				Utils.writeByteArrayToFile(localPath + fileName, file.getBytes());
				result.put("url", fileName);
			}catch(IOException e){
				throw new GlobalProcessException("上传文件失败", e);
			}
		}else
			throw new GlobalProcessException("文件内容为空");
		return result;
	}
}
