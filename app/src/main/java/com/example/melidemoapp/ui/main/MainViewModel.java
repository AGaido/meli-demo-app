package com.example.melidemoapp.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.melidemoapp.model.Product;
import com.example.melidemoapp.repository.ProductRepository;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private ProductRepository productRepository;
    private MutableLiveData<ArrayList<Product>> productsList;

    public MainViewModel() {
        this.productRepository = new ProductRepository();
    }

    public MutableLiveData<ArrayList<Product>> getProductListResult() {
        if (productsList == null){
            productsList = new MutableLiveData<>();
        }
        return productsList;
    }

    public void getProductListResult(String queryFilter) {
        productRepository.getProductsByStringSearch(queryFilter, productsList);
    }
}
