package com.example.foodtrack.API;

import android.content.SharedPreferences;

import com.example.foodtrack.Model.API.NguoiDungAPIModel;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.BinhLuanSanPhamModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.Model.SanPhamYeuThichModel;
import com.example.foodtrack.Model.TestChat.TinNhanModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {



  //    linkAPI root:
    public static String url ="https://0856-42-116-118-17.ngrok-free.app/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:sss").create();
    APIService API_SERVICE = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    // hàm này dùng để login , gửi email và pass word
    @POST("user/login")
    Call<NguoiDungAPIModel> GetUserToLogin(@Body NguoiDungAPIModel userModel);

    // hàm này dùng để singin đăng kí tài khoản , api trả về ....
    @POST("user/register")
    Call<NguoiDungModel> PostUserToSingin(@Body NguoiDungModel userModel);


    // Home_Page
    // dùng để list sản phẩm deal hời
    @GET("food/bargain")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_DealHoi();
    // dùng để list sản phẩm banchay
    @GET("/food/bestseller")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_BanChay();
  // dùng để list sản phẩm monmoi
    @GET("food/new")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_MonMoi();

    // Explore
  // dùng để lấy các món ăn
    @GET("food/list")
    Call<List<SanPhamAPIModel>> getListMonAn_Explore();
    // dùng đẻ lấy các thức uống
    @GET("sanpham/douong")
    Call<List<SanPhamAPIModel>>getListDoUong_Explore();

    @POST("/user/password/forgot")
    Call<NguoiDungModel>PostEmailToLogin(@Body NguoiDungModel userModel);

    @POST("/user/password/otp")
    Call<NguoiDungModel>PostEmailandOPTLogin(@Body NguoiDungModel userModel);

    @POST("/user/password/reset")
    Call<NguoiDungModel>PostToResetPass(@Body NguoiDungModel userModel);









    //lấy tất cả sản phẩm trong dơn hàng hiện tại :ròi
  @GET("order/detail")
  Call<List<SanPhamAPIModel>>GetSanPhamGioHang(@Query ("idDonHang") String idDonHang);

  // lấy đơn hàng đang giao: chưa
    @GET("")
    Call<List<DonHangAPIModel>> GetDonHangDangGiao();
    //Lấy tất cả sản phẩm đã mua - history: histori chưa
    @GET("")
    Call<List<SanPhamAPIModel>> GetSanPhamDaMua(@Query("idNguoiDung") String idUser);

    //lấy đơn hàng đã hủy: chưa
    @GET("")
    Call<List<DonHangAPIModel>> GetDonHangDaHuy();

    // lấy danh sách sản phẩm yêu thích: roi
    @GET("food/love/list")
    Call<List<SanPhamAPIModel>> getDsSanPhamYeuThich(@Query("idNguoiDung") String idNguoiDung);

    @POST("order/new")
    // thêm sản phẩm vào đơn hàng :roi
    Call<ChiTietDonHangAPIModel> PostToBuyProduct(@Body ChiTietDonHangAPIModel product);

    //  đặt hàng:set trangThaiDat==1 roi
    @POST("")
    Call<DonHangAPIModel> PostToOrder(@Body DonHangAPIModel donHang);

    // hủy đặt hàng set trangThaiDat==0;
    Call<DonHangAPIModel> PostToCancleOrder(@Body DonHangAPIModel donHang);

    //  đặt hàng:set trangThaiDat==datHang;
    Call<DonHangAPIModel> PostToAgainOrder(@Body DonHangAPIModel donHang);

  //lấy danh sách chat: roi
  @GET("chat")
  Call<List<TinNhanModel>> getDsChat(@Query("idPhongChat")String idPhongChat);

    // gửi bình luận: chua
    @POST("")
    Call<BinhLuanSanPhamModel> guiBinhLuan (@Body BinhLuanSanPhamModel binhLuanSanPhamModel);


    // thêm sản phẩm yêu thích : rồi
   @POST("food/love")
    Call<SanPhamYeuThichModel> ThemSanPhamYeuThichModel(@Body SanPhamYeuThichModel model);
  // bỏ sản phẩm yêu thích : rồi
  @POST("food/unlove")
  Call<SanPhamYeuThichModel> BoSanPhamYeuThichModel(@Body SanPhamYeuThichModel model);

  @GET("food/detail")
  Call<SanPhamYeuThichModel> GetTrangThaiYeuThich(@Query("idNguoiDung")String idNguoiDung, @Query("idSanPham") String idSanPham);


// chưa 
  @GET("")
  Call<List<ChiTietDonHangAPIModel>> GetChiTietDonHangDangGiao();





   // xóa sản phẩm trong dơn hàng: rôồi
  @POST("order/cancel")
  Call<ChiTietDonHangAPIModel> XoaSanPhamGioHang(@Body ChiTietDonHangAPIModel model);


  // lấy comment sản phẩm : chuưa
  @GET("")
  Call<BinhLuanSanPhamModel>LayCommentSanPham(@Query("idNguoiDung")String idNguoiDung);



  // thay đổi ảnh : chưa
  @POST("")
  @Multipart
  Call<NguoiDungAPIModel> ChangInfoUser(@Part("hoTen") RequestBody hoTen, @Part("sdt") RequestBody sdt, @Part("email") RequestBody email, @Part("gioiTinh") RequestBody gioiTinh, @Part("ngaySinh") RequestBody ngaySinh, @Part("diaChi") RequestBody diaChi, @Part MultipartBody.Part image);




}


