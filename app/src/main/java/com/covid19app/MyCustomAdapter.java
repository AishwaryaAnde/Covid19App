package com.covid19app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryModelList;
    private List<CountryModel> countryModelListFilterded;

    public MyCustomAdapter(@NonNull Context context, List<CountryModel> countryModelList) {
        super(context, R.layout.list_custom_item);

        this.context = context;
        this.countryModelList = countryModelList;
        this.countryModelListFilterded = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item, null, true);
        TextView txtCountryName = view.findViewById(R.id.txtCountryName);
        ImageView imgFlag = view.findViewById(R.id.imgFlag);

        txtCountryName.setText(countryModelListFilterded.get(position).getCountry());
        Glide.with(context).load(countryModelListFilterded.get(position).getFlag()).into(imgFlag);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelListFilterded.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelListFilterded.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = countryModelList.size();
                    filterResults.values = countryModelList;
                } else {
                    List<CountryModel> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (CountryModel itemModel : countryModelList) {
                        if (itemModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultModel.add(itemModel);
                        }
                        filterResults.count = resultModel.size();
                        filterResults.values = resultModel;
                    }
                }

                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelListFilterded = (List<CountryModel>) results.values;
                AffectedCountries.countryModelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }

        };

        return filter;
    }
}
