package cn.ly.singup.service.front.impl;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ly.singup.dao.INewsDAO;
import cn.ly.singup.service.front.INewsServiceFront;
import cn.ly.singup.service.front.abs.AbstractServiceFront;
import cn.ly.singup.vo.News;
@Service
public class NewsServiceFrontImpl extends AbstractServiceFront implements INewsServiceFront {

	@Resource
	private INewsDAO newsDAO ;

	@Override
	public News show(int nid) {
		News vo = this.newsDAO.findById(nid) ;
		if(vo.getFlag().equals(1)){		// 只查询发布的公告信息
			return vo ;
		}
		return null ;
	}

	@Override
	public Map<String, Object> list(String column, String keyword, int currentPage, int lineSize) {
		Map<String,Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		map.put("flag", 1) ;	// 业务层上针对于flag进行控制
		Map<String,Object> result = new HashMap<String,Object>() ;
		result.put("allNews",this.newsDAO.findAllSplit(map));
		result.put("newsCount",this.newsDAO.getCount(map)) ;
		return result ;
	}

	
	
	
	
	

}
