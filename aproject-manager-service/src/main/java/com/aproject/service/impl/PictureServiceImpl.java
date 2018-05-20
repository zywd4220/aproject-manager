package com.aproject.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aproject.common.pojo.PictureResult;
import com.aproject.common.utils.FastDFSClient;
import com.aproject.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;
	
	@Override
	public PictureResult uploadPic (MultipartFile picFile) {
		PictureResult result = new PictureResult();
		// 判断图片是否为空
		if(picFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}
		//上传到图片服务器
		try {
			//取图片拓展名
			String originalFilename = picFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			String url = client.uploadFile(picFile.getBytes(),extName);
			//拼接图片服务器的ip地址
			url = IMAGE_SERVER_BASE_URL + url;
			//把url响应给客户端
			result.setError(0);
			result.setUrl(url); 
		} catch (Exception e) {
			result.setError(1);
			result.setMessage("图片上传失败");
			e.printStackTrace();
		}
		return result;
	}

}
