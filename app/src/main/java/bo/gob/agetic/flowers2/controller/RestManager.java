package bo.gob.agetic.flowers2.controller;

import bo.gob.agetic.flowers2.model.callback.FlowerService;
import bo.gob.agetic.flowers2.model.helper.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ramiro on 7/17/16.
 */

public class RestManager {
    private FlowerService mFlowerService;

    public FlowerService getFlowerService() {
        if (mFlowerService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mFlowerService = retrofit.create(FlowerService.class);
        }
        return mFlowerService;
    }
}
