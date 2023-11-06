package com.example.jeudepart.partie1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jeudepart.partie1.BD.SpinnerItem;
import com.example.jeudepart.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    private Context context;
    private List<SpinnerItem> voyageurList;

    private int resource;

    public SpinnerAdapter(@NonNull Context context, @NonNull List<SpinnerItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(this.resource, parent, false);
        }


        SpinnerItem currentSpinner = getItem(position);


        ImageView image1TextView = (ImageView) convertView.findViewById(R.id.spinner_image);
        TextView label2TextView = (TextView) convertView.findViewById(R.id.spinner_text);


        image1TextView.setImageResource(currentSpinner.getImageResId());
        label2TextView.setText(currentSpinner.getText());

        return convertView;
    }
}
