package grandi.bambino.weatherforecast;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseCityAdapter extends RecyclerView.Adapter<ChooseCityAdapter.ChooseCityHolder> {
    private OnCityClickListener mListener;
    private String[] cityName;

    public ChooseCityAdapter(String[] cityName, OnCityClickListener listener){
        this.cityName = cityName;
        this.mListener = listener;
    }

    public class ChooseCityHolder extends RecyclerView.ViewHolder {
        TextView mCityNameTextView;

        public ChooseCityHolder(@NonNull View itemView) {
            super(itemView);
            mCityNameTextView = itemView.findViewById(R.id.choose_city_items);
            mCityNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(cityName[getAdapterPosition()]);
                }
            });
        }
    }
    //Создаём новый объект ViewHolder, если не обходимо
    @NonNull
    @Override
    public ChooseCityHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_city_item, parent, false);

        return new ChooseCityHolder(view);
    }
    //Заполняем объект
    @Override
    public void onBindViewHolder(@NonNull final ChooseCityHolder holder, int position) {
        holder.mCityNameTextView.setText(cityName[position]);


    }
    //Храним количество данных
    @Override
    public int getItemCount() {
        return cityName.length;
    }
}
