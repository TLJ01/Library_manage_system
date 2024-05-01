package com.tan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tan.pojo.BorrowRecord;
import jakarta.servlet.http.HttpServletRequest;

public interface ServiceBorrow {
    /**
     * 借书
     * @param request
     * @param bookId
     * @return
     */
    void borrowBook(Integer bookId, HttpServletRequest request) throws JsonProcessingException;

    /**
     * 还书
     * @param recordId
     * @return
     */
    void returnBook(Integer recordId);
}
