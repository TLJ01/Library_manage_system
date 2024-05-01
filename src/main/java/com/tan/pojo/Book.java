package com.tan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午9:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("books")
public class Book {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String bookName;

    private String author;

    private String category;

    private Integer status; // 1表示可借，0表示已借出，等等

    private Integer isDeleted; // 1表示未删除，0表示已删除

}