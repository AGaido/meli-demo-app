package com.example.melidemoapp.service;

import com.example.melidemoapp.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alejandro.gaidolfi on 13/07/20.
 */
public interface WebService {

    @GET("sites/MLA/search")
    Call<Response> getProductByStringQuery(@Query("q") String queryFilter);

}
