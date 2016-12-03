package cn.ly.singup.service.back.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import cn.ly.singup.dao.IDictionaryDAO;
import cn.ly.singup.dao.INewsDAO;
import cn.ly.singup.service.back.INewsServiceBack;
import cn.ly.singup.service.front.abs.AbstractServiceFront;
import cn.ly.singup.vo.News;
@Service
public class NewsServiceBackImpl extends AbstractServiceFront implements INewsServiceBack {

	@Resource
	private INewsDAO newsDAO ;
	@Resource
	private IDictionaryDAO dictionaryDAO;;
	
	
	
	@RequiresRoles("news")
	@RequiresPermissions("news:add")
	@Override
	public Map<String, Object> addPre() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allNewsType", this.dictionaryDAO.findAllByItem("news"));
		return map ;
	}

	
	@RequiresRoles("news")
	@RequiresPermissions("news:add")
	@Override
	public boolean add(News vo) {
		vo.setPubdate(new Date());
		//查询是否已有该title的公告
		if(this.newsDAO.findByTitle(vo.getTitle())==null){
			return this.newsDAO.doCreate(vo) ;
		}
		return false;
	}

	@Override
	public News getByTitle(String title) {
		return this.newsDAO.findByTitle(title);
	}


	@Override
	public Map<String, Object> list(String column, String keyword, int currentPage, int lineSize) {
		Map<String,Object> params = super.handleParams(column, keyword, currentPage, lineSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("allNews",this.newsDAO.findAllSplit(params));
		result.put("newsCount", this.newsDAO.getCount(params));
		return result ;
	}


	@Override
	public Map<String, Object> listNone(String column, String keyword, int currentPage, int lineSize) {
		Map<String,Object> params = super.handleParams(column, keyword, currentPage, lineSize);
		params.put("flag",0);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("allNews",this.newsDAO.findAllSplitByFlag(params));
		result.put("newsCount",this.newsDAO.getCountByFlag(params));
		return result;
	}


	@RequiresRoles("news")
	@RequiresPermissions("news:edit")
	@Override
	public Map<String, Object> editPre(int nid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allNewsType",this.dictionaryDAO.findAllByItem("news"));
		map.put("news",this.newsDAO.findById(nid));
		return map;
	}


	
	@RequiresRoles("news")
	@RequiresPermissions("news:edit")
	@Override
	public boolean edit(News vo) {
		News oldNews = this.newsDAO.findByTitle(vo.getTitle());  //标题
		if(oldNews!=null){
			if(!vo.getNid().equals(oldNews.getNid())){		//新闻编号不同  ，跳出
				return false;		
			}
		}
		return this.newsDAO.doUpdate(vo);
	}

	@Override
	@RequiresRoles("news")
	@RequiresPermissions("news:edit")
	public boolean remove(Set<Integer> ids) {
		if (ids == null || ids.size() == 0) {
			return false ;
		}
		return this.newsDAO.doRemove(ids.toArray()); 
	}


	@Override
	@RequiresRoles("news")
	@RequiresPermissions("news:list")
	public List<News> listByFlag(int currentPage, int lineSize, int flag) {
		Map<String,Object> params = super.handleParams(null, null, currentPage, lineSize);
		params.put("flag", flag);
		return this.newsDAO.findAllSplitByFlag(params);
	} 

}
