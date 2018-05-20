package com.aproject.service;

import com.aproject.common.pojo.EasyUIDataGridResult;
import com.aproject.common.pojo.TaotaoResult;
//import com.aproject.common.pojo.TaotaoResult;
import com.aproject.pojo.TbItem;
import com.aproject.pojo.TbItemDesc;

public interface ItemService {

	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page, int rows,String sellername);
	TaotaoResult createItem(TbItem item,String desc,String itemParam);
	TaotaoResult UpdateItem(TbItem item,String desc,String itemParam);
	String getItemParamHtml(Long itemId);
	TaotaoResult deleteItem(Long itemId);
	//TaotaoResult addItem(TbItem item, TbItemDesc itemDesc, String itemParams);
}
