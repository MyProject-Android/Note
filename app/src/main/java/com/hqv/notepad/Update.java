package com.hqv.notepad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Update extends Fragment {

    TextView txtTime;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update,container,false);
        txtTime= view.findViewById(R.id.textViewTime);
        return view;
    }
    public void GanNoiDung(String time){
        txtTime.setText(time);
    }
}