package com.example.melidemoapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melidemoapp.R;
import com.example.melidemoapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alejandro.gaidolfi on 13/07/20.
 */
public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> products;
    private LayoutInflater layoutInflater;

    public ProductListAdapter(ArrayList<Product> products, Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.product_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = getItem(position);

        viewHolder.tv_name.setText(product.getTitle());
        viewHolder.tv_price.setText("$ " + product.getPrice());
        Picasso.with(context)
                .load(product.getThumbnail())
                .into(viewHolder.iv_product);

        return convertView;
    }

    public void updateProductsList(ArrayList<Product> products) {
        this.products = products;
    }

    private class ViewHolder {
        private ImageView iv_product;
        private TextView tv_name;
        private TextView tv_price;

        public ViewHolder(View parentView) {
            iv_product = parentView.findViewById(R.id.iv_product);
            tv_name = parentView.findViewById(R.id.tv_name);
            tv_price = parentView.findViewById(R.id.tv_price);
        }
    }
}
