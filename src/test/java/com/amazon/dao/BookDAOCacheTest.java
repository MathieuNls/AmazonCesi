package com.amazon.dao;

import com.amazon.pojo.Book;

import junit.framework.TestCase;

public class BookDAOCacheTest extends TestCase {

	public void testDeleteBook() {
		Book b = new Book("ISBN10", "ISBN13", 200, "Un Titre", "Resumé", "Author", "editor", "src/image.pmg");

		BookDAOCache.getInstance().saveBook(b);
		BookDAOCache.getInstance().deleteBookByISBN10("ISBN10");

		assertNull(BookDAOCache.getInstance().getBookByISBN10("ISBN10"));

		b = new Book("ISBN10", "ISBN13", 200, "Un Titre", "Resumé", "Author", "editor", "src/image.pmg");

		BookDAOCache.getInstance().saveBook(b);
		BookDAOCache.getInstance().deleteBookByISBN13("ISBN13");

		assertNull(BookDAOCache.getInstance().getBookByISBN13("ISBN13"));

	}

	public void testGetInstance() {

		BookDAOCache i1 = BookDAOCache.getInstance();
		BookDAOCache i2 = BookDAOCache.getInstance();

		assertEquals(i1, i2);
	}

	public void testSaveBook() {
		Book b = new Book("ISBN11", "ISBN14", 200, "Un Titre", "Resumé", "Author", "editor", "src/image.pmg");
		BookDAOCache.getInstance().saveBook(b);

		assertEquals(b, BookDAOCache.getInstance().getBookByISBN10("ISBN11"));
		assertEquals(b, BookDAOCache.getInstance().getBookByISBN13("ISBN14"));
	}

}
