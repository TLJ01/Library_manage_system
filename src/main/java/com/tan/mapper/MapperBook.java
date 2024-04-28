package com.tan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan.pojo.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapperBook extends BaseMapper<Book> {

}
