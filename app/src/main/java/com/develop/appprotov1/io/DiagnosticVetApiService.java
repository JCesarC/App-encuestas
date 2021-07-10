package com.develop.appprotov1.io;

import com.develop.appprotov1.Cuestionario;
import com.develop.appprotov1.CustomFilesList;
import com.develop.appprotov1.io.response.Post;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DiagnosticVetApiService {
    @GET("cuestionario")
    Call<ArrayList<Cuestionario>> getCuestionario();


    @Headers("content-type: application/json")
    @POST("cuestionario")
    Call<Cuestionario> savePost(@Body Cuestionario cuestionario
    );

    @Multipart
    @POST("/")
    Call<ResponseBody> uploadImages(
            @Part MultipartBody.Part file
    );

}
