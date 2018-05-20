package com.aproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aproject.common.pojo.EasyUIDataGridResult;
import com.aproject.common.pojo.TaotaoResult;
import com.aproject.pojo.TbItem;
import com.aproject.pojo.TbSeller;
import com.aproject.pojo.TbUser;
import com.aproject.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	private TbItem getItemById(@PathVariable Long itemId) {
		TbItem item =itemService.getItemById(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows,HttpServletRequest request) {
		TbSeller seller = (TbSeller) request.getAttribute("seller");
		String sellername = seller.getSellername();
		EasyUIDataGridResult result = itemService.getItemList(page, rows,sellername);
		return result;
	}
	
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String desc,String itemParams,HttpServletRequest request) {
		TbSeller seller = (TbSeller) request.getAttribute("seller");
		item.setSellerName(seller.getSellername());
		TaotaoResult result = itemService.createItem(item, desc,itemParams);
		return result;
	}

	@RequestMapping(value="/item/update", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc,String itemParams,HttpServletRequest request) {
//		Long id = item.getId();
//		System.out.println(id);
	//	String itemId = request.getParameter("id");
	//	System.out.println(itemId);
		TbSeller seller = (TbSeller) request.getAttribute("seller");
		item.setSellerName(seller.getSellername());
		TaotaoResult result = itemService.UpdateItem(item, desc, itemParams);
		return result;
	}
	
	@RequestMapping(value="/item/delete", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItem(HttpServletRequest request) {
		String itemIdss = request.getParameter("ids");
		String[] itemIds = itemIdss.split(",");
		for(String itemId : itemIds) {
			long aItemId = Long.parseLong(itemId);
			itemService.deleteItem(aItemId);
		}
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String html = itemService.getItemParamHtml(itemId);
		model.addAttribute("html", html);
		return  "itemparam";
	}

	
}
