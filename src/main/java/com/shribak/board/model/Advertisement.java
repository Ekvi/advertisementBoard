package com.shribak.board.model;



public class Advertisement  {
    private String author;
    private long date;
    private String category;
    private String title;
    private String content;

    public Advertisement() {}

    public Advertisement(String author, long date, String category, String title, String content) {
        this.author = author;
        this.date = date;
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
