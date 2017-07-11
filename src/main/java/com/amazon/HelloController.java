package com.amazon;

import org.springframework.web.bind.annotation.RestController;

import com.amazon.dao.BookDAOCache;
import com.amazon.pojo.Book;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "Hello";
    }  
}