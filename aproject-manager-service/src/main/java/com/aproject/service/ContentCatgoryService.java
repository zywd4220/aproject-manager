package com.aproject.service;

import java.util.List;

import com.aproject.common.pojo.EasyUITreeNode;
import com.aproject.common.pojo.TaotaoResult;

public interface ContentCatgoryService {

	List<EasyUITreeNode> getContentCatList(Long parentId);
	TaotaoResult insertCatgory(Long parentId,String name);
}
