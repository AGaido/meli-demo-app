package com.example.melidemoapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.melidemoapp.model.Product;
import com.example.melidemoapp.service.WebService;

import java.util.ArrayList;

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

    public void getProductsByStringSearch(String stringSearch, final MutableLiveData<ArrayList<Product>> productsList) {
        webService.getProductByStringQuery(stringSearch).enqueue(new Callback<com.example.melidemoapp.model.Response>() {
            @Override
            public void onResponse(Call<com.example.melidemoapp.model.Response> call, Response<com.example.melidemoapp.model.Response> response) {
                ArrayList<Product> products = new ArrayList<>();
                if (response.body() != null)
                    products = response.body().getResults();
                productsList.postValue(products);
            }

            @Override
            public void onFailure(Call<com.example.melidemoapp.model.Response> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                productsList.postValue(new ArrayList<Product>());
            }
        });


    }

}
