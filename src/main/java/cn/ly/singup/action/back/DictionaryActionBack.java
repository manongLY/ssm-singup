package cn.ly.singup.action.back;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ly.singup.service.back.IDictionaryServiceBack;
import cn.ly.singup.vo.Dictionary;
import cn.ly.util.CreateStaticUtil;
import cn.ly.util.action.AbstractAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
public class DictionaryActionBack extends AbstractAction{
	
	@Resource 
	private IDictionaryServiceBack dictionaryServiceBack;
	
	
	@RequestMapping("create")
	@RequiresRoles("bespeak")
	@RequiresPermissions("bespeak:list")
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = this.dictionaryServiceBack.listBespeak() ;
		if(map.size()>3){
			// 2、将内容输出为JSON的结构，使用JSON处理
			JSONObject obj = new JSONObject();
			Set<Map.Entry<String,Object>> set = map.entrySet();
			Iterator<Map.Entry<String,Object>> iter = set.iterator();
			while(iter.hasNext()){
				Map.Entry<String, Object> me = iter.next();
				JSONArray array = new JSONArray();
				List<Dictionary> list = (List<Dictionary>) me.getValue();
				Iterator<Dictionary> iterD = list.iterator() ;
				while(iterD.hasNext()){
					Dictionary vo = iterD.next() ;
					JSONObject temp = new JSONObject();
					temp.put("dtid",vo.getDtid()) ;
					temp.put("title",vo.getTitle()) ;
					array.add(temp);
				}
				obj.put(me.getKey(), array) ;
			}
			String filePath = request.getServletContext().getRealPath("/")+"dict.inf" ;
			new CreateStaticUtil<String>().createJSON(new File(filePath), obj);
			super.print(response, true);
		}else{
			super.print(response, false);
		}
		return null ;
	}


	@Override
	public String getFileUploadDir() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
