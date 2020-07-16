package com.example.melidemoapp.ui.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.melidemoapp.model.Product;
import com.example.melidemoapp.model.Response;
import com.example.melidemoapp.repository.ProductRepository;
import com.example.melidemoapp.service.CallbackHandler;

import java.util.ArrayList;

public class SearchProductViewModel extends ViewModel {

    private ProductRepository productRepository;
    private MutableLiveData<ArrayList<Product>> productsList;
    private MutableLiveData<Throwable> error;

    public SearchProductViewModel() {
        this.productRepository = new ProductRepository();
    }

    public MutableLiveData<Throwable> getErrorResponse() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public MutableLiveData<ArrayList<Product>> getProductListResult() {
        if (productsList == null) {
            productsList = new MutableLiveData<>();
        }
        return productsList;
    }

    public void getProductListResult(String queryFilter) {
        CallbackHandler callbackHandler = new CallbackHandler() {
            @Override
            public void onComplete(retrofit2.Response<Response> response) {
                ArrayList<Product> products = new ArrayList<>();
                if (response.body() != null)
                    products = response.body().getResults();
                if (products == null)
                    products = new ArrayList<>();
                productsList.postValue(products);
                error.postValue(null);
            }

            @Override
            public void onError(Throwable t) {
                error.postValue(t);
            }
        };
        productRepository.getProductsByStringSearch(queryFilter, callbackHandler);
    }
}
