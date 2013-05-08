package com.obss.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.obss.model.response.ResponseGedikStockList;
import com.obss.service.GedikStockService;

@Controller
public class StockDataController {
	
	protected static Logger logger = Logger.getLogger("sessionListener");
		
	@Resource(name = "gedikStockService")
	private GedikStockService gedikStockService;

	@RequestMapping(value = "/stocks")
	public ModelAndView listAllStocks() {
		
		logger.debug("Stocks page requested");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("stockData");
		
		ResponseGedikStockList resultList;
		try {
			resultList = gedikStockService.getAllStocks();
		} catch (IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return modelAndView;
		}
		
		modelAndView.addObject("gedikStockList", resultList.getGedikStockList());

		return modelAndView;
	}

}
