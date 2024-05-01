package com.tan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tan.pojo.BorrowRecord;
import com.tan.pojo.Result;
import com.tan.service.ServiceBorrow;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerBorrow {
    @Autowired
    private ServiceBorrow serviceBorrow;

    @PostMapping("/borrow")
    public Result borrowBook(@RequestParam Integer bookId, HttpServletRequest request) throws Exception {
        serviceBorrow.borrowBook(bookId,request);
        return Result.success("借书成功");
    }

    @PostMapping("/return")
    public Result returnBook(@RequestParam Integer recordId) {
        serviceBorrow.returnBook(recordId);
        return Result.success("还书成功");
    }

    // 其他API端点...
}
