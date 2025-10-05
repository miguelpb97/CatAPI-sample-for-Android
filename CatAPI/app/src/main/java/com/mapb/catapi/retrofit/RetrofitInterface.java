package com.mapb.catapi.retrofit;

import com.mapb.catapi.models.FavoriteCatGetModel;
import com.mapb.catapi.models.FavoriteCatPostModel;
import com.mapb.catapi.models.CatGetModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("v1/images/search?limit=10")
    Call<ArrayList<CatGetModel>> getRandomCats(@Header("x-api-key") String apiKey);

    @POST("v1/favourites")
    Call<FavoriteCatPostModel> addFavoriteCat(@Header("x-api-key") String apiKey, @Body FavoriteCatPostModel cat);

    @GET("v1/favourites?order=DESC")
    Call<ArrayList<FavoriteCatGetModel>> getFavoriteCats(@Header("x-api-key") String apiKey);

    @DELETE("v1/favourites/{favouriteId}")
    Call<Void> deleteFavoriteCat(@Header("x-api-key") String apiKey, @Path("favouriteId") float favouriteId);

}

