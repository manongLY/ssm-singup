package cn.ly.singup.service.front.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ly.singup.dao.IBespeakDAO;
import cn.ly.singup.dao.IDictionaryDAO;
import cn.ly.singup.service.front.IBespeakServiceFront;
import cn.ly.singup.service.front.abs.AbstractServiceFront;
import cn.ly.singup.vo.Bespeak;
@Service
public class BespeakServiceFrontImpl extends AbstractServiceFront implements IBespeakServiceFront {

	@Resource
	private IBespeakDAO bespeakDAO ;
	
	@Resource
	private IDictionaryDAO dictionaryDAO ;
	
	@Override
	public boolean add(Bespeak vo) {
		// 1、要根据数据字典的id查询出用户的学历、报名类型、来源
		vo.setEdu(this.dictionaryDAO.findById(vo.getEduid()).getTitle());
		vo.setType(this.dictionaryDAO.findById(vo.getTypeid()).getTitle());
		vo.setSrc(this.dictionaryDAO.findById(vo.getSrcid()).getTitle());
		// 2、设置相关的信息
		vo.setIndate(new Date());	// 当前日期为报名日期
		vo.setStatus(0);			// 表示该报名信息未处理
		vo.setNote("");				// 设置为一个空字符串
		return this.bespeakDAO.doCreate(vo) ;
	}

}
