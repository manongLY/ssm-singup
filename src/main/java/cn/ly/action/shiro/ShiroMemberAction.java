package cn.ly.action.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ly.util.action.AbstractAction;

@Controller
public class ShiroMemberAction extends AbstractAction{

	@RequestMapping("/successUrl")
	public ModelAndView successUrl(){
		return new ModelAndView(super.getValue("shiro.successUrl.page")) ;
	}
	
	@RequestMapping("/loginUrl")
	public ModelAndView loginUrl(){
		return new ModelAndView(super.getValue("shiro.loginUrl.page")) ;
	}

	@RequestMapping("/unauthUrl")
	public ModelAndView unauthUrl(){
		return new ModelAndView(super.getValue("shiro.unauthUrl.page")) ;
	}
	@RequestMapping("/logoutUrl")
	@RequiresUser
	public ModelAndView logoutUrl(){
		ModelAndView mav = new ModelAndView(super.getValue("forward.back.page")) ;
		super.setMsgAndUrl(mav, "shiro.logout.msg","front.index.action");
		SecurityUtils.getSubject().logout() ;
		return mav ;
	}
	
	
	
	@Override
	public String getFileUploadDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
