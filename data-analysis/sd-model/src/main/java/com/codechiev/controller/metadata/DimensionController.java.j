package com.codechiev.controller.metadata;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.copycat.framework.Page;
import org.copycat.framework.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechiev.model.Constant;
import com.codechiev.model.JsTreeNode;
import com.codechiev.model.metadata.DimIndex;
import com.codechiev.model.metadata.Dimension;
import com.codechiev.service.dao.DimIndexDaoService;
import com.codechiev.service.dao.DimensionDaoService;
import com.codechiev.util.JsonUtils;

@Controller
@RequestMapping(value = "/metadata")
public class DimensionController {
	
	@Autowired
	private DimensionDaoService dimDaoService;
	@Autowired
	private DimIndexDaoService dimIndexDaoService;

	@RequestMapping("/dimension")
	public String subjectList(Model model) {
		List<DimIndex> itemlist = dimIndexDaoService.selectByIdAsc(0, 100);
		List<JsTreeNode> treelist = new ArrayList<JsTreeNode>();
		for(DimIndex item:itemlist){
			treelist.add(new JsTreeNode(item.getDim_index_id(), item.getParent_dim_index_id(), item.getDim_index_name(), ""));
		}
		model.addAttribute("treelist", JsonUtils.toJson(treelist));
		return "metadata/dimindex";
	}

	//post methods
	@RequestMapping(value = "/dimension/add", method = RequestMethod.POST)
	public @ResponseBody String subjectAdd(HttpServletRequest request,HttpServletResponse response,
			Dimension obj) {
		obj.setCreate_time(System.currentTimeMillis());
		dimDaoService.insert(obj);
		return "ok";
	}
	@RequestMapping(value = "/dimension/change", method = RequestMethod.POST)
	public @ResponseBody String subjectChange(HttpServletRequest request,HttpServletResponse response,
			Dimension obj) {
		dimDaoService.update(obj);
		return "ok";
	}
	@RequestMapping(value = "/dimension/delete", method = RequestMethod.POST)
	public @ResponseBody String dimDelete(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id") String id){
		dimDaoService.deleteById(id);
		return "ok";
	}
	
	@RequestMapping(value = "/dim-index/add", method = RequestMethod.POST)
	public @ResponseBody String dimIndexAdd(HttpServletRequest request,HttpServletResponse response,
			DimIndex obj) {
		dimIndexDaoService.insert(obj);
		return "ok";
	}
	@RequestMapping(value = "/dim-index/change", method = RequestMethod.POST)
	public @ResponseBody String dimIndexChange(HttpServletRequest request,HttpServletResponse response,
			DimIndex obj) {
		dimIndexDaoService.update(obj);
		return "ok";
	}
	@RequestMapping(value = "/dim-index/delete", method = RequestMethod.POST)
	public @ResponseBody String dimIndextDelete(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("ids") String idjson) {

		try {
			String[] ids = JsonUtils.toObject(String[].class, idjson);
			for(String id:ids){
				dimIndexDaoService.deleteCascade(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "ok";
	}
	
	@RequestMapping(value = "/dimension.get", method = RequestMethod.GET)
	public String entityList(Model model,
			@RequestParam(value = "page", required = false) Integer number) {
		Page page = new Page(number==null?1:number, Constant.PAGE_SIZE);
		List<Dimension> list = dimDaoService.selectPageByIdAsc(page);
		model.addAttribute("page", page);
		model.addAttribute("pageurl", "/metadata/dimension.get?page=");		
		model.addAttribute("itemList", list);
		model.addAttribute("fieldList", DbUtil.GetIdFieldNameList(Dimension.class));
		return "metadata/dimension";
	}
}