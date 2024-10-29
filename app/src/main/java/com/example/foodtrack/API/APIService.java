package com.example.foodtrack.API;

import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

  //    linkAPI root:
    String url ="https://6a35-2a09-bac5-d46f-16dc-00-247-56.ngrok-free.app/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:sss").create();
    APIService API_SERVICE = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    // hàm này dùng để login , gửi email và pass word , api trả về code và message.
    @POST("user/login")
    Call<NguoiDungModel> GetUserToLogin(@Body NguoiDungModel userModel);

    // hàm này dùng để singin đăng kí tài khoản , api trả về ....
    @POST("user/register")
    Call<NguoiDungModel> PostUserToSingin(@Body NguoiDungModel userModel);


    // Home_Page
    // dùng để list sản phẩm deal hời
    @GET("food/bargain")
    Call<List<SanPhamModel>> getListSanphamHomePage_DealHoi();
    // dùng để list sản phẩm banchay
    @GET("/food/bestseller")
    Call<List<SanPhamModel>> getListSanphamHomePage_BanChay();
  // dùng để list sản phẩm monmoi
    @GET("sanpham/monmoi")
    Call<List<SanPhamModel>> getListSanphamHomePage_MonMoi();

    // Explore
  // dùng để lấy các món ăn
    @GET("sanpham/monan")
    Call<List<SanPhamModel>> getListMonAn_Explore();
    // dùng đẻ lấy các thức uống
    @GET("sanpham/douong")
    Call<List<SanPhamModel>>getListDoUong_Explore();

    // Tìm sản phẩm chi tiết
    @GET("sanpham/{id}")
    Call<SanPhamModel>getChiTietSanPham(@Query("idSanPham")String idSanPham);


}


