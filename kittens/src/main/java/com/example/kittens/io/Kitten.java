package com.example.kittens.io;

public class Kitten {

    public final String name;

    public final int id;

    public final String imageUrl;

    Kitten(int id, String name, String imageUrl) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }
}
