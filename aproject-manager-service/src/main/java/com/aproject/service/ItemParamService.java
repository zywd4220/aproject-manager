package com.aproject.service;

import com.aproject.common.pojo.EasyUIDataGridResult;
import com.aproject.common.pojo.TaotaoResult;

public interface ItemParamService {

	TaotaoResult getItemParamByCid(Long cid); 
	EasyUIDataGridResult getItemParamList(int page, int rows);
	TaotaoResult insertItemParam(Long cid , String paramData);
	
}
