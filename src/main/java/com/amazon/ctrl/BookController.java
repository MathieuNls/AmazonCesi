package com.amazon.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dao.BookDAOCache;
import com.amazon.pojo.Book;

@RestController
public class BookController {
	
	@RequestMapping("/book")
	public String book(){
		
		return BookDAOCache.getInstance().getBookByISBN10("ISBN15").toString();
	}
	
	
}
