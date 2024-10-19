package com.example.foodtrack.API;

import com.example.foodtrack.Model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

  //    linkAPI root:
    String url ="https://387b-2a09-bac5-d468-e6-00-17-34b.ngrok-free.app/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:sss").create();
    APIService API_SERVICE = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    // hàm này dùng để login , gửi email và pass word , api trả về code và message.
    @POST("user/login")
    Call<UserModel> GetUserToLogin(@Body UserModel userModel);

    // hàm này dùng để singin đăng kí tài khoản , api trả về ....
    @POST("user/register")
    Call<UserModel> PostUserToSingin(@Body UserModel userModel);


}


