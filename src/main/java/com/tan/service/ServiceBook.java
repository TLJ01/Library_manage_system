package com.tan.service;

import com.tan.dto.DtoSaveBook;
import com.tan.pojo.Book;
import com.tan.pojo.PageBean;

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
     * @param dtoSaveBook
     */
    void insert(DtoSaveBook dtoSaveBook);

    /**
     * 更新书籍
     * @param book
     */
    void update(Book book);
}
