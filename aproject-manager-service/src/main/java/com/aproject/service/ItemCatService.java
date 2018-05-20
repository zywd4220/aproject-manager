package com.aproject.service;

import java.util.List;

import com.aproject.common.pojo.EasyUITreeNode;


public interface ItemCatService {

	List<EasyUITreeNode> getItemCatList(long parentId);
}
