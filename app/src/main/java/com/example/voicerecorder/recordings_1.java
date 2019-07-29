package com.example.voicerecorder;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.voicerecorder.R;

import java.util.ArrayList;
import java.util.List;

public class recordings_1 extends Fragment {

    View view;
    public recordings_1() {
    }

    private static final int MY_PERMISSION = 1;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recordings,container,false);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION);
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION);
            }
        }

        else {
            dostuff();
        }
        return view;
    }

    public void dostuff()
    {

        ListView listView = (ListView)view.findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        getmusic();
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void getmusic()
    {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContext().getContentResolver().query(songUri,null,null,null,null);

        if(cursor!=null && cursor.moveToFirst())
        {
            int songtitle = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songartist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songlocation = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currtitle = cursor.getString(songtitle);
                String currartist = cursor.getString(songartist);
                String currlocation = cursor.getString(songlocation);
                arrayList.add("Title" + currtitle + "\n" + "Artist" + currartist + "\n" + "Location" + currlocation );
            }while (cursor.moveToNext());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION:
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        dostuff();
                    }
                }
                else {
                        Toast.makeText(getContext(),"No Permission Granted",Toast.LENGTH_SHORT).show();

                        getActivity().finish();
                    }
                    return;
            }
        }
    }
}
