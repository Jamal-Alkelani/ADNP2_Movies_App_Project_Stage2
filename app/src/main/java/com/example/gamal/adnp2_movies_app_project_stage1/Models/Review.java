package com.example.gamal.adnp2_movies_app_project_stage1.Models;

public class Review {
    String author;
    String content;
    String readMore;

    public Review(String author, String content, String readMore) {
        this.author = author;
        this.content = content;
        this.readMore = readMore;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReadMore() {
        return readMore;
    }

    public void setReadMore(String readMore) {
        this.readMore = readMore;
    }
}
