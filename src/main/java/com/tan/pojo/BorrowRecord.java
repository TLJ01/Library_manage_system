package com.tan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("borrow_records")
public class BorrowRecord {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer bookId;
    private Integer userId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Integer status; // 0表示未还，1表示已归还
    private Integer isDeleted; // 0表示未删除，1表示已删除

}
