package cn.ly.singup.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ly.singup.vo.Member;

public interface IMemberDAO {
	
	public Member findById(String mid) ;
	
	/**
	 * 修改密码
	 * @param params
	 * key=mid,value=要修改密码的用户编号<br>
	 * key=newPassword, value=加密后的新密码<br>
	 * @return
	 */
	public boolean doUpdatePassword(Map<String,Object> params) ;
	/**
	 *  修改用户列表中用户的密码              但是不会修改超级管理员的密码
	 *  key =mid , value=要修改的用户编号<br>
	 *  key = password, value=加密过的新密码
	 * @param params
	 * @return
	 */
	public boolean doUpdatePasswordByMember(Map<String,Object> params);
	
	
	/**
	 * 1、locked = 0，表示该用户属于活跃用户，不锁定；<br>
	 * 2、locked = 1，表示该用户被锁定， 无法登录；<br>
	 *    包含有用户更新所需要的以下参数：<br>
	 *    1、key = mid、value = 要更新用户的mid；<br>
	 *    2、key = locked、value = 表示锁定的状态；<br>
	 * @param params
	 * @return
	 */
	public boolean doUpdateLocked(Map<String,Object> params);
	

	public boolean doCreate(Member vo) ;
	/**
	 * key = mid,
	 * key = rid
	 * @param params
	 * @return
	 */
	public boolean doCreateMemberAndRole(Map<String,Object> params);
	
	/**
	 * @param vo
	 * @return
	 */
	public boolean doUpdate (Member vo);
	
	/**
	 * 取得一个用户对应的所有的角色编号
	 * @param mid
	 * @return
	 */
	public Set<Integer> findAllRoleByMember(String mid);
	
	/**
	 * 更新之前删除掉所有的member_role与此用户关系的数据信息
	 * @param mid
	 * @return
	 */
	public boolean doRemoveMemberAndRole(String mid);
	
	public List<Member> findAll() ;
}
