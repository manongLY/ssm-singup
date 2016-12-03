package cn.ly.singup.service.front;

import java.util.Map;

import cn.ly.singup.vo.News;

public interface INewsServiceFront {
	public News show(int nid);
	
	public Map<String,Object> list(String column,String keyword,int currentPage,int lineSize) ;
}
