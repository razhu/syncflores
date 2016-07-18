package bo.gob.agetic.flowers2.model.callback;

import java.util.List;

import bo.gob.agetic.flowers2.model.Flower;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ramiro on 7/17/16.
 */

public interface FlowerService {
    @GET("/feeds/flowers.json")
    Call<List<Flower>> getAllFlowers();
}

