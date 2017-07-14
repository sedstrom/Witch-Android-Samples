package com.example.kittens.ui.grid;


import com.example.kittens.io.Kittens;
import com.example.kittens.io.KittensResponse;
import com.example.kittens.utils.StateBinder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

class GridPresenter {

    private GridState state = new GridState();

    GridPresenter(final StateBinder<GridState> stateBinder) {

        Kittens.getInstance().getKittens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<KittensResponse>() {
                    @Override
                    public void accept(KittensResponse kittensResponse) throws Exception {
                        state.items  = kittensResponse.kittens;
                        stateBinder.bind(state);
                    }
                });
    }


}
