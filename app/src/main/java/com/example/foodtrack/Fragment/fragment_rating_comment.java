package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Model.BinhLuanSanPhamModel;
import com.example.foodtrack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_rating_comment extends Fragment {

    // Biến nhận dữ liệu từ Bundle
    private String idSanPham,idDonHang;

    // Các view trong Fragment
    private ImageView btn_back;
    private TextView btn_GuiCamNhan;
    private EditText textArea_Comment;

    public fragment_rating_comment() {
        // Constructor rỗng bắt buộc
    }

    public static fragment_rating_comment newInstance(String idSanPham, String idDonHang) {
        fragment_rating_comment fragment = new fragment_rating_comment();
        Bundle args = new Bundle();
        args.putString("idSanPham", idSanPham);
        args.putString("idDonHang", idDonHang);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idSanPham = getArguments().getString("idSanPham");
            idDonHang = getArguments().getString("idDonHang");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_comment, container, false);
        initViews(view);   // Khởi tạo các thành phần giao diện
        setListeners();    // Gán sự kiện cho các nút
        return view;
    }

    // Ánh xạ các thành phần giao diện
    private void initViews(View view) {
        btn_back = view.findViewById(R.id.btn_back_rating_comment);
        btn_GuiCamNhan = view.findViewById(R.id.btn_GuiCamNhan);
        textArea_Comment = view.findViewById(R.id.textArea_Comment);
    }

    // Thiết lập sự kiện cho các nút
    private void setListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_GuiCamNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = textArea_Comment.getText().toString().trim();
                if (commentText.isEmpty()) {
                    textArea_Comment.setError("Vui lòng nhập bình luận!");
                    return;
                }

                // Reset ô nhập bình luận
                textArea_Comment.setText("");

                // Tạo đối tượng bình luận
                BinhLuanSanPhamModel binhLuanSanPhamModel = new BinhLuanSanPhamModel();
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
                binhLuanSanPhamModel.setIdNguoiDung(sharedPreferences.getString("idUser", ""));


                binhLuanSanPhamModel.setIdSanPham(idSanPham);
                binhLuanSanPhamModel.setIdDonHang(idDonHang);

                binhLuanSanPhamModel.setNoiDung(commentText);

                // Gửi bình luận qua API
                guiBinhLuan(binhLuanSanPhamModel, view);
            }
        });
    }

    // Tạo popup xác nhận gửi bình luận
    private void showPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_submit_cmt, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView btnOk = popupView.findViewById(R.id.btn_ok_popup_submit_cmt);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_history_API());
                }
            }
        });
    }

    // Gửi bình luận qua API
    private void guiBinhLuan(BinhLuanSanPhamModel binhLuanSanPhamModel, View view) {
        APIService.API_SERVICE.guiBinhLuan(binhLuanSanPhamModel).enqueue(new Callback<BinhLuanSanPhamModel>() {
            @Override
            public void onResponse(Call<BinhLuanSanPhamModel> call, Response<BinhLuanSanPhamModel> response) {
                if (response.isSuccessful()) {
                    showPopup(view);
                } else {
                    // Xử lý khi phản hồi không thành công
                    textArea_Comment.setError("Có lỗi xảy ra. Vui lòng thử lại!");
                }
            }

            @Override
            public void onFailure(Call<BinhLuanSanPhamModel> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                textArea_Comment.setError("Không thể kết nối đến máy chủ!");
            }
        });
    }
}
