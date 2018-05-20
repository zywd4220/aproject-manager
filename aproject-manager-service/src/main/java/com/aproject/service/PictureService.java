package com.aproject.service;

import org.springframework.web.multipart.MultipartFile;

import com.aproject.common.pojo.PictureResult;

public interface PictureService {

	PictureResult uploadPic(MultipartFile picFile);
}
