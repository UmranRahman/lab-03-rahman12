package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment{

    private static final String ARG_CITY = "arg_city";
    private City city;

    public static EditCityFragment newInstance(City city) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city); // Pass city object
        fragment.setArguments(args);
        return fragment;
    }

    interface EditCityDialogListener{
        void editCity(City city,String name,String province);
    }
    private EditCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityFragment.EditCityDialogListener) {
            listener = (EditCityFragment.EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);
        EditText editCityName = view.findViewById(R.id.edit_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_province_text);

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(view).setTitle("Edit a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
            String cityName = editCityName.getText().toString();
            String provinceName = editProvinceName.getText().toString();
            listener.editCity(city, cityName, provinceName);
        }).create();
    }
}
