package cn.ly.singup.action.back;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ly.singup.service.back.IMemberServiceBack;
import cn.ly.singup.vo.Member;
import cn.ly.util.action.AbstractAction;
import cn.ly.util.encrypt.MyPasswordEncrypt;
@Controller
@RequestMapping("/admin/member/*")
public class MemberActionBack extends AbstractAction{

	@Resource
	private IMemberServiceBack memberServiceBack;
	
	@RequestMapping("list")
	@RequiresUser
	@RequiresRoles("member")
	@RequiresPermissions("member:list")
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView(super.getValue("back.member.list.page"));
		try {
			mav.addObject("allMembers",this.memberServiceBack.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequiresUser
	@RequestMapping("editPassword")
	public ModelAndView editPassword(String newpassword,String oldpassword){
		ModelAndView mav = new ModelAndView(super.getValue("forward.back.page")) ;
		String newPassword = MyPasswordEncrypt.encryptPassword(newpassword) ;
		String oldPassword = MyPasswordEncrypt.encryptPassword(oldpassword) ;
		if(this.memberServiceBack.editPassword(super.getMid(), oldPassword, newPassword)){
			super.setMsgAndUrl(mav, "back.member.edit.password.success.msg","front.index.action");
		}else{
			super.setMsgAndUrl(mav, "back.member.edit.password.failure.msg", "front.index.action");
		}
		super.logout();
		return mav ;
	}
	
	
	@RequestMapping("editPasswordPre")
	public ModelAndView editPasswordPre(){
		return new ModelAndView(super.getValue("back.member.editpassword.page")) ;
	}
	
	//整个对于密码的更新处理是利用模态窗口并且结合Ajax 异步处理完成的，所以应该在MemberActionBack 里面追加有新的操
	//作方法，同时此方法不会进行跳转。
	@RequiresRoles("member")
	@RequiresPermissions("member:edit")
	@RequiresUser
	@RequestMapping("editPasswordByMember")
	public ModelAndView editPasswordByMember(String mid,String password,HttpServletResponse response){
		try {
			super.print(response, this.memberServiceBack.editPasswordByMember(mid, MyPasswordEncrypt.encryptPassword(password)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequiresRoles("member")
	@RequiresPermissions("member:edit")
	@RequiresUser
	@RequestMapping("editLocked")
	public ModelAndView editLocked(String mid,String locked,HttpServletResponse response){
		try {
			super.print(response, this.memberServiceBack.editLocked(mid, locked));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	
	
	
	@RequiresRoles("member")
	@RequiresPermissions("member:add")
	@RequiresUser
	@RequestMapping("addPre")
	public ModelAndView addPre(){
		ModelAndView mav = new ModelAndView(super.getValue("back.member.add.page"));
		mav.addAllObjects(this.memberServiceBack.addPre());
		return mav;
	}
	
	@RequiresRoles("member")
	@RequiresPermissions("member:add")
	@RequiresUser
	@RequestMapping("add")
	public ModelAndView add(Member vo,HttpServletRequest request){
		vo.setPassword(MyPasswordEncrypt.encryptPassword(vo.getPassword()));
		ModelAndView mav = new ModelAndView(super.getValue("forward.back.page"));
		if(this.memberServiceBack.add(vo,super.getSetByInteger(request, "rid"))){
			super.setMsgAndUrl(mav, "vo.add.success.msg","back.member.add.action");
		}else{
			super.setMsgAndUrl(mav, "vo.add.failure.msg", "back.member.add.action");
		}
		return mav ;
	}
	
	@RequestMapping("checkMid")
	@RequiresUser
	public ModelAndView checkMid(String mid,HttpServletResponse response){
		if("".equals(mid)||mid==null){
			super.print(response, false);
		}else{
			try {
				super.print(response,this.memberServiceBack.get(mid)==null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null; 
	}
	
	
	@RequiresRoles("member")
	@RequiresPermissions("member:edit")
	@RequiresUser
	@RequestMapping("editPre")
	public ModelAndView editPre(String mid){
		ModelAndView mav = new ModelAndView(super.getValue("back.member.edit.page"));
		mav.addAllObjects(this.memberServiceBack.editPre(mid));
		return mav;
	}
	
	
	@RequiresRoles("member")
	@RequiresPermissions("member:edit")
	@RequiresUser
	@RequestMapping("edit")
	public ModelAndView edit(Member vo ,HttpServletRequest request){
		vo.setPassword(MyPasswordEncrypt.encryptPassword(vo.getPassword()));
		ModelAndView mav = new ModelAndView(super.getValue("forward.back.page"));
		if(this.memberServiceBack.edit(vo, super.getSetByInteger(request, "rid"))){
			super.setMsgAndUrl(mav, "vo.edit.success.msg","back.member.edit.action");
		}else{
			super.setMsgAndUrl(mav, "vo.edit.failure.msg", "back.member.edit.action");
		}
		return mav;
	}
	
	
	
	
	
	
	@Override
	public String getFileUploadDir() {
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
