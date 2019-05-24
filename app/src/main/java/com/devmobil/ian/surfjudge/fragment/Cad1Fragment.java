package com.devmobil.ian.surfjudge.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.devmobil.ian.surfjudge.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cad1Fragment extends Fragment implements View.OnClickListener {

    private ImageView img;
    public EditText edtTitle;
    public EditText edtDescription;
    public EditText edtWaves;
    public Spinner spinner;
    public static final int PICK_IMAGE = 1234;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private FloatingActionButton fab;
    private String mCurrentPhotoPath;
    public Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cad1, container, false);
        spinner = root.findViewById(R.id.spinner);
        String[] aux = new String[]{"Open", "Long"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, aux);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);        // Inflate the layout for this fragment

        img = root.findViewById(R.id.imgCad);
        img.setOnClickListener(this);

        fab =  root.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        edtTitle = root.findViewById(R.id.edtTitle);
        edtDescription = root.findViewById(R.id.edtDescription);
        edtWaves = root.findViewById(R.id.edtWaves);
        return root;
    }


    @Override
    public void onClick(View v) {
        if(v == img){
            Intent it = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(it, "Selecione a foto do perfil"), PICK_IMAGE);
        }else if( v == fab){
           }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source;
        FileChannel destination;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        destination.close();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = Objects.requireNonNull(getContext()).getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @SuppressLint("SetTextI18n")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode != Activity.RESULT_CANCELED) {
            if (data.getData() != null) {
                try {
                    File novo = createImageFile();
                    copyFile(new File(getRealPathFromURI(data.getData())), novo);
                    img.setImageURI(data.getData());
                    uri = data.getData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String validate (){

        if(edtTitle.getText().toString().equals("")){
            return "Title is required! Please enter a title.";
        }else if(edtDescription.getText().toString().equals("")){
            return "Description is required! please enter a description.\n";
        }else if(edtWaves.getText().toString().equals("")){
            return "Waves is mandatory! please insert a waves.";
        }else if(uri == null){
            return "Banner is required! please insert a banner.";
        }
        return "";
    }
}