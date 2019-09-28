package com.qinzi123.controller.web;

import com.qinzi123.util.Utils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/11.
 */
@RestController
@Api(value = "上传文件", description = "上传文件")
public class FileController {

	@Value("${web.upload-path}")
	public String currentPath;

	private String getPath(){
		//return FileService.class.getResource("/").getPath();
		return currentPath;
	}

	//
	@RequestMapping(value = "/uploadPicture/{columnName}", method = RequestMethod.POST)
	public Map<String, String> uploadPicture(@RequestParam("file") MultipartFile file, @PathVariable("columnName") String columnName){
		Map<String, String> result = new HashMap<String, String>();
		result.put("field", columnName);
		if(!file.isEmpty()){
			try {
				String localPath = getPath();//String.format("%sstatic", getPath());
				String fileName = String.format("/img/%d_%s", System.currentTimeMillis(), file.getOriginalFilename());
				Utils.writeByteArrayToFile(localPath + fileName, file.getBytes());
				result.put("url", fileName);
			}catch(IOException e){
				result.put("url", "");
				e.printStackTrace();
			}
		}
		return result;
	}
}
