package com.mapb.catapi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapb.catapi.adapters.FavoriteCatAdapter;
import com.mapb.catapi.models.FavoriteCatGetModel;
import com.mapb.catapi.retrofit.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteCatsListActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.thecatapi.com";
    private FavoriteCatAdapter catsFAdapter;
    private ArrayList<FavoriteCatGetModel> cats = new ArrayList<>();
    private String apiKey = "";
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_cats_list);

        RecyclerView rvFavoriteCats = findViewById(R.id.recycler_view_favorite_cats);

        apiKey = ApiKeyManager.readApiKeyFile(getApplicationContext());

        rvFavoriteCats.setHasFixedSize(true);
        rvFavoriteCats.setLayoutManager(new LinearLayoutManager(this));

        catsFAdapter = new FavoriteCatAdapter(cats);

        rvFavoriteCats.setAdapter(catsFAdapter);

        getFavoriteCats();

        catsFAdapter.setOnItemClickListener(new FavoriteCatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, FavoriteCatGetModel cat, int posicion) {
                removeFavoriteCat(cat.getId(), posicion);
            }
        });
    }

    protected void getFavoriteCats() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ArrayList<FavoriteCatGetModel>> call = retrofitInterface.getFavoriteCats(apiKey);
        call.enqueue(new Callback<ArrayList<FavoriteCatGetModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<ArrayList<FavoriteCatGetModel>> call, @NonNull Response<ArrayList<FavoriteCatGetModel>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    for (FavoriteCatGetModel cat : response.body()) {
                        cats.add(cat);
                        catsFAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(FavoriteCatsListActivity.this, "Favorite cats loaded with success.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FavoriteCatsListActivity.this, "Failed to get favorite cats.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FavoriteCatGetModel>> call, Throwable t) {
                Toast.makeText(FavoriteCatsListActivity.this, "Network error while getting favorite cats.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeFavoriteCat(float id, int index) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                        retrofitInterface = retrofit.create(RetrofitInterface.class);// creamos un call y llamamos a la interfaz creada
                        Call<Void> call = retrofitInterface.deleteFavoriteCat(apiKey, id);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    Toast.makeText(FavoriteCatsListActivity.this, "Cat removed from favorites.", Toast.LENGTH_SHORT).show();

                                    // Aquí puedes actualizar tu lista si es necesario
                                    // Por ejemplo: eliminar el elemento de la lista y notificar al adaptador
                                    cats.remove(index);
                                    catsFAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(FavoriteCatsListActivity.this, "Error deleting favorite cat.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                Toast.makeText(FavoriteCatsListActivity.this, "Network error while deleting favorite cat.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, 750);
            }
        });
    }

}
