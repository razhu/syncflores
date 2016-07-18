package bo.gob.agetic.flowers2.model.helper;

/**
 * Created by ramiro on 18-07-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bo.gob.agetic.flowers2.model.Flower;

/**
 * Created by ramiro on 7/18/16.
 */

public class FlowerDatabase extends SQLiteOpenHelper {
    public FlowerDatabase(Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
        } catch (SQLException ex) {
            Log.d("DESDE LA BD", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Constants.DATABASE.DROP_QUERY);
        this.onCreate(db);
    }

    public void addFlower(Flower flower) {

        Log.d("DESDE LA BD", "Values Got " + flower.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.DATABASE.PRODUCT_ID, flower.getProductId());
        values.put(Constants.DATABASE.CATEGORY, flower.getCategory());
        values.put(Constants.DATABASE.PRICE, Double.toString(flower.getPrice()));
        values.put(Constants.DATABASE.INSTRUCTIONS, flower.getInstructions());
        values.put(Constants.DATABASE.NAME, flower.getName());

        try {
            db.insert(Constants.DATABASE.TABLE_NAME, null, values);
        } catch (Exception e) {

        }
        db.close();
    }
    public List<Flower> getFlowers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Constants.DATABASE.GET_FLOWERS_QUERY, null);

        List<Flower> flowerList = new ArrayList<>();

        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do{
                    Flower flower = new Flower();
                    //flower.setFromDatabase(true);
                    flower.setName(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NAME)));
                    flower.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PRICE))));
                    flower.setInstructions(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.INSTRUCTIONS)));
                    flower.setCategory(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.CATEGORY)));
                    flower.setProductId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PRODUCT_ID))));


                    flowerList.add(flower);
                    //publishFlower(flower);

                }while (cursor.moveToNext());
            }
        }
        return flowerList;
    }

}
