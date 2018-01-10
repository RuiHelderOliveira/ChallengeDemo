package me.demo.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import me.demo.R;
import me.demo.adapters.DataAdapter;
import me.demo.classes.Data;
import me.demo.classes.User;
import me.demo.interfaces.OnItemClickListener;
import me.demo.services.ApiServices;
import me.demo.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rui Oliveira on 10/01/18.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        Call<Data> call = ServiceGenerator.createService(ApiServices.class).getUsers(0, 20, "abc");
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                mAdapter = new DataAdapter(MainActivity.this, response.body().getResults(), new OnItemClickListener() {
                    @Override
                    public void onItemClick(User item) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("id", item);
                        MainActivity.this.startActivity(intent);
                    }
                });


                mRecyclerView.setHasFixedSize(true);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    mRecyclerView.setLayoutManager(layoutManager);
                }

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                }

                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();

            }
        });
    }
}
