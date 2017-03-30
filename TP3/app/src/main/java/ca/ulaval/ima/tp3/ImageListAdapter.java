package ca.ulaval.ima.tp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageListAdapter extends ArrayAdapter {
    private Context context;


    private LayoutInflater inflater;

    private ArrayList<HashMap<String, String>> imageUrls;

    public ImageListAdapter(Context context, ArrayList<HashMap<String, String>> imageUrls) {
        super(context, R.layout.offer_list, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.offer_list, parent, false);
        }
        System.out.println(imageUrls.get(position).toString());

        Picasso
                .with(context)
                .load(imageUrls.get(position).get("photo_url"))
                .fit() // will explain later
                .into((ImageView) convertView.findViewById(R.id.image));

        TextView model = (TextView)convertView.findViewById(R.id.model);
        model.setText(imageUrls.get(position).get("model"));
        TextView price = (TextView)convertView.findViewById(R.id.price);
        price.setText(imageUrls.get(position).get("price")+"$");
        TextView brand = (TextView)convertView.findViewById(R.id.brand);
        brand.setText(imageUrls.get(position).get("brand"));
        TextView year = (TextView)convertView.findViewById(R.id.year);
        year.setText(imageUrls.get(position).get("year"));
        return convertView;
    }
}