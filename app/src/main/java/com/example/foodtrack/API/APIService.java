package com.example.foodtrack.API;

import android.content.SharedPreferences;

import com.example.foodtrack.Model.API.NguoiDungAPIModel;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.BinhLuanSanPhamModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.Model.SanPhamYeuThichModel;
import com.example.foodtrack.Model.TestChat.TinNhanModel;
import com.example.foodtrack.Model.ThongBaoModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
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

  OkHttpClient okHttpClient = new OkHttpClient.Builder()
          .connectTimeout(30, TimeUnit.SECONDS)
          .readTimeout(30, TimeUnit.SECONDS)
          .writeTimeout(30, TimeUnit.SECONDS)
          .build();

  //    linkAPI root:
    public static String url ="https://foodtrack-wpjz.onrender.com/";

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:sss").create();
    APIService API_SERVICE = new Retrofit.Builder().baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    // hàm này dùng để login , gửi email và pass word: rồi
    @POST("user/login")
    Call<NguoiDungAPIModel> GetUserToLogin(@Body NguoiDungAPIModel userModel);

    // hàm này dùng để singin đăng kí tài khoản , api trả về ....: rồi
    @POST("user/register")
    Call<NguoiDungModel> PostUserToSingin(@Body NguoiDungModel userModel);



    // Home_Page
    // dùng để list sản phẩm deal hời: rồi
    @GET("food/bargain")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_DealHoi();
    // dùng để list sản phẩm banchay: rồi
    @GET("/food/bestseller")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_BanChay();
  // dùng để list sản phẩm monmoi: rồi
    @GET("food/new")
    Call<List<SanPhamAPIModel>> getListSanphamHomePage_MonMoi();

    // Explore
  // dùng để lấy các món ăn: rồi
    @GET("food/list")
    Call<List<SanPhamAPIModel>> getListMonAn_Explore();
    // lấy các thức uống; rồi
    @GET("food/list/drink")
    Call<List<SanPhamAPIModel>>getListDoUong_Explore();


    // quên mật khẩu: rồi
    @POST("/user/password/forgot")
    Call<NguoiDungModel>PostEmailToLogin(@Body NguoiDungModel userModel);

    // lấy otp: rồi
    @POST("/user/password/otp")
    Call<NguoiDungModel>PostEmailandOPTLogin(@Body NguoiDungModel userModel);

    // reset password : roi
    @POST("/user/password/reset")
    Call<NguoiDungModel>PostToResetPass(@Body NguoiDungModel userModel);


    // cập nhật số lượng sản phẩm, trong giỏ hàng: rồi
  @POST ("order/update/quantity")
  Call<ChiTietDonHangAPIModel> UpdateSoLuongSanPhamGioHang (@Body  ChiTietDonHangAPIModel donHangAPIModel);



    //lấy tất cả sản phẩm trong dơn hàng hiện tại :ròi
  @GET("order/detail")
  Call<List<SanPhamAPIModel>>GetSanPhamGioHang(@Query ("idDonHang") String idDonHang);

  // lấy đơn hàng đang giao: roi
    @GET("order/list/unfinished")
    Call<List<DonHangAPIModel>> GetDonHangDangGiao(@Query("idNguoiDung")String idNguoiDung);

  //lấy đơn hàng đã hủy : roi
  @GET("order/list/deny")
  Call<List<DonHangAPIModel>> GetDonHangDaHuy(@Query("idNguoiDung")String idNguoiDung);

  // hủy đặt hàng set trangThaiDat==0;: roi
  @POST("order/deny")
  Call<DonHangAPIModel> PostToCancleOrder(@Body DonHangAPIModel donHang);


    //Lấy tất cả sản phẩm đã mua - history: ròi
    @GET("food/list/order")
    Call<List<DonHangAPIModel>> GetSanPhamDaMua(@Query("idNguoiDung") String idUser);

    // mưa lại đơn hàng : dang lam
    @POST("/order/reorder")
    Call<ChiTietDonHangAPIModel> MuaLaiDonHang(@Body ChiTietDonHangAPIModel model);




    // lấy danh sách sản phẩm yêu thích: roi
    @GET("food/love/list")
    Call<List<SanPhamAPIModel>> getDsSanPhamYeuThich(@Query("idNguoiDung") String idNguoiDung);

    @POST("order/new")
    // thêm sản phẩm vào đơn hàng :roi
    Call<ChiTietDonHangAPIModel> PostToBuyProduct(@Body ChiTietDonHangAPIModel product);

    //  đặt hàng:set trangThaiDat==1 dang
    @POST("order/confirm")
    Call<DonHangAPIModel> PostToOrder(@Body DonHangAPIModel donHang);




  //lấy danh sách chat: roi
  @GET("chat")
  Call<List<TinNhanModel>> getDsChat(@Query("idPhongChat")String idPhongChat);


    // thêm sản phẩm yêu thích : rồi
   @POST("food/love")
    Call<SanPhamYeuThichModel> ThemSanPhamYeuThichModel(@Body SanPhamYeuThichModel model);
  // bỏ sản phẩm yêu thích : rồi
  @POST("food/unlove")
  Call<SanPhamYeuThichModel> BoSanPhamYeuThichModel(@Body SanPhamYeuThichModel model);

  @GET("food/detail")
  Call<SanPhamYeuThichModel> GetTrangThaiYeuThich(@Query("idNguoiDung")String idNguoiDung, @Query("idSanPham") String idSanPham);



  Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://example.com/")
          .client(okHttpClient)
          .addConverterFactory(GsonConverterFactory.create())
          .build();


//  // lấy chi tiết đơn hàng đang giao: roi
//  @GET("")
//  Call<List<ChiTietDonHangAPIModel>> GetChiTietDonHangDangGiao();
//
//  // lấy chi tiết đơn hàng đã hủy : ròi
//  @GET("")
//  Call<List<ChiTietDonHangAPIModel>> GetChiTietDonHangDaHuy();



   // xóa sản phẩm trong dơn hàng: rôồi
  @POST("order/cancel")
  Call<ChiTietDonHangAPIModel> XoaSanPhamGioHang(@Body ChiTietDonHangAPIModel model);



  // gửi bình luận: chua
  @POST("user/comment")
  Call<BinhLuanSanPhamModel> guiBinhLuan (@Body BinhLuanSanPhamModel binhLuanSanPhamModel);

  // lấy comment sản phẩm : chuưa
  @GET("food/list/comment")
  Call<List<BinhLuanSanPhamModel>>LayCommentSanPham(@Query("idSanPham") String idSanPham);
// đăng comment : sản phẩm : chưa



  // thay đổi ảnh : roi
  @POST("user/update/avatar")
  @Multipart
  Call<NguoiDungAPIModel> ChangInfoUser(@Part("idNguoiDung") RequestBody idUser, @Part MultipartBody.Part image);

// cập nhật thông tin user: rồi
  @POST("user/update/info")
  Call<NguoiDungAPIModel> UpdateInfo(@Body NguoiDungAPIModel model);

  // Lấy thông tin user : roi
  @GET("user/info")
  Call<NguoiDungAPIModel> GetInfoUser(@Query("idNguoiDung")String idUser);

  // Tìm kiếm sản phẩm: rồi
  @GET("food/search")
  Call<List<SanPhamAPIModel>> GetSearchResult(@Query("query")String query);

  // lấy danh sách thông báo : chưa
  @GET("user/list/notification")
  Call<List<ThongBaoModel>> GetListNoti(@Query("idNguoiDung") String idNguoiDung);

  // gửi thông báo : chưa
  @POST("")
  Call<ThongBaoModel> SendNoti(@Body ThongBaoModel model);

}


