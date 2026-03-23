package com.example.applicationb;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlbumAPI {

    @GET("health")
    Call<ResponseBody> health();

    @POST("albums")
    Call<Album> createAlbum(@Body Album body);

    @GET("albums")
    Call<List<Album>> getAlbums();

    @PUT("albums")
    Call<ResponseBody> updateAlbum(@Body Album body);

    @GET("albums/{search}")
    Call<List<Album>> searchAlbums(@Path("search") String search);
}
