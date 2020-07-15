package com.example.melidemoapp.ui.main;

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

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private MainViewModel mViewModel;
    private MainFragment context;
    private ListView lv_products;
    private ProgressBar pb;
    private ProductListAdapter productListAdapter;
    private EditText et_search;
    private View viewInflate;
    private Observer<ArrayList<Product>> observer;
    private ArrayList<Product> products = new ArrayList<>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.main_fragment, container, false);
        context = this;
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        observer = new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> productsResult) {
              onProductsListChange(productsResult);
            }
        };
        mViewModel.getProductListResult().observe(getViewLifecycleOwner(), observer);
        return viewInflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findFragmentViews();

        lv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.productDetail);
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
        lv_products.setEmptyView(viewInflate.findViewById(R.id.empty));
    }

    private void findFragmentViews() {
        et_search = viewInflate.findViewById(R.id.et_search);
        lv_products = viewInflate.findViewById(R.id.lv_products);
        pb = viewInflate.findViewById(R.id.pb);
    }


    private void onProductsListChange(ArrayList<Product> productsResult) {
        pb.setVisibility(View.GONE);
        products = productsResult;
        productListAdapter.updateProductsList(products);
        productListAdapter.notifyDataSetChanged();
    }

}
