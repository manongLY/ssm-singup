package cn.ly.singup.service.front.abs;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractServiceFront {

	protected Map<String,Object> handleParams(String column,String keyword,int currentPage,int lineSize){
		Map<String,Object> map = new HashMap<String,Object>();
		if("".equals(column)||column==null||"null".equalsIgnoreCase(column)){
			
		}else{
			map.put("column", column);
		}
		if("".equals(keyword)||keyword==null||"null".equalsIgnoreCase(keyword)){
			
		}else{
			map.put("keyword", "%"+keyword+"%");
		}
		if((currentPage-1)<0){
			map.put("start", 0);
		}else{
			map.put("start",(currentPage-1)*lineSize);
		}
		map.put("lineSize",lineSize>0?lineSize:5);
		return map;
			
	}
	
}
