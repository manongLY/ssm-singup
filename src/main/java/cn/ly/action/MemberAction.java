package cn.ly.action;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ly.singup.service.back.IMemberServiceBack;

@Controller
@RequestMapping("/pages/*")
public class MemberAction {
	
	private Logger log = Logger.getLogger(MemberAction.class);
	@Resource
	private IMemberServiceBack memberServiceBack;
	@RequestMapping("add")
	public ModelAndView add()throws Exception{
		return null;
	}
	
	@RequestMapping("list")
	public ModelAndView list()throws Exception{
		
		return null ;
	}
	
	
	
	@InitBinder 
	public void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(java.util.Date.class, 
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true)); 
		}
}
