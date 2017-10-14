package com.itheima.dao;

import java.util.List;

import com.itheima.pojo.Book;

public interface BookDao {

	/**
	 * 查询所有book数据
	 * @return
	 */
	public List<Book> queryBookList();
	
}
