package cn.ly.singup.dao;

import java.util.List;

import cn.ly.singup.vo.Dictionary;

public interface IDictionaryDAO {
	/**
	 * 根据数据字典的分类查询出所有相关的数据信息
	 * @param item
	 * @return
	 */
	public List<IDictionaryDAO> findAllByItem(String item);
	
	public Dictionary findById(int dtid) ;
}
