package com.example.melidemoapp.repository;

import com.example.melidemoapp.model.Product;
import com.example.melidemoapp.service.CallbackHandler;
import com.example.melidemoapp.service.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alejandro.gaidolfi on 13/07/20.
 */
public class ProductRepository {

    private static final String TAG = Product.class.getSimpleName();
    private WebService webService;

    public ProductRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webService = retrofit.create(WebService.class);
    }

    public void getProductsByStringSearch(String stringSearch, final CallbackHandler callbackHandler) {
        webService.getProductByStringQuery(stringSearch).enqueue(new Callback<com.example.melidemoapp.model.Response>() {
            @Override
            public void onResponse(Call<com.example.melidemoapp.model.Response> call, Response<com.example.melidemoapp.model.Response> response) {
                callbackHandler.onComplete(response);
            }

            @Override
            public void onFailure(Call<com.example.melidemoapp.model.Response> call, Throwable t) {
                callbackHandler.onError(t);
            }
        });


    }

}
