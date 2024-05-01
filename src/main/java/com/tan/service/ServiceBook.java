package com.tan.service;

import com.tan.pojo.Book;
import com.tan.pojo.PageBean;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ServiceBook {

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean findByPage(Integer currentPage, Integer pageSize);

    /**
     * 根据名字模糊查询
     * @param name
     * @return
     */
    List<Book> findByName(String name);

    /**
     * 新增书籍
     * @param book
     */
    Boolean insert(Book book, HttpServletRequest request) throws Exception;

    /**
     * 更新书籍
     * @param book
     */
    Boolean update(Book book, HttpServletRequest request) throws Exception;

    /**
     * 根据id查询书籍
     * @param id
     * @return
     */
    Book selectById(Integer id);

    /**
     * 根据id删除书籍
     * @param id
     */
    Boolean deleteById(Integer id, HttpServletRequest request) throws Exception;
}
