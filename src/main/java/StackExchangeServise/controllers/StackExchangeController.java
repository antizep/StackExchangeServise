package StackExchangeServise.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import StackExchangeServise.ResultModel;
import StackExchangeServise.services.SearshProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/stackExchange")
public class StackExchangeController {
	
	
	@Autowired
	ApplicationContext context;
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		String message = "Message";
		model.addAttribute("message",message);
		return "index";
	}
	
	@RequestMapping(value = "resultTable", method = RequestMethod.GET  )
	public String searsher(Model model,
			@RequestParam("searshString")String searshString) {
		
		
		SearshProvider provider = context.getBean("stackExchange",SearshProvider.class);
		List<ResultModel> resultsList = provider.searsh(searshString);
		
		if(provider.getError() == null) {
			
			model.addAttribute("results",resultsList);
		
		}else {
			
			model.addAttribute("error",provider.getError());
			
		}
		
		return "resultTable";
	
	}
}
