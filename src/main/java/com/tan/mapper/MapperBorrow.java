package com.tan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan.pojo.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapperBorrow extends BaseMapper<BorrowRecord> {
}
