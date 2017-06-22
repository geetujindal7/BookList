package com.example.hi.books;
/**
 * Created by hi on 29-04-2017.
 */
public class Book {
   private String author;
    private String publisher;
   private String title;
    public Book(String mauthor  , String mtitle, String mpublisher){
        author = mauthor;
        title = mtitle;
        publisher = mpublisher;
    }
    public String getAuthor()
    {
        return author;
    }
    public String getPublisher()
    {
        return publisher;
    }
    public String getTitle()
    {
    return title;
    }
}