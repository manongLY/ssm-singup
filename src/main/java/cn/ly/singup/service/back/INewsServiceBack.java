package cn.ly.singup.service.back;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ly.singup.vo.News;

public interface INewsServiceBack {
	
	/**
	 * 1、要知道所有的新闻数据分类，查询：IDictionaryDAO.findAllByItem()方法；<br>
	 * @return
	 * 1、key = allNewsType、value = IDictionaryDAO.findAllByItem("news")
	 */
	public Map<String,Object> addPre();
	
	
	
	public boolean add(News vo);
	
	
	
	/**
	 * 主要是为了Ajax验证使用
	 * @param title
	 * @return
	 */
	public News getByTitle(String title );
	
	
	//分页查询
	public Map<String,Object> list(String column,String keyword,int currentPage ,int lineSize);
	
	
	//草稿箱查询
	public Map<String,Object> listNone(String column,String keyword,int currentPage ,int lineSize);
	
	
	
	//回填处理
	public Map<String,Object> editPre(int nid);
	public boolean edit(News vo);
	
	public boolean remove(Set<Integer> ids) ;
	
	
	
	public List<News> listByFlag(int currentPage,int lineSize,int flag);
}
