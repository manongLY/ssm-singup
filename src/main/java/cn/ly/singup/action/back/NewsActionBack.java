package cn.ly.singup.action.back;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ly.singup.service.back.INewsServiceBack;
import cn.ly.singup.vo.News;
import cn.ly.util.CreateStaticUtil;
import cn.ly.util.action.AbstractAction;
import cn.ly.util.split.SplitPageUtil;
@Controller
@RequestMapping("/admin/news/*")
public class NewsActionBack extends AbstractAction{

	@Resource
	private INewsServiceBack newsServiceBack ;
	
	@RequestMapping("create")
	@RequiresRoles("news")
	@RequiresPermissions("news:list")
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response){
		String filePath = request.getServletContext().getRealPath("/")+"WEB-INF"+File.separator+"news.static";
		new CreateStaticUtil().create(new File(filePath), this.newsServiceBack.listByFlag(1,2,1));;
		super.print(response, true);
		return null ;
	}
	
	@RequestMapping(value="remove")
	@RequiresRoles("news")
	@RequiresPermissions("news:edit")
	public ModelAndView remove(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("===========================");
		super.print(response, this.newsServiceBack.remove(super.getBatchIds(request)));
		return null;
	}  
	
	
	@RequestMapping("checkNidAndTitle")
	@RequiresRoles("news")
	@RequiresPermissions("news:edit")
	public ModelAndView checkNidAndTitle(int nid,String title,HttpServletResponse response){
		News vo = this.newsServiceBack.getByTitle(title);
		if(vo == null){
			super.print(response, false);
		}else{
			super.print(response, vo.getNid().equals(nid));
		}
		return null;
	}
	
	
	@RequiresRoles("news")
	@RequiresPermissions("news:edit")
	@RequestMapping("editPre")
	public ModelAndView editPre(int nid){
		ModelAndView mav = new ModelAndView(super.getValue("back.news.edit.page"));
		mav.addAllObjects(this.newsServiceBack.editPre(nid));
		return mav;
	}
	
	
	@RequiresRoles("news")
	@RequiresPermissions("news:edit")
	@RequestMapping("edit")
	public ModelAndView edit(News vo,MultipartFile pic,HttpServletRequest request){
		ModelAndView mav = new ModelAndView(super.getValue("forward.back.page"));
		if(pic!=null){
			if(pic.getSize()>0){
				if("nophoto.gif".equals(vo.getPhoto())){	// 原始没有图片名称
					vo.setPhoto(super.createFileName(pic));	 // 创建新的图片名称
				}
			}
			vo.setMid(super.getMid());		// 设置进行新闻创建的管理员信息
		}
		if(this.newsServiceBack.edit(vo)){
			if(pic !=null&& pic.getSize()>0){
				super.saveFile(pic, vo.getPhoto(), request);	
			}
			super.setMsgAndUrl(mav, "vo.edit.success.msg","back.news.list.action");
		}else{
			super.setMsgAndUrl(mav, "vo.edit.failure.msg","back.news.list.action");
		}
		return mav;
	}
	
	//查询草稿    flag=0
	@RequestMapping("listNone")
	@RequiresRoles("news")
	@RequiresPermissions("news:list")
	public ModelAndView listNone(HttpServletRequest request){
		SplitPageUtil spu = new SplitPageUtil(request,"title");
		ModelAndView mav = new ModelAndView(super.getValue("back.news.list.page"));
		Map<String,Object> map = this.newsServiceBack.listNone(spu.getColumn(),spu.getKeyWord(),spu.getCurrentPage(),spu.getLineSize());
		super.handleSplit(mav, map.get("newsCount"), "公告标题:title|公告摘要:abs", "back.news.listNone.action", spu);
		mav.addObject("allNews",map.get("allNews"));
		return mav ;
	}
	
	
	
	@RequestMapping("list")
	@RequiresRoles("news")
	@RequiresPermissions("news:list")
	public ModelAndView list(HttpServletRequest request){
		SplitPageUtil spu = new SplitPageUtil(request,"title");// 可以接收到所有的分页数据
		ModelAndView mav = new ModelAndView(super.getValue("back.news.list.page"));
		// 进行分页信息的查询
		Map<String,Object> map = this.newsServiceBack.list(spu.getColumn(),spu.getKeyWord(),spu.getCurrentPage(),spu.getLineSize());
		super.handleSplit(mav,map.get("newsCount"),"公告标题:title|公告摘要:abs", "back.news.list.action", spu);
		mav.addObject("allNews",map.get("allNews"));// 真正需要进行显示的数据的集合
		return mav ;
	}
	
	
	@RequiresRoles("news")
	@RequiresPermissions("news:add")
	@RequestMapping("addPre")
	public ModelAndView addPre(){
		ModelAndView mav = new ModelAndView(super.getValue("back.news.add.page"));
		mav.addAllObjects(this.newsServiceBack.addPre());
		return mav;
	}
	
	@RequiresRoles("news")
	@RequiresPermissions("news:add")
	@RequestMapping("add")
	public ModelAndView add(News vo,MultipartFile pic,HttpServletRequest request){
		ModelAndView mav = new ModelAndView(super.getValue("forward.back.page"));
		if(pic!=null){
			String fileName = super.createFileName(pic);
			vo.setMid(super.getMid());
			vo.setPhoto(fileName);
			if(this.newsServiceBack.add(vo)){
				super.saveFile(pic, fileName, request);
				super.setMsgAndUrl(mav, "vo.add.success.msg","back.news.add.action");
			}else{
				super.setMsgAndUrl(mav, "vo.add.failure.msg","back.news.add.action");
			}
		}
		return mav;
	}
	
	
	@RequiresRoles("news")
	@RequiresPermissions("news:add")
	@RequestMapping("checkTitle")
	public ModelAndView checkTitle(String title ,HttpServletResponse response){
		super.print(response, this.newsServiceBack.getByTitle(title)==null);
		return null ;
	}
	
	
	@Override
	public String getFileUploadDir() {
		return "/upload/news/";
	}

	@Override
	public String getType() {
		return "公告";
	}

}
