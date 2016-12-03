package cn.ly.singup.dao;

import java.util.List;
import java.util.Map;

import cn.ly.singup.vo.News;

public interface INewsDAO {
	public News findByTitle(String title);
	public boolean doCreate(News vo);
	
	public List<News> findAllSplit(Map<String,Object> map);
	//发布状态  flag
	public List<News> findAllSplitByFlag(Map<String,Object> map);
	
	
	public Integer getCount(Map<String,Object> map);
	//发布状态  flag
	public Integer getCountByFlag(Map<String,Object> map);
	
	
	//回填
	public News findById(Integer nid);
	
	public boolean doUpdate(News vo);
	
	
	//删除
	//实现数据的批量删除操作，执行到此层次的Set集合一定有数据
	public boolean doRemove(Object ids[]) ;
}
