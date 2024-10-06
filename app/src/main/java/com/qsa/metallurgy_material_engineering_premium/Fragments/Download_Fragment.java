package com.qsa.metallurgy_material_engineering_premium.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsa.metallurgy_material_engineering_premium.Adapters.DownloadsAdapter;
import com.qsa.metallurgy_material_engineering_premium.Booksdb;
import com.qsa.metallurgy_material_engineering_premium.Interfaces.FragmentCallback;
import com.qsa.metallurgy_material_engineering_premium.MainActivity;
import com.qsa.metallurgy_material_engineering_premium.R;

import java.util.ArrayList;
import java.util.List;

public class Download_Fragment extends Fragment implements FragmentCallback {

    RecyclerView recyclerView;
    ArrayList<Booksdb> dataList;
    DownloadsAdapter downloadAdapter;
    ProgressDialog progressDialog;
    public List<Booksdb> booksdbs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_download_, container, false);

        recyclerView = view.findViewById(R.id.recycleViewDownloads);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //Favourite_Fragment fragment = new Favourite_Fragment();
        booksdbs = MainActivity.myappdatabas.myDao().getBooks();
        loadData();
        downloadAdapter = new DownloadsAdapter(getContext(), dataList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(downloadAdapter);


    }

    @Override
    public void doSomething() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Bookmark_Fragment()).commit();
    }

    private void loadData() {
/*
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("saveData", getContext().MODE_PRIVATE);
        int dataListSize = sharedPreferences.getInt("dataListSize", 0);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Books>>() {
        }.getType();



*/

        String info = "";
        dataList = new ArrayList<>();
        dataList.clear();

        for (Booksdb booksd : booksdbs)
        {
            dataList.add(booksd);
            String id = booksd.getId();
            String bookName = booksd.getBookName();
            String booksdesc = booksd.getDescription();
            info = info+"\n\n"+"id: "+id+"\n bookname: "+bookName+"\n bookcategory: "+booksdesc;
        }

        Log.e("TAGDATA", info);


        /*for (int i = 0; i < dataListSize; i++) {
            try {
                Log.i("Order is ", "" + dataListSize); // Log the order
                String json = sharedPreferences.getString("downloadBookData_" + dataList.get(i).getId(), null);
                dataList = gson.fromJson(json, type);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }*/



    }
}