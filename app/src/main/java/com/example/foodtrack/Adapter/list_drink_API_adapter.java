package com.example.foodtrack.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class list_drink_API_adapter extends ArrayAdapter<SanPhamAPIModel> {

    public list_drink_API_adapter(Context context, ArrayList<SanPhamAPIModel> arrayListDrink) {
        super(context, R.layout.fragment_food_drink_item, arrayListDrink);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        SanPhamAPIModel drink = getItem(position); // Lấy đối tượng Drink ở vị trí hiện tại

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_food_drink_item, parent, false);
        }

        TextView title = view.findViewById(R.id.item_title_product);
        TextView price = view.findViewById(R.id.item_price_product);
        ImageView img = view.findViewById(R.id.item_image_product);
        TextView addToCartBtn = view.findViewById(R.id.btn_AddToCart_food_drink);

        if (drink != null) {
            String imageUrl = drink.getImages();
            if (imageUrl.startsWith("http://")) {
                imageUrl = imageUrl.replace("http://", "https://");
            }

            Glide.with(getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.icon_food2)
                    .error(R.drawable.icon_food1)
                    .into(img);
//            img.setImageBitmap(drink.getImages());
            title.setText(drink.getTenSanPham());

            NumberFormat formatter = NumberFormat.getInstance(Locale.ITALY);
            String formattedPrice= formatter.format(drink.getGiaTien());
            formattedPrice = formattedPrice + "vnđ";
            price.setText(formattedPrice);
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_listview_sanpham);
        view.startAnimation(animation);
        return view;
    }
}
