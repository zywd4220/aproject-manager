package com.aproject.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.aproject.common.pojo.EasyUIDataGridResult;
import com.aproject.common.pojo.TaotaoResult;
import com.aproject.mapper.TbItemParamMapper;

import com.aproject.pojo.TbItemParam;
import com.aproject.pojo.TbItemParamExample;
import com.aproject.pojo.TbItemParamExample.Criteria;
import com.aproject.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		//根据cid查询规格参数模板
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		//执行查询
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if(list != null && list.size()>0) {
			TbItemParam itemParam = list.get(0);
			return TaotaoResult.ok(itemParam);
		}
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult insertItemParam(Long cid, String paramData) {
		//创建一个pojo
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParamMapper.insert(itemParam);

		return TaotaoResult.ok();
	}
	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) {
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//取分页信息
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		//返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		return result;

	}


}
