package com.codechiev.controller.metadata;

import java.util.List;

import org.copycat.framework.Page;
import org.copycat.framework.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codechiev.model.Constant;
import com.codechiev.model.metadata.Attribute;
import com.codechiev.service.dao.AttributeDaoService;

@Controller
@RequestMapping(value = "/metadata")
public class AttributeController {
	
	@Autowired
	private AttributeDaoService attributeDaoService;

	@RequestMapping(value = "/attribute.get", method = RequestMethod.GET)
	public String entityList(Model model,
			@RequestParam(value = "page", required = false) Integer number,
			@RequestParam(value = "entity_id", required = false) String entity_id) {
		String where = entity_id==null?null:(" where entity_id='"+entity_id+"'");
		int size = Constant.PAGE_SIZE;
		if(null!=where)
			size=999;
		Page page = new Page(number==null?1:number, size);
		List<Attribute> list = attributeDaoService.selectPageByIdDesc(page, where);
		model.addAttribute("page", page);
		model.addAttribute("pageurl", "/metadata/attribute.get?page=");		
		model.addAttribute("itemList", list);
		model.addAttribute("fieldList", DbUtil.GetIdFieldNameList(Attribute.class));
		return "metadata/attribute";
	}
	
}