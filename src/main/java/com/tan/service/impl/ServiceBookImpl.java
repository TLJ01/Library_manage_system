package com.tan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tan.mapper.MapperBook;
import com.tan.pojo.Book;
import com.tan.pojo.PageBean;
import com.tan.pojo.User;
import com.tan.service.ServiceBook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean findByPage(Integer currentPage, Integer pageSize) {
        //逻辑删除字段,查询未被删除的书籍
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getIsDeleted,1);

        IPage<Book> page = new Page<>(currentPage,pageSize);
        bookMapper.selectPage(page,queryWrapper);
        //
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
        //逻辑删除字段,查询未被删除的书籍+模糊查询
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getIsDeleted,1);
        queryWrapper.like(Book::getBookName,name);
        return this.list(queryWrapper); // 使用MyBatis-Plus的list方法进行查询
    }

    /**
     * 新增书籍
     * @param book
     */
    @Override
    public Boolean insert(Book book, HttpServletRequest request) throws Exception {
        if (isAdmin(request)) {
            bookMapper.insert(book);
            return true;
        }
        else return false;
    }

    /**
     * 更新书籍
     * @param book
     */
    @Override
    public Boolean update(Book book, HttpServletRequest request) throws Exception {
        if (isAdmin(request)) {
            UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",book.getId());
            bookMapper.update(book,updateWrapper);
            return true;
        }else return false;
    }

    /**
     * 根据id查询书籍
     * @param id
     * @return
     */
    @Override
    public Book selectById(Integer id) {
        //逻辑删除字段,查询未被删除的书籍
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getId,id);
        queryWrapper.eq(Book::getIsDeleted,1);
        return bookMapper.selectOne(queryWrapper);
    }

    /**
     * 根据id删除书籍
     * @param id
     */
    @Override
    public Boolean deleteById(Integer id,HttpServletRequest request) throws Exception {
        if (isAdmin(request)){
            bookMapper.deleteById(id);
            return true;
        }else return false;
    }

    /**
     * 判断当前用户是否是管理员-->roleId=1
     * @param request
     * @return
     */
    public Boolean isAdmin(HttpServletRequest request) throws Exception {
        //获取请求头中的jwt
        String jwt = request.getHeader("token");
        //获取redis中的用户信息-->token+jwt是key
        //基于token获取redis中的对象
        String jsonUser = (String) redisTemplate.opsForValue().get("token:" + jwt);
        // 使用Jackson的ObjectMapper将JSON字符串反序列化为Java对象
        ObjectMapper objectMapper = new ObjectMapper();
        User loginUser = objectMapper.readValue(jsonUser, User.class); // 假设User是您的用户类

        //从loginUser对象中获取roleId
        Integer roleId = loginUser.getRoleId(); // 假设User类中有getRoleId方法

        return roleId == 1;
    }

}
