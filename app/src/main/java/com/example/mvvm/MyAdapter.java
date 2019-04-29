package com.example.mvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.List;

public class MyAdapter extends ArrayAdapter {
    public MyAdapter(Context context, List data) {
        super(context, -1, data);
    }
}
