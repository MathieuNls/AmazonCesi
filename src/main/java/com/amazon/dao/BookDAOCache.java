package com.amazon.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazon.pojo.Book;

public class BookDAOCache implements BookDAO {

	private static BookDAOCache instance;
	private List<Book> books = new ArrayList<Book>();

	private BookDAOCache() {
	}

	public static BookDAOCache getInstance() {
		if (instance == null) {
			synchronized (BookDAOCache.class) {
				if (instance == null) {
					BookDAOCache.instance = new BookDAOCache();
				}
			}
		}
		return BookDAOCache.instance;
	}

	@Override
	public Book getBookByISBN10(String isbn10) {

		for (Book b : books) {
			if (b.getISBN10().compareToIgnoreCase(isbn10) == 0) {
				return b;
			}
		}
		return null;
	}

	@Override
	public Book getBookByISBN13(String isbn13) {
		for (Book b : books) {
			if (b.getISBN13().compareToIgnoreCase(isbn13) == 0) {
				return b;
			}
		}
		return null;
	}

	@Override
	public void saveBook(Book book) {

		this.books.add(book);
	}

	@Override
	public void deleteBookByISBN10(String isbn10) {
		int i = 0;
		boolean found = false;
		for (i = 0; i < books.size(); i++) {
			if (books.get(i).getISBN10() == isbn10) {
				found = true;
				break;
			}
		}
		if (found) {
			books.remove(i);
		}
	}

	@Override
	public void deleteBookByISBN13(String isbn13) {

		int i = 0;
		boolean found = false;
		for (i = 0; i < books.size(); i++) {
			if (books.get(i).getISBN13() == isbn13) {
				found = true;
				break;
			}
		}
		if (found) {
			books.remove(i);
		}
	}

}
