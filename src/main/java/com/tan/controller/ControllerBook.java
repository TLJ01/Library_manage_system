package com.tan.controller;

import com.tan.dto.DtoSaveBook;
import com.tan.mapper.MapperBook;
import com.tan.pojo.Book;
import com.tan.pojo.PageBean;
import com.tan.pojo.Result;
import com.tan.service.ServiceBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午9:25
 */
@Slf4j
@RestController
@RequestMapping("/books")
public class ControllerBook {

    @Autowired
    private MapperBook bookMapper;

    @Autowired
    private ServiceBook serviceBook;

    /**
     * 根据id查询书籍
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        return Result.success(bookMapper.selectById(id));
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result page(Integer currentPage, Integer pageSize){
       PageBean pageBean = serviceBook.findByPage(currentPage,pageSize);
       return Result.success(pageBean);
    }

    /**
     * 根据名字进行模糊查询
     * @param bookName
     * @return
     */
    @GetMapping
    public List<Book> findBooksByName(@RequestParam String bookName) {
        return serviceBook.findByName(bookName);
    }

    /**
     * 插入书籍
     * @param dtoSaveBook
     * @return
     */
    @PostMapping("/insert")
    public Result save(@RequestBody DtoSaveBook dtoSaveBook){
        serviceBook.insert(dtoSaveBook);
        return Result.success();
    }

    /**
     * 根据id删除书籍
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        bookMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 更新书籍
     * @param book
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book){
        serviceBook.update(book);
        return Result.success();
    }

}
