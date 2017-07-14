package com.example.kittens.io;

import java.util.Arrays;
import java.util.List;

public class KittensResponse {

    public final List<Kitten> kittens;

    public KittensResponse(Kitten... kittens) {
        this.kittens = Arrays.asList(kittens);
    }
}
