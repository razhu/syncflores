package bo.gob.agetic.flowers2.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bo.gob.agetic.flowers2.R;
import bo.gob.agetic.flowers2.model.Flower;

/**
 * Created by ramiro on 7/17/16.
 */

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.Holder>{
    private final FlowerClickListener mListener;
    private List<Flower> mFlowers = new ArrayList<>();

    public FlowerAdapter(FlowerClickListener listener){
        mFlowers = new ArrayList<>();
        mListener = listener;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Flower currFlower = mFlowers.get(position);

        holder.mName.setText(currFlower.getName());
        holder.mPrice.setText(String.format("$%.2f", currFlower.getPrice()));

    }

    @Override
    public int getItemCount() {
        return mFlowers.size();
    }

    public void addFlower(Flower flower) {
        Log.d("CARAJOOOOOOOOOOO", flower.getName());
        mFlowers.add(flower);
        notifyDataSetChanged();
    }

    public Flower getSelectedFlower(int position) {
        return mFlowers.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mPhoto;
        private TextView mName, mPrice;

        public Holder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.flowerPhoto);
            mName = (TextView) itemView.findViewById(R.id.flowerName);
            mPrice = (TextView) itemView.findViewById(R.id.flowerPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(getLayoutPosition());
        }
    }
    public interface FlowerClickListener {

        void onClick(int position);
    }
}
