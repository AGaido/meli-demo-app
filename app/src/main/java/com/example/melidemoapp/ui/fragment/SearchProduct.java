package com.example.melidemoapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.melidemoapp.R;
import com.example.melidemoapp.model.Product;
import com.example.melidemoapp.ui.ProductListAdapter;

import java.util.ArrayList;

public class SearchProduct extends Fragment {

    private static final String TAG = SearchProduct.class.getSimpleName();
    private SearchProductViewModel mViewModel;
    private Context context;
    private ListView lv_products;
    private ProgressBar pb;
    private ProductListAdapter productListAdapter;
    private EditText et_search;
    private View viewInflate;
    private Observer<ArrayList<Product>> productsObserver;
    private ArrayList<Product> products = new ArrayList<>();
    private Observer<Throwable> errorObserver;
    private TextView tv_empty_list;

    public static SearchProduct newInstance() {
        return new SearchProduct();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.main_fragment, container, false);
        context = getActivity();
        mViewModel = ViewModelProviders.of(this).get(SearchProductViewModel.class);

        productsObserver = new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> productsResult) {
                onProductsListChange(productsResult);
            }
        };
        errorObserver = new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable error) {
                onErrorReceived(error);
            }
        };
        mViewModel.getProductListResult().observe(getViewLifecycleOwner(), productsObserver);
        mViewModel.getErrorResponse().observe(getViewLifecycleOwner(), errorObserver);
        return viewInflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findFragmentViews();

        lv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", productListAdapter.getItem(position));
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.productDetail, bundle);
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    pb.setVisibility(View.VISIBLE);
                    String queryString = et_search.getText().toString().trim();
                    mViewModel.getProductListResult(queryString);
                }
                return false;
            }
        });

        productListAdapter = new ProductListAdapter(products, getActivity());
        lv_products.setAdapter(productListAdapter);
        lv_products.setEmptyView(tv_empty_list);
    }

    private void findFragmentViews() {
        et_search = viewInflate.findViewById(R.id.et_search);
        lv_products = viewInflate.findViewById(R.id.lv_products);
        pb = viewInflate.findViewById(R.id.pb);
        tv_empty_list = viewInflate.findViewById(R.id.empty);
    }


    private void onProductsListChange(ArrayList<Product> productsResult) {
        pb.setVisibility(View.GONE);
        products = productsResult;
        productListAdapter.updateProductsList(products);
        productListAdapter.notifyDataSetChanged();
    }

    private void onErrorReceived(Throwable error) {
        if (error == null) return;
        pb.setVisibility(View.GONE);
        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
    }

}
