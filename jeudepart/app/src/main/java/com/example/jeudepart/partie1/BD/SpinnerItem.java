package com.example.jeudepart.partie1.BD;

public class SpinnerItem {
    private String text;
    private int imageResId;

    public SpinnerItem(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}