package cn.ly.singup.service.back.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Service;

import cn.ly.singup.dao.IActionDAO;
import cn.ly.singup.dao.IMemberDAO;
import cn.ly.singup.dao.IRoleDAO;
import cn.ly.singup.service.back.IMemberServiceBack;
import cn.ly.singup.service.back.abs.AbstractServiceBack;
import cn.ly.singup.vo.Member;
@Service
public class MemberServiceBackImpl extends AbstractServiceBack implements IMemberServiceBack{
	
	@Resource
	private IMemberDAO memberDAO ;
	@Resource
	private IRoleDAO roleDAO ;
	@Resource
	private IActionDAO actionDAO ;
	

	@Override
	@RequiresUser
	public boolean editPassword(String mid, String oldPassword, String newPassword) {
		Member vo = this.memberDAO.findById(mid) ;
		if(vo == null){
			return false;
		}else{
			if(oldPassword.equals(vo.getPassword())){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("mid", mid);
				params.put("newPassword",newPassword);
				return this.memberDAO.doUpdatePassword(params);
			}
		}
		return false;
	}

	
	
	
	@RequiresRoles("member")
	@RequiresPermissions("member:edit")
	@Override
	public boolean editPasswordByMember(String mid, String password) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mid", mid);
		map.put("password",password);
		return this.memberDAO.doUpdatePasswordByMember(map);
	}

	@Override
	public boolean editLocked(String mid, String locked) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mid",mid);
		map.put("locked",locked);
		return this.memberDAO.doUpdateLocked(map);
	}
	
	@RequiresRoles("member")
	@RequiresPermissions("member:list")
	public Map<String,Object> addPre(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allRoles",this.roleDAO.findAll());
		return map;
		
	}
	
	
	
	@Override
	public boolean add(Member vo,Set<Integer> rid){
		Member member = this.memberDAO.findById(vo.getMid());
		if(member == null){
			vo.setRegdate(new Date());
			vo.setSflag(0);
			vo.setLocked(0);
			if(this.memberDAO.doCreate(vo)){
				//添加membr_role
				Iterator<Integer> iter = rid.iterator();
				while(iter.hasNext()){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("mid",vo.getMid() );
					map.put("rid", iter.next());
					this.memberDAO.doCreateMemberAndRole(map);
				}
				return true;
			}
		}
		return false;
	}

	@RequiresRoles("member")
	@RequiresPermissions("member:list")
	@Override
	public List<Member> list()  {
		return this.memberDAO.findAll();
	}

	
	@RequiresRoles("member")
	@RequiresPermissions("member:edit")
	@Override
	public Map<String, Object> editPre(String mid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allRoles",this.roleDAO.findAll());
		map.put("memberRoles",this.memberDAO.findAllRoleByMember(mid));
		map.put("member",this.memberDAO.findById(mid));
		return map;
	}

	@RequiresRoles("member")
	@RequiresPermissions("member:edit")
	@Override
	public boolean edit(Member vo, Set<Integer> rid) {
		if(this.memberDAO.findById(vo.getMid())!=null){
			if(this.memberDAO.doUpdate(vo)){
				if(this.memberDAO.doRemoveMemberAndRole(vo.getMid())){
					Iterator<Integer> iter = rid.iterator();
					while(iter.hasNext()){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("mid",vo.getMid());
						map.put("rid",iter.next());
						this.memberDAO.doCreateMemberAndRole(map);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Member get(String mid) {
		return this.memberDAO.findById(mid);
	}

	
	@Override
	public Map<String, Object> listAuthByMember(String mid) {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allRoles",this.roleDAO.findAllRoleFlag(mid)) ;
		map.put("allActions",this.actionDAO.findAllActionFlag(mid));
		return map ;
	}


	
	
}
