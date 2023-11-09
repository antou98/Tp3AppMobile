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
    private List<SpinnerItem> itemList;

    public SpinnerAdapter(Context context, List<SpinnerItem> itemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.image_text, parent, false);
        }

        SpinnerItem currentSpinner = getItem(position);

        ImageView imagePays = convertView.findViewById(R.id.imagePays);
        TextView textPays = convertView.findViewById(R.id.textPays);

        //set le spinner  seulement si pas null éviter les erreurs
        if (currentSpinner != null) {
            imagePays.setImageResource(currentSpinner.getImageResId());
            textPays.setText(currentSpinner.getText());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
