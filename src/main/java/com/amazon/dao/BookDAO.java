package com.amazon.dao;

import com.amazon.pojo.Book;

public interface BookDAO {
	
	public Book getBookByISBN10(String isbn10);
	public Book getBookByISBN13(String isbn13);
	public void saveBook(Book book);
	public void deleteBookByISBN10(String isbn10);
	public void deleteBookByISBN13(String isbn13);
}