package com.aproject.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aproject.pojo.TbSeller;


public interface SellerService {

	TbSeller getSellerByToken(HttpServletRequest request, HttpServletResponse response);
}
