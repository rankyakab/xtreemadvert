package com.xtreemadvert.xtreemads;

public class Comment {
    private String name;
    private String message;
    private String commentCalender;

    public Comment(String name,String message, String commentCalender){
        this.name = name;
        this.message=message;
        this.commentCalender=commentCalender;

    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getCommentCalender() {
        return commentCalender;
    }
}
