package cn.ly.singup.service.back.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import cn.ly.singup.dao.IDictionaryDAO;
import cn.ly.singup.service.back.IDictionaryServiceBack;
import cn.ly.singup.service.back.abs.AbstractServiceBack;
@Service
public class DictionaryServiceBackImpl extends AbstractServiceBack implements IDictionaryServiceBack {

	@Resource
	private IDictionaryDAO dictionaryDAO ;
	
	
	@RequiresRoles("bespeak")
	@RequiresPermissions("bespeak:list")
	@Override
	public Map<String, Object> listBespeak() {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allEdus",this.dictionaryDAO.findAllByItem("edus")) ;
		map.put("allBetypes",this.dictionaryDAO.findAllByItem("betype")) ;
		map.put("allSources",this.dictionaryDAO.findAllByItem("source")) ;
		return map ;
	}

}
