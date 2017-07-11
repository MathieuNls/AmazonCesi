package com.amazon.pojo;

public class Book {
	
	private String ISBN10;
	private String ISBN13;
	private int pages;
	private String title;
	private String summary;
	private String author;
	private String editor;
	private String thumbnail;
	
	public Book(String iSBN10, String iSBN13, int pages, String title, String summary, String author, String editor,
			String thumbnail) {
		ISBN10 = iSBN10;
		ISBN13 = iSBN13;
		this.pages = pages;
		this.title = title;
		this.summary = summary;
		this.author = author;
		this.editor = editor;
		this.thumbnail = thumbnail;
	}
	
	@Override
	public String toString() {
		return "Book [ISBN10=" + ISBN10 + ", ISBN13=" + ISBN13 + ", pages=" + pages + ", title=" + title + ", summary="
				+ summary + ", author=" + author + ", editor=" + editor + ", thumbnail=" + thumbnail + "]";
	}
	public String getISBN10() {
		return ISBN10;
	}
	public void setISBN10(String iSBN10) {
		ISBN10 = iSBN10;
	}
	public String getISBN13() {
		return ISBN13;
	}
	public void setISBN13(String iSBN13) {
		ISBN13 = iSBN13;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
	
	

}
