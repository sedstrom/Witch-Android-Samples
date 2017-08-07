package com.example.kittens.ui.pager;

import com.example.kittens.io.Kittens;
import com.example.kittens.io.KittensResponse;
import com.example.kittens.utils.StateBinder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


public class PagerPresenter {

    private PagerState state = new PagerState();

    PagerPresenter(final StateBinder<PagerState> stateBinder, final int initId) {

        Kittens.getInstance()
                .getKittens()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<KittensResponse>() {
                    @Override
                    public void accept(KittensResponse kittensResponse) throws Exception {
                        state.kittens = kittensResponse.kittens;
                        for (int i = 0; i < kittensResponse.kittens.size(); i++) {
                            if (kittensResponse.kittens.get(i).id == initId) {
                                state.startPosition = i;
                                break;
                            }
                        }

                        stateBinder.bind(state);
                    }
                });


    }

}
