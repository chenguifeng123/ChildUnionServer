package com.qinzi123.controller.web;

import com.qinzi123.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/11.
 */
@RestController
@Api(value = "上传文件", description = "上传文件")
public class FileController {

	@Autowired
	FileService fileService;

	@RequestMapping(value = "/manage/uploadFile/{columnName}", method = RequestMethod.POST)
	public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("columnName") String columnName){
		return fileService.uploadFile(file, columnName);
	}
}
