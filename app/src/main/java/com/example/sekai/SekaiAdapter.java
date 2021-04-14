package com.example.sekai;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SekaiAdapter extends RecyclerView.Adapter<SekaiAdapter.CountriesViewHolder> implements Filterable {
    Context context;

    List<Country> countries;
    List<Country> countriesFiltered;

    CountryAdapterListener countryAdapterListener;

    public interface CountryAdapterListener {
        void onCountrySelected(Country country);
    }

    public SekaiAdapter(Context context, List<Country> countries,CountryAdapterListener countryAdapterListener){
        this.context = context;
        this.countries = countries;
        countriesFiltered = countries;
        this.countryAdapterListener = countryAdapterListener;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.listall, parent,false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        holder.setCountry(countriesFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return countriesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    countriesFiltered = countries;
                } else {
                    List<Country> filteredList = new ArrayList<>();
                    for (Country country : countries) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (country.getName().toLowerCase().contains(charString.toLowerCase()) || country.getAlpha2code().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(country);
                        }
                    }

                    countriesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = countriesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countriesFiltered = (ArrayList<Country>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        ImageView ivFlag;

        public CountriesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.Name);
            ivFlag = itemView.findViewById(R.id.Flagpic);
        }

        private void setCountry(Country country){
            tv_name.setText(country.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countryAdapterListener.onCountrySelected(country);
                }
            });

            setImage(country.getFlag());
        }

        private void setImage(String url){
            GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(context).
                    using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                    .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                    .decoder(new SvgDecoder())
                    .listener(new SvgSoftwareLayerSetter<Uri>());

            Uri uri = Uri.parse(url);
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .load(uri)
                    .into(ivFlag);
        }
    }
}
