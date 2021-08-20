package com.scriptech.vstudy.model;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class booksModel extends ArrayList<booksModel> {
    @PropertyName("book_name")
    public String book_name;
    @PropertyName("book_img")
    public String book_img;
    @PropertyName("book_link")
    public String book_link;
    @PropertyName("book_author")
    public String book_author;

    public String getBookName() {
        return book_name;
    }

    public String getBookImage() {
        return book_img;
    }

    public String getBookLink() {
        return book_link;
    }

    public String getBookAuthor() {
        return book_author;
    }
}
