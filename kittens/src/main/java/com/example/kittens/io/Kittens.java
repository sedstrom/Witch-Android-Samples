package com.example.kittens.io;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class Kittens {

    private static Kittens INSTANCE;

    private Kittens() {

    }

    public static Kittens getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Kittens();
        }
        return INSTANCE;
    }

    private final BehaviorSubject<KittensResponse> kittens = BehaviorSubject.create();

    public Observable<KittensResponse> getKittens() {
        if(!kittens.hasValue()) {
            Observable.just(mockKittens()).delay(3, TimeUnit.SECONDS).subscribe(new Consumer<KittensResponse>() {
                @Override
                public void accept(KittensResponse kittensResponse) throws Exception {
                    kittens.onNext(kittensResponse);
                }
            });
        }
        return kittens;
    }

    private KittensResponse mockKittens() {
        return new KittensResponse(
                new Kitten(0, "Meow", "https://cdn.pixabay.com/photo/2016/09/05/21/37/cat-1647775__480.jpg"),
                new Kitten(1, "Kitty", "https://cdn.pixabay.com/photo/2017/01/19/12/05/cat-1992140__480.jpg"),
                new Kitten(2, "Cookie", "https://cdn.pixabay.com/photo/2014/03/30/23/49/cat-301723__480.jpg"),
                new Kitten(3, "Garfield", "https://cdn.pixabay.com/photo/2015/08/30/10/58/cat-914110__480.jpg"),
                new Kitten(4, "Bubba", "https://cdn.pixabay.com/photo/2014/11/30/14/11/kitty-551554__480.jpg"),
                new Kitten(5, "Jax", "https://cdn.pixabay.com/photo/2014/03/30/23/35/cat-301720__480.jpg"),
                new Kitten(6, "Mario", "https://cdn.pixabay.com/photo/2013/12/12/03/08/kitten-227009__480.jpg"),
                new Kitten(7, "Luigi", "https://cdn.pixabay.com/photo/2016/11/24/12/55/kitten-1856134__480.jpg"),
                new Kitten(8, "Luigi", "https://cdn.pixabay.com/photo/2016/09/07/23/10/cat-1652880__480.jpg"),
                new Kitten(9, "Meow", "https://cdn.pixabay.com/photo/2016/09/05/21/37/cat-1647775__480.jpg"),
                new Kitten(10, "Kitty", "https://cdn.pixabay.com/photo/2017/01/19/12/05/cat-1992140__480.jpg"),
                new Kitten(11, "Cookie", "https://cdn.pixabay.com/photo/2014/03/30/23/49/cat-301723__480.jpg"),
                new Kitten(12, "Garfield", "https://cdn.pixabay.com/photo/2015/08/30/10/58/cat-914110__480.jpg"),
                new Kitten(13, "Bubba", "https://cdn.pixabay.com/photo/2014/11/30/14/11/kitty-551554__480.jpg"),
                new Kitten(14, "Jax", "https://cdn.pixabay.com/photo/2014/03/30/23/35/cat-301720__480.jpg"),
                new Kitten(15, "Mario", "https://cdn.pixabay.com/photo/2013/12/12/03/08/kitten-227009__480.jpg"),
                new Kitten(16, "Luigi", "https://cdn.pixabay.com/photo/2016/11/24/12/55/kitten-1856134__480.jpg"),
                new Kitten(17, "Luigi", "https://cdn.pixabay.com/photo/2016/09/07/23/10/cat-1652880__480.jpg")
        );
    }
}
