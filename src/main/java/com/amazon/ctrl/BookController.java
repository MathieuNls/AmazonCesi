package com.amazon.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dao.APIKeyCache;
import com.amazon.dao.BookDAOCache;
import com.amazon.keys.Role;
import com.amazon.pojo.Book;

@RestController
public class BookController {

	/**
	 * curl localhost:8080/book?isbn10=1131354&key=awdawd
	 * curl localhost:8080/book?isbn13=awdawd&key=adawd
	 * 
	 * @param id
	 * @return a book with `id` as IBSN10
	 */
	@RequestMapping("/book")
	public String bookByISBN(
			@RequestParam(value = "isbn10", required = false) String isbn10,
			@RequestParam(value = "isbn13", required = false) String isbn13,
			@RequestParam(value = "key") String key) {
		
		APIKeyCache akc = APIKeyCache.getInstance();
		
		if(!akc.isAuthorized(Role.VISITOR, key)
				|| APIKeyCache.getInstance().reachedLimit("/book", key)){
			return null;
		}
		
		if (isbn10 != null) {
			akc.callSuccess("/book", key);
			return BookDAOCache.getInstance().getBookByISBN10(isbn10).toString();
		} else if (isbn13 != null) {
			akc.callSuccess("/book", key);
			return BookDAOCache.getInstance().getBookByISBN13(isbn13).toString();
		} else {
			return null;
		}
		
	}

	/**
	 * curl
	 * localhost:8080/book/create?key=awdnladn&isbn10=ad&isbn13=awd&pages=10&author=awd&editor=adw
	 * 
	 * @param isbn10
	 * @param isbn13
	 * @param pages
	 * @param author
	 * @param editor
	 * @return
	 */
	@RequestMapping("/book/create")
	public void createBook(@RequestParam(value = "isbn10") String isbn10, @RequestParam(value = "isbn13") String isbn13,
			@RequestParam(value = "title") String title, @RequestParam(value = "summary") String summary,
			@RequestParam(value = "pages") int pages, @RequestParam(value = "author") String author,
			@RequestParam(value = "editor") String editor,
			@RequestParam(value = "key") String key) {

		if(!APIKeyCache.getInstance().isAuthorized(Role.ADMIN, key)
				|| APIKeyCache.getInstance().reachedLimit("/book/create", key)){
			return;
		}
		
		BookDAOCache bdc = BookDAOCache.getInstance();
		bdc.saveBook(new Book(isbn10, isbn13, pages, title, summary, author, editor, null));
		APIKeyCache.getInstance().callSuccess("/book/create", key);
	}

	/**
	 * curl localhost:8080/book/delete?isbn10?=sfklse&key=admin
	 * Delete book with isbn10 = sfklse
	 * 
	 * curl localhost:8080/book/delete?isbn13?=sfklse&key=admin
	 * Delete book with isbn13 = sfklse
	 * 
	 * @param isbn10
	 */
	@RequestMapping("/book/delete")
	public boolean deleteBook(@RequestParam(value = "isbn10", required = false) String isbn10,
			@RequestParam(value = "isbn13", required = false) String isbn13,
			@RequestParam(value = "key") String key) {
		
		if(!APIKeyCache.getInstance().isAuthorized(Role.ADMIN, key)
				|| APIKeyCache.getInstance().reachedLimit("/book/delete", key)){
			return false;
		}
		
		BookDAOCache bdc = BookDAOCache.getInstance();
		if (isbn10 != null) {
			bdc.deleteBookByISBN10(isbn10);
			APIKeyCache.getInstance().callSuccess("/book/delete", key);
		} else if (isbn13 != null) {
			bdc.deleteBookByISBN13(isbn13);
			APIKeyCache.getInstance().callSuccess("/book/delete", key);
		} else {
			return false;
		}
		return true;

	}

}
