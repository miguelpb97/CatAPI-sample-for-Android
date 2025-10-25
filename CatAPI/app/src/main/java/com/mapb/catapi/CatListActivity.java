package com.mapb.catapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapb.catapi.adapters.CatAdapter;
import com.mapb.catapi.models.FavoriteCatPostModel;
import com.mapb.catapi.models.CatGetModel;
import com.mapb.catapi.retrofit.RetrofitInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatListActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.thecatapi.com";
    private CatAdapter catsAdapter;
    private ArrayList<CatGetModel> cats = new ArrayList<>();
    private RetrofitInterface retrofitInterface;
    private String apiKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_list);

        // Definimos la toolbar
        Toolbar toolbar = findViewById(R.id.toolbarCatList);
        setSupportActionBar(toolbar);

        Button buttonFavoriteCats = findViewById(R.id.button_favorite_cat_list);
        Button buttonAddMoreCats = findViewById(R.id.button_add_more_cats);
        RecyclerView rvCatList = findViewById(R.id.recycler_view_cat_list);

        apiKey = ApiKeyManager.readApiKeyFile(getApplicationContext());

        rvCatList.setHasFixedSize(true);
        rvCatList.setLayoutManager(new LinearLayoutManager(this));

        catsAdapter = new CatAdapter(cats);

        rvCatList.setAdapter(catsAdapter);

        addMoreCats();

        buttonAddMoreCats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoreCats();
            }
        });

        buttonFavoriteCats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatListActivity.this, FavoriteCatsListActivity.class);
                startActivity(intent);
            }
        });

        catsAdapter.setOnItemClickListener(new CatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, CatGetModel cat, int posicion) {
                addFavouriteCat(new FavoriteCatPostModel(cat.getId(), "user-123"), posicion);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Le pasamos la layout de la carpeta menu
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_setting) {
            Intent intent = new Intent(CatListActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void addMoreCats() {
        runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                cats.clear();
                catsAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCats();
                        Toast.makeText(CatListActivity.this, "Loading random cats...", Toast.LENGTH_SHORT).show();
                    }
                }, 750);
            }
        });
    }

    private void getCats() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ArrayList<CatGetModel>> call = retrofitInterface.getRandomCats(apiKey);

        call.enqueue(new Callback<ArrayList<CatGetModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<ArrayList<CatGetModel>> call, @NonNull Response<ArrayList<CatGetModel>> response) {
                if (response.isSuccessful() && response.code() == 200) {

                    if (response.body() != null) {
                        cats.add(0, new CatGetModel(response.body().get(0).getId(), response.body().get(0).getUrl()));
                        cats.add(1, new CatGetModel(response.body().get(1).getId(), response.body().get(1).getUrl()));
                        cats.add(2, new CatGetModel(response.body().get(2).getId(), response.body().get(2).getUrl()));
                        cats.add(3, new CatGetModel(response.body().get(3).getId(), response.body().get(3).getUrl()));
                        cats.add(4, new CatGetModel(response.body().get(4).getId(), response.body().get(4).getUrl()));
                        cats.add(5, new CatGetModel(response.body().get(5).getId(), response.body().get(5).getUrl()));
                        cats.add(6, new CatGetModel(response.body().get(6).getId(), response.body().get(6).getUrl()));
                        cats.add(7, new CatGetModel(response.body().get(7).getId(), response.body().get(7).getUrl()));
                        cats.add(8, new CatGetModel(response.body().get(8).getId(), response.body().get(8).getUrl()));
                        cats.add(9, new CatGetModel(response.body().get(9).getId(), response.body().get(9).getUrl()));

                        catsAdapter.notifyDataSetChanged();

                        Toast.makeText(CatListActivity.this, "Random cats obtained correctly.", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(CatListActivity.this, "Error loading cat list.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CatGetModel>> call, @NonNull Throwable t) {

                Toast.makeText(CatListActivity.this, "Network error while getting a random cat. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addFavouriteCat(FavoriteCatPostModel catFModel, int index) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CatListActivity.this, "Adding cat to favorites.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                        retrofitInterface = retrofit.create(RetrofitInterface.class);// creamos un call y llamamos a la interfaz creada
                        Call<FavoriteCatPostModel> call = retrofitInterface.addFavoriteCat(apiKey, catFModel);
                        call.enqueue(new Callback<FavoriteCatPostModel>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(@NonNull Call<FavoriteCatPostModel> call, @NonNull Response<FavoriteCatPostModel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(CatListActivity.this, "Cat added to favorites.", Toast.LENGTH_SHORT).show();
                                    cats.remove(index);
                                    catsAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(CatListActivity.this, "Error adding cat to favorites.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<FavoriteCatPostModel> call, @NonNull Throwable t) {
                                Toast.makeText(CatListActivity.this, "Network error while adding cat to favorites.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, 750);
            }
        });
    }
}