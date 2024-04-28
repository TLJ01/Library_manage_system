package com.tan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan.dto.DtoSaveBook;
import com.tan.mapper.MapperBook;
import com.tan.pojo.Book;
import com.tan.pojo.PageBean;
import com.tan.service.ServiceBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午9:38
 */
@Slf4j
@Service
public class ServiceBookImpl extends ServiceImpl<MapperBook,Book> implements ServiceBook {

    @Autowired
    private MapperBook bookMapper;

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean findByPage(Integer currentPage, Integer pageSize) {
        IPage<Book> page = new Page<>(currentPage,pageSize);
        bookMapper.selectPage(page,null);
        // TODO 查询总数为0
        log.info("查询总数:{}",page.getTotal());
        return new PageBean(page.getTotal(),page.getRecords());
    }

    /**
     * 根据名字模糊查询
     * @param name
     * @return
     */
    @Override
    public List<Book> findByName(String name) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("book_name", name); // 模糊查询条件
        return this.list(queryWrapper); // 使用MyBatis-Plus的list方法进行查询
    }

    /**
     * 新增书籍
     * @param dtoSaveBook
     */
    @Override
    public void insert(DtoSaveBook dtoSaveBook) {
        Book book = new Book();
        BeanUtils.copyProperties(dtoSaveBook,book);
        // TODO 书籍的id没有自动增长
        book.setUpdateTime(LocalDateTime.now());
        bookMapper.insert(book);
    }

    /**
     * 更新书籍
     * @param book
     */
    @Override
    public void update(Book book) {
        UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",book.getId());
        book.setUpdateTime(LocalDateTime.now());
        bookMapper.update(book,updateWrapper);
    }

}
