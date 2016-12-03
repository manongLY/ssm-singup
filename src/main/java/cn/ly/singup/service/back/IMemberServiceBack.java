package cn.ly.singup.service.back;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ly.singup.vo.Member;

public interface IMemberServiceBack {
	public boolean editPasswordByMember(String mid,String password)throws Exception;
	public boolean editLocked(String mid,String locked)throws Exception;
	public boolean editPassword(String mid,String oldPassword,String newPassword) ;
	/**
	 * 调用IRoleDAO.findAll 方法
	 * @return
	 * key = allRoles, value 
	 */
	public Map<String,Object> addPre();
	
	/**
	 * 1、检查要追加的 mid 是否存在，findById<br>
	 * 2、调用 doCreate(Member vo)<br>
	 * 3、调用doCreateMemberAndRole( )
	 * key=rid
	 * @param vo
	 * @param rid
	 * @return
	 */
	public boolean add(Member vo,Set<Integer> rid);
	
	/**
	 * key=allRoles ,value=全部角色 
	 * key=memberRoles ，value=此用户编号下的所有的角色编号
	 * key=member ，value=查询出来的用户信息
	 * @param mid
	 * @return
	 */
	public Map<String,Object> editPre(String mid);
	
	
	/**
	 * 1、修改用户信息
	 * 2、删除已有的角色与用户关系
	 * 3、重新保存用户与角色关系
	 * @param vo
	 * @param mid
	 * @return
	 */
	public boolean edit(Member vo,Set<Integer> rid);
	
	public List<Member> list();
	
	
	
	public Member get(String mid) ;
	
	public Map<String,Object> listAuthByMember(String mid) ;
}
