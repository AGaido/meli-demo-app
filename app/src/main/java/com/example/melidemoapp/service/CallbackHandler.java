package com.example.melidemoapp.service;

import com.example.melidemoapp.model.Response;

/**
 * Created by alejandro.gaidolfi on 16/07/20.
 */
public interface CallbackHandler {

    void onComplete(retrofit2.Response<Response> response);

    void onError(Throwable t);

}
