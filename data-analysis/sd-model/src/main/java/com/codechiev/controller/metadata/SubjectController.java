package com.codechiev.controller.metadata;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechiev.model.JsTreeSimpleNode;
import com.codechiev.model.metadata.Subject;
import com.codechiev.service.dao.SubjectDaoService;

@Controller
@RequestMapping(value = "/meta-data")
public class SubjectController {
	
	@Autowired
	private SubjectDaoService subjectDaoService;

	@RequestMapping("/subject.get")
	public @ResponseBody List<JsTreeSimpleNode> subjectList(Model model) {
		List<Subject> sblist = subjectDaoService.selectByIdAsc(0, 100);
		List<JsTreeSimpleNode> treelist = new ArrayList<JsTreeSimpleNode>();
		for(Subject sb:sblist){
			treelist.add(new JsTreeSimpleNode(sb.getSubject_area_id(), sb.getParent_subject_area_id(), sb.getSubject_area_name(), sb));
		}
		return treelist;
	}

	//post methods
	@RequestMapping(value = "/subject.add", method = RequestMethod.POST)
	public @ResponseBody String subjectAdd(HttpServletRequest request,HttpServletResponse response,
			Subject subject) {
		subjectDaoService.insert(subject);
		return "ok";
	}
	@RequestMapping(value = "/subject.change", method = RequestMethod.POST)
	public @ResponseBody String subjectChange(HttpServletRequest request,HttpServletResponse response,
			Subject subject) {
		subjectDaoService.update(subject);
		return "ok";
	}
	@RequestMapping(value = "/subject.delete", method = RequestMethod.POST)
	public @ResponseBody String subjectDelete(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("ids") String[] ids) {

		for(String id:ids){
			subjectDaoService.deleteCascade(id);
		}
		
		return "ok";
	}
}