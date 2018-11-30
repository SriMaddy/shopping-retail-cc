package com.shoppingapp.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoppingapp.BR;
import com.shoppingapp.R;
import com.shoppingapp.model.entity.Cart;
import com.shoppingapp.model.entity.Product;
import com.shoppingapp.view.CartActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {

    private Activity mActivity;
    private List<Product> products;
    private List<Cart> carts;

    public CartRecyclerAdapter(Activity activity, List<Product> products, List<Cart> carts) {
        this.products = products;
        this.carts = carts;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.cart_list_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Product product = products.get(position);
        Cart cart = getCart(product);
        viewHolder.bind(product, cart);
    }

    private Cart getCart(Product product) {
        Cart cartFound = null;
        for(Cart cart : carts) {
            if(cart.getProductId() == product.getId()) {
                cartFound = cart;
                break;
            }
        }
        return cartFound;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void removeProductFromCart(Product product) {
        int position = products.indexOf(product);
        Log.i("ProductRemoved", products.remove(product) + "");
        notifyItemRemoved(position);

        CartActivity cartActivity = ((CartActivity) mActivity);
        cartActivity.removeProductFromCart(product);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;
        private Product product;

        @BindView(R.id.count_txt_view)
        TextView quantityTxtView;

//        @BindView(R.id.view_foreground)
//        public RelativeLayout viewForeground;
//
//        @BindView(R.id.view_background)
//        public RelativeLayout viewBackground;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(Product product, Cart cart) {
            this.binding.setVariable(BR.product, product);
            this.product = product;
            quantityTxtView.setText(cart.getNumberOfProduct() + "");
        }
    }
}
