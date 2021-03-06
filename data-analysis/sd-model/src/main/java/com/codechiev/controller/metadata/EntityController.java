package com.codechiev.controller.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.copycat.framework.Page;
import org.copycat.framework.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechiev.model.Constant;
import com.codechiev.model.metadata.Entity;
import com.codechiev.service.dao.EntityDaoService;

@Controller
@RequestMapping(value = "/meta-data")
public class EntityController {
	
	@Autowired
	private EntityDaoService entityDaoService;

	@RequestMapping(value = "/entity.get", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> entityList(
			@RequestParam(value = "pageNum", required = false) Integer pageNum) {
		Page page = new Page(pageNum==null?1:pageNum, Constant.PAGE_SIZE);
		List<Entity> list = entityDaoService.selectPageByIdAsc(page);	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("items", list);
		map.put("attributes", DbUtil.GetAttrList(Entity.class));
		return map;
	}
	@RequestMapping(value = "/entity.add", method = RequestMethod.POST)
	public @ResponseBody String entityAdd(Entity entity) {
		entity.setEntity_id(UUID.randomUUID().toString().replaceAll("-", ""));
		entityDaoService.insert(entity);
		return "ok";
	}
	@RequestMapping(value = "/entity.change", method = RequestMethod.POST)
	public @ResponseBody String entityChange(HttpServletRequest request,HttpServletResponse response,
			Entity entity) {
		entityDaoService.update(entity);
		return "ok";
	}
	@RequestMapping(value = "/entity.del", method = RequestMethod.POST)
	public @ResponseBody String entityChange(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "entity_id", required = true) String entity_id) {
		entityDaoService.deleteById(entity_id);
		return "ok";
	}
}