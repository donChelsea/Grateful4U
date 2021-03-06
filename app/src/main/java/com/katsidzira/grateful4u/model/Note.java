package com.katsidzira.grateful4u.model;

public class Note {

    private String title;
    private String description;
    private String date;
    private String mood;
    private int color;

    public Note(String title, String description, String date, String mood, int color) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.mood = mood;
        this.color = color;
    }

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
