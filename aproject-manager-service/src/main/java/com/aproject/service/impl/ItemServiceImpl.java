package com.aproject.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.aproject.common.pojo.EasyUIDataGridResult;
import com.aproject.common.pojo.HttpClientUtil;
import com.aproject.common.pojo.TaotaoResult;
import com.aproject.common.utils.IDUtils;
import com.aproject.common.utils.JsonUtils;
import com.aproject.mapper.TbItemDescMapper;
import com.aproject.mapper.TbItemMapper;
import com.aproject.mapper.TbItemParamItemMapper;
import com.aproject.pojo.TbItem;
import com.aproject.pojo.TbItemDesc;
import com.aproject.pojo.TbItemExample;
import com.aproject.pojo.TbItemParamExample;
import com.aproject.pojo.TbItemExample.Criteria;
import com.aproject.pojo.TbItemParamItem;
import com.aproject.pojo.TbItemParamItemExample;
import com.aproject.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	TbItemMapper itemMapper;
	
	@Autowired
	TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) { 
		
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows,String sellername) {
		//分页处理
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andSellerNameEqualTo(sellername);
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc,String itemParam) {
		// 生成商品id
		long itemId = IDUtils.genItemId();
		// 补全TbItem属性
		item.setId(itemId);
		// '商品状态，1-正常，2-下架，3-删除'
		item.setStatus((byte) 1);
		// 创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入商品表
		itemMapper.insert(item);
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述数据
		itemDescMapper.insert(itemDesc);

		//添加商品描述规格处理
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		//插入数据
		itemParamItemMapper.insert(itemParamItem);
		HttpClientUtil.doPost("http://localhost:8083/search/importall");

		return TaotaoResult.ok();

		
	}

	@Override
	public TaotaoResult UpdateItem(TbItem item, String desc, String itemParam) {
		long itemId = item.getId();
		Date date = new Date();
		item.setStatus((byte) 1);
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.updateByPrimaryKey(item);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 修改商品描述数据
		itemDescMapper.updateByPrimaryKey(itemDesc);

		//修改商品描述规格处理
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.aproject.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem>list = itemParamItemMapper.selectByExampleWithBLOBs(example);

		//取规格参数
		TbItemParamItem itemParamItem = list.get(0);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.updateByExample(itemParamItem, example);
		
		return TaotaoResult.ok();
	}
	
	@Override
	public String getItemParamHtml(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.aproject.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem>list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.isEmpty()) {
			return"";
		}
		//取规格参数
		TbItemParamItem itemParamItem = list.get(0);
		//取json数据
		String paramData = itemParamItem.getParamData();
		//转换成java对象	
		List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
		//遍历list生成html
		StringBuffer sb = new StringBuffer();
		
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
			sb.append("		</tr>\n");
			//取规格项
			List<Map>mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
				sb.append("			<td>"+map2.get("v")+"</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");
		
		return sb.toString();

	}

	@Override
	public TaotaoResult deleteItem(Long itemId) {
		itemMapper.deleteByPrimaryKey(itemId);	
		itemDescMapper.deleteByPrimaryKey(itemId);
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.aproject.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		itemParamItemMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}



	

}
