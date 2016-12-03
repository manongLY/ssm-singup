package cn.ly.singup.action.front;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ly.util.action.AbstractAction;

@Controller
public class RandAndCodeAction extends AbstractAction{
	
	
	@RequestMapping("/checkRandAndCode")
	public ModelAndView checkRandAndCode(String code,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession() ;
		String rand = (String) session.getAttribute("rand") ;
		if(rand == null ||"".equals(rand)){
			super.print(response, false);
			return null ;
		}
		if(code == null ||"".equals(code)){
			super.print(response, false);
			return null ;
		}
		super.print(response, code.equalsIgnoreCase(rand));
		return null ;
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
