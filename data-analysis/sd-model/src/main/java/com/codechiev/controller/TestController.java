package com.codechiev.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.codechiev.model.User;
import com.codechiev.service.SessionService;
import com.codechiev.service.dao.UserDaoService;
import com.codechiev.util.IJob;
import com.codechiev.util.JobQueueUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/test")
public class TestController {
	
	@Autowired
	private UserDaoService userDaoService;
	@Autowired
	private SessionService sessionService;

	@RequestMapping("/")
	@ResponseBody
	public String test(HttpServletRequest request) {
		return "test";
	}

	@RequestMapping("/error")
	public String testError(HttpServletRequest request) {
		return "error";
	}
	
	@RequestMapping("/userid/{id}")
	public @ResponseBody String userid(HttpServletRequest request,@PathVariable long id) {
		User user = userDaoService.selectById(id);
		if(null!=user)
			return user.getUsername();
		return "null";
	}
	
	//asynchronized message test
	//msg with uid -1 is show in public
	@RequestMapping("/chat")
	public String chat(HttpServletRequest request) {
		return "chat/index";
	}
	/**
	 * @param request
	 * @param touser
	 * @param msg
	 * @return
	 */
	@RequestMapping(value="/chat-send", method = RequestMethod.POST)
	public @ResponseBody String sendchat(HttpServletRequest request,
			@RequestParam(value = "touser") String touser,
			@RequestParam(value = "msg") String msg) {
		String uname =sessionService.getSession().getUsername();
		singleThreadJobService.produce(new PutMsgJob( new Msg(uname, msg),  touser));
		return "ok";
	}
	/**
	 * 非html类型
	 * @param request
	 * @param seq 确保取到最新的消息
	 * @return
	 */
	@RequestMapping(value="/chat-get", method = RequestMethod.GET)
	public @ResponseBody DeferredResult<String> getchat(HttpServletRequest request,
			@RequestParam(value = "seq") Integer seq) {
		DeferredResult<String> result = new DeferredResult<String>(15000L, "async-timeout");
		singleThreadJobService.produce(new GetMsgJob(result, seq));
		return result;
	}
	
	private Map<String, UserMsg> userMsgs = new HashMap<String, UserMsg>();
	private UserMsg getAndPut(String uid){
		UserMsg userMsg = userMsgs.get(uid);
		if(null==userMsg){
			userMsg =  new UserMsg();
			userMsgs.put(uid, userMsg);
		}
		return userMsg;
	}
	
	//one thread
	private final Deque<DeferredResult<String>> pollingQueue = new ArrayDeque<DeferredResult<String>>();
	JobQueueUtil singleThreadJobService = new JobQueueUtil();
	@PostConstruct
	void init(){
		singleThreadJobService.initSingleThread();
	}

	class GetMsgJob extends IJob{
		private DeferredResult<String> result;
		private Integer seq;

		public GetMsgJob(DeferredResult<String> result, Integer seq) {
			super();
			this.result = result;
			this.seq = seq;
		}

		@Override
		public void doJob() {
			//查看缓存是否有消息
			UserMsg userMsg=getAndPut("-1");
			String json = userMsg.getJsonMsg(seq);		
			if(null == json){
				//放入轮询队列
				pollingQueue.add(result);
			}else{
				result.setResult(json);
			}
		}
	}
	
	class PutMsgJob extends IJob{
		String to;
		Msg msg;
		public PutMsgJob(Msg msg, String to) {
			this.to = to;
			this.msg = msg;
		}
		@Override
		public void doJob() {
			//消息缓存
			UserMsg userMsg=getAndPut("-1");
			userMsg.putMsg(msg);
			Msg[] msgarr = {msg};
			String json = new Gson().toJson(msgarr);
			//公共消息群发
			if(to.equals("-1")){
				for (DeferredResult<String> polling : pollingQueue) {
					polling.setResult(json);
					pollingQueue.remove(polling);
				}
			}else{
			//私人消息,点对点发送
				//if(polling.uname equals touser) setResual...
			}
		}
	}
}

class UserMsg{
	public int seq = 0;
	public int limit = 16; 
	public Deque<Msg> msgs = new ArrayDeque<Msg>();
	public void putMsg(Msg msg){
		msgs.addLast(msg);
		if(msgs.size()>limit){
			msgs.removeFirst();
		}
		
		msg.seq = ++seq;
		if(seq>(Integer.MAX_VALUE>>1))
			seq=0;
	}
	public String getJsonMsg(int seq){
		List<Msg> list = new ArrayList<Msg>();
		for(Msg msg : msgs){
			if(msg.seq>seq){
				list.add(msg);
			}
		}
		if(list.size()>0){
			return new Gson().toJson(list);
		}
		return null;
	}
}
class Msg{
	public int seq;
	public String from;
	public String msg;
	public Msg(String from, String msg) {
		super();
		this.from = from;
		this.msg = msg;
	}
}