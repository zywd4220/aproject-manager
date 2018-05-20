package com.aproject.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aproject.common.pojo.TaotaoResult;
import com.aproject.mapper.TbContentMapper;
import com.aproject.pojo.TbContent;
import com.aproject.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//插入数据
		contentMapper.insert(content);
		
		return TaotaoResult.ok();
	}

}
