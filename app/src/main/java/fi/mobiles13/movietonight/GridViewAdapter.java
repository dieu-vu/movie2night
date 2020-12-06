package fi.mobiles13.movietonight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<String> titles;
    private final ArrayList<String> years;
    private final ArrayList<String> ratings;
    private final ArrayList<String> urls;

    View view;
    LayoutInflater layoutInflater;

    public GridViewAdapter(Context context, ArrayList<String> titles, ArrayList<String> years, ArrayList<String> ratings, ArrayList<String> urls) {
        this.context = context;
        this.titles = titles;
        this.years = years;
        this.ratings = ratings;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView ==null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item, null);


            TextView titleview = (TextView) view.findViewById(R.id.titleview);
            titleview.setText(titles.get(position));
            TextView yearview = (TextView) view.findViewById(R.id.yearview);
            yearview.setText(years.get(position));
            TextView ratingview = (TextView) view.findViewById(R.id.ratingview);
            ratingview.setText(ratings.get(position));
            TextView linkview = (TextView) view.findViewById(R.id.linkview);
            linkview.setText(urls.get(position));
        }
        return view;
    }
}
