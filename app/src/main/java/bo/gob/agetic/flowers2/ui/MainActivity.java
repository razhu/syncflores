package bo.gob.agetic.flowers2.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import bo.gob.agetic.flowers2.R;
import bo.gob.agetic.flowers2.controller.RestManager;
import bo.gob.agetic.flowers2.model.Flower;
import bo.gob.agetic.flowers2.model.adapter.FlowerAdapter;
import bo.gob.agetic.flowers2.model.helper.Constants;
import bo.gob.agetic.flowers2.model.helper.FlowerDatabase;
import bo.gob.agetic.flowers2.model.helper.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FlowerAdapter.FlowerClickListener{
    private RecyclerView mRecyclerView;
    private RestManager mManager;
    private FlowerAdapter mFlowerAdapter;
    private FlowerDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configViews();

        mManager = new RestManager();
        mDatabase = new FlowerDatabase(this);

        if(Utils.isNetworkAvailable(getApplicationContext())){
            getFeed();
        }else{
            getFeedFromDatabase();
        }
    }

    private void getFeedFromDatabase() {
        mDatabase = new FlowerDatabase(this);
        List<Flower> flowerList = mDatabase.getFlowers();

    }

    private void getFeed() {
        Call<List<Flower>> listCall = mManager.getFlowerService().getAllFlowers();
        listCall.enqueue(new Callback<List<Flower>>() {
            @Override
            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                if(response.isSuccessful()){
                    List<Flower> flowerList = response.body();

                    for(int i=1; i< flowerList.size(); i++){
                        Flower flower = flowerList.get(i);

                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(flower);

                        mFlowerAdapter.addFlower(flower);
                    }
                }else{
                    int sc = response.code();
                    switch (sc){

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Flower>> call, Throwable t) {

            }
        });
    }

    private void configViews() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mFlowerAdapter = new FlowerAdapter(this);
        mRecyclerView.setAdapter(mFlowerAdapter);
    }

    @Override
    public void onClick(int position) {
        Flower selectedFlower = mFlowerAdapter.getSelectedFlower(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.FLOWER, selectedFlower);
        startActivity(intent);
    }
    public class SaveIntoDatabase extends AsyncTask<Flower, Void, Void> {


        private final String TAG = SaveIntoDatabase.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Flower... params) {

            Flower flower = params[0];

            try {

                mDatabase.addFlower(flower);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }

    }

}
