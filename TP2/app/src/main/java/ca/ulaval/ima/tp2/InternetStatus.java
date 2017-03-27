package ca.ulaval.ima.tp2;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;


public class InternetStatus extends Fragment {
    public View internetStatusView;
    Button internet_status_button;
    protected GradientDrawable bgShape;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        internetStatusView = inflater.inflate(R.layout.internet_status_layout, container, false);
        internet_status_button = (Button) internetStatusView.findViewById(R.id.internet_status_button);
        final TextView internet_status_text_view = (TextView) internetStatusView.findViewById(R.id.internet_status);
        ImageView iv = (ImageView) internetStatusView.findViewById(R.id.internet_status_shape);
        bgShape = (GradientDrawable)iv.getDrawable();
        bgShape.setColor(Color.RED);


        internet_status_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();




                if (networkInfo != null && networkInfo.isConnected()) {
                    if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                        internet_status_text_view.setText("3G/LTE");

                    }
                    if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                        internet_status_text_view.setText("WIFI");
                    }
                    bgShape.setColor(Color.GREEN);

                } else {
                    internet_status_text_view.setText("Aucune connexion");
                    bgShape.setColor(Color.RED);

                }
            }
        });
        return internetStatusView;

    }


}
