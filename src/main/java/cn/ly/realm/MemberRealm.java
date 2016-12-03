package cn.ly.realm;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.ly.singup.service.back.IMemberServiceBack;
import cn.ly.singup.vo.Member;
import cn.ly.util.encrypt.MyPasswordEncrypt;

public class MemberRealm extends AuthorizingRealm{

	private Logger  log = Logger.getLogger(MemberRealm.class) ;
	
	@Resource 
	private IMemberServiceBack memberServiceBack ;
	
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("++++++++++++2、角色与权限++++++++++++++++++++") ;
		String username = (String) principals.getPrimaryPrincipal() ;
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo() ;
		Map<String,Object> map = this.memberServiceBack.listAuthByMember(username) ;
		Set<String> allRoles = (Set<String>) map.get("allRoles") ;
		Set<String> allActions = (Set<String>) map.get("allActions") ;
		auth.setRoles(allRoles);
		auth.setStringPermissions(allActions);
		return auth ;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("*********************1、用户登录验证********************");
		String username = (String) token.getPrincipal() ;			// 取得用户名
		// 需要通过用户名取得用户的完整信息，利用业务层操作
		Member vo = this.memberServiceBack.get(username) ;			 // 根据后台业务查询用户的完整数据
		if(vo == null ){
			log.info("用户名不存在");
			throw new UnknownAccountException("用户名不存在") ;
		}else{	// 进行密码的验证处理	
			//	取得加密后的密码
			String password = MyPasswordEncrypt.encryptPassword(new String((char[]) token.getCredentials())) ; 
			if(vo.getPassword().equals(password)){
				AuthenticationInfo auth = new SimpleAuthenticationInfo(username, password, "memberRealm");
				return auth ;
			}else{
				log.info("密码错误");
				throw new IncorrectCredentialsException("密码错误") ;
			}
		}
	}
	
}
