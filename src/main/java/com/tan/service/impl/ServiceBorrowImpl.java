package com.tan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tan.mapper.MapperBook;
import com.tan.mapper.MapperBorrow;
import com.tan.pojo.Book;
import com.tan.pojo.BorrowRecord;
import com.tan.pojo.Result;
import com.tan.pojo.User;
import com.tan.service.ServiceBorrow;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ServiceBorrowImpl implements ServiceBorrow {
    @Autowired
    private MapperBorrow mapperBorrow;

    @Autowired
    private MapperBook mapperBook;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 借书
     * @param request
     * @param bookId
     */
    public void borrowBook(Integer bookId, HttpServletRequest request) throws JsonProcessingException {

        /**
         * userId应该通过redis来获得
         */

        //获取请求头中的jwt
        String jwt = request.getHeader("token");
        //获取redis中的用户信息-->token+jwt是key
        //基于token获取redis中的对象
        String jsonUser = (String) redisTemplate.opsForValue().get("token:" + jwt);
        // 使用Jackson的ObjectMapper将JSON字符串反序列化为Java对象
        ObjectMapper objectMapper = new ObjectMapper();
        User loginUser = objectMapper.readValue(jsonUser, User.class); // 假设User是您的用户类
        Integer userId = loginUser.getId();

        /**
         *  默认能看见的书籍都能借,这里就不判断了
         *  -->前端通过书籍状态status,进行显示
         *  1代表可以借,0代表已经借出
         */

        //更新书籍
        Book book = mapperBook.selectById(bookId);
        book.setStatus(0);
        mapperBook.updateById(book);

        //存入借阅表
        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);
        record.setUserId(userId);
        record.setBorrowDate(LocalDate.now());

        /**
         * 还书时间-->默认一个月以内
         *
         */
        record.setReturnDate(LocalDate.now().plusDays(30));

        // 保存借阅记录
        mapperBorrow.insert(record);
    }

    /**
     * 还书
     * @param recordId
     */
    public void returnBook(Integer recordId) {

        //找到当前借阅记录对应的对象
        LambdaQueryWrapper<BorrowRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BorrowRecord::getId,recordId);
        BorrowRecord borrowRecord = mapperBorrow.selectOne(queryWrapper);

        //得到书籍的id
        Integer bookId = borrowRecord.getBookId();

        //更新书籍状态
        Book book = mapperBook.selectById(bookId);
        book.setStatus(1);
        mapperBook.updateById(book);

        //更新当前的借阅记录
        borrowRecord.setStatus(1);//1表示已经归还
        mapperBorrow.updateById(borrowRecord);

    }

}
