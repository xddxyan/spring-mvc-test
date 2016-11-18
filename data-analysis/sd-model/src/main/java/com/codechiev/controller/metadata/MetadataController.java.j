package com.codechiev.controller.metadata;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.copycat.framework.util.DbUtil;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechiev.model.metadata.Database;
import com.codechiev.model.metadata.Entity;
import com.codechiev.service.dao.DatabaseDaoService;
import com.codechiev.service.dao.EntityDaoService;

@Controller
@RequestMapping(value = "/metadata")
public class MetadataController {
	
	@Autowired
	private DatabaseDaoService databaseDaoService;
	
	@Autowired
	private EntityDaoService entityDaoService;
	
	@RequestMapping("/database")
	public String database(Model model) {
		List<Database> list = databaseDaoService.selectByIdAsc(0,999);
		model.addAttribute("itemList", list);
		model.addAttribute("fieldList", DbUtil.GetIdFieldNameList(Database.class));
		return "metadata/database";
	}
	
	//post methods
	@RequestMapping(value = "/database/add", method = RequestMethod.POST)
	public @ResponseBody String dbAdd(HttpServletRequest request,HttpServletResponse response,
			Database db) {
		databaseDaoService.insert(db);
		return "ok";
	}
	@RequestMapping(value = "/database/change", method = RequestMethod.POST)
	public @ResponseBody String dbChange(HttpServletRequest request,HttpServletResponse response,
			Database db) {
		databaseDaoService.update(db);
		return "ok";
	}
	@RequestMapping(value = "/database/delete", method = RequestMethod.POST)
	public @ResponseBody String dbDelete(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "database_id") String db_id) {
		databaseDaoService.deleteById(db_id);
		return "ok";
	}
	
	@ExceptionHandler(PSQLException.class)
	public @ResponseBody String handleCustomException(PSQLException ex) {
		return ex.getLocalizedMessage();
	}
}