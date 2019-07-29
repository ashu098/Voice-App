package com.example.voicerecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class record_1 extends Fragment {


    View view;
    String pathsave;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    private int x=0;

    private File file,file1;

    public record_1() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record,container,false);

        final ImageButton record = (ImageButton) view.findViewById(R.id.ib1);
        final ImageButton record_stop = (ImageButton) view.findViewById(R.id.ib2);

        final int REQUEST_PERMISSION_CODE = 1000;

        if(!checkpermission())
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{

                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
            },REQUEST_PERMISSION_CODE);
        }

        file = new File(Environment.getExternalStorageDirectory() + "/Audio123");

        file.mkdirs();
            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkpermission()) {
                        x+=1;
                        pathsave = file.toString() + "/" + "audio_record" + x +".mp3";
                        setupMediaRecorder();
                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getActivity(),"Recording...",Toast.LENGTH_SHORT).show();

                        record_stop.setEnabled(true);

                    }
                    else {
                        ActivityCompat.requestPermissions(getActivity(),new String[]{

                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                        },REQUEST_PERMISSION_CODE);
                    }
                }
            });

            record_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaRecorder.stop();

                    record.setEnabled(true);
                    record_stop.setEnabled(false);
                    Toast.makeText(getActivity(),"Recording stopped...",Toast.LENGTH_SHORT).show();

                }
            });





        return view;
    }

    private void setupMediaRecorder()
    {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(pathsave);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1000:
            {
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private boolean checkpermission()
    {
        int external_storage = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.RECORD_AUDIO);

        return external_storage == PackageManager.PERMISSION_GRANTED && record_audio == PackageManager.PERMISSION_GRANTED;

    }


}


