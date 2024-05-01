package com.tan.controller;

import com.tan.pojo.Book;
import com.tan.pojo.PageBean;
import com.tan.pojo.Result;
import com.tan.service.ServiceBook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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
    private ServiceBook serviceBook;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 根据id查询书籍
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Book book = serviceBook.selectById(id);
        if(book == null){return Result.error("该书籍不存在");}
        else return Result.success(book);
    }


    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result page(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "5") Integer pageSize){
       PageBean pageBean = serviceBook.findByPage(currentPage,pageSize);
       return Result.success(pageBean);
    }

    /**
     * 根据名字进行模糊查询
     * @param bookName
     * @return
     */
    @GetMapping
    public Result findBooksByName(@RequestParam String bookName) {
        List<Book> books = serviceBook.findByName(bookName);
        log.info("书籍信息:{}",books);
        return Result.success(books);
    }

    /**
     * 插入书籍
     * 管理员权限
     * @param book
     * @return
     */
    @PostMapping("/insert")
    public Result save(@RequestBody Book book, HttpServletRequest request) throws Exception {
        if (serviceBook.insert(book,request))return Result.success();
        else return Result.error("您没有操作权限");
    }

    /**
     * 根据id删除书籍
     * 管理员权限
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        log.info("id:{}",id);
        if(serviceBook.deleteById(id,request))return Result.success();
        else return Result.error("您没有操作权限");
    }

    /**
     * 更新书籍
     * 管理员权限
     * @param book
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book, HttpServletRequest request) throws Exception {
        if(serviceBook.update(book,request))return Result.success();
        else return Result.error("您没有操作权限");
    }

}
