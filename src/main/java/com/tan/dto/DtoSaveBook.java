package com.tan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午10:11
 */
//这里需要构造器,前端传进来的信息需要构造器封装
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoSaveBook {

    private String bookName;

    private String author;

    private double price;

    private Integer stock;

}
