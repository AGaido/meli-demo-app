package com.example.melidemoapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.melidemoapp.R;
import com.example.melidemoapp.model.Product;
import com.squareup.picasso.Picasso;

public class ProductDetail extends Fragment {

    private ProductDetailViewModel mViewModel;
    private Product product;
    private View viewInflate;
    private TextView tv_price;
    private TextView tv_name;
    private ImageView iv_product;

    public static ProductDetail newInstance() {
        return new ProductDetail();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.product_detail_fragment, container, false);
        return viewInflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);

        Bundle bundle = getArguments();
        if (bundle.getSerializable("product") != null)
            product = (Product) bundle.getSerializable("product");

        tv_name = viewInflate.findViewById(R.id.tv_name);
        tv_price = viewInflate.findViewById(R.id.tv_price);
        iv_product = viewInflate.findViewById(R.id.iv_product);

        tv_name.setText(product.getTitle());
        tv_price.setText("$ " + product.getPrice());
        Picasso.with(getActivity())
                .load(product.getThumbnail().replace("-I", "-F"))
                .error(getActivity().getResources().getDrawable(R.drawable.ic_error_outline_black_24dp))
                .into(iv_product);
    }

}
