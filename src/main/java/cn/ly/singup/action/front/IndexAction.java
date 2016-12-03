package cn.ly.singup.action.front;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ly.util.action.AbstractAction;
@Controller
public class IndexAction extends AbstractAction{

	@RequestMapping("/index")
	public ModelAndView index(){
		return new ModelAndView(super.getValue("front.index.page")) ;
	}
	
	@RequestMapping("/admin/indexBack")
	@RequiresUser
	public ModelAndView indexBack(){
		return new ModelAndView(super.getValue("shiro.successUrl.msg")) ;
	}
	
	@Override
	public String getFileUploadDir() {
		return null;
	}

	@Override
	public String getType() {
		return null;
	}

}
