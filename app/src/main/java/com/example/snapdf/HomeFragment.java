package com.example.snapdf;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class HomeFragment extends Fragment {
    TextView homeText;

    public HomeFragment() {
        // Required empty public constructor
    }

    ListView lv_pdf;
    public static ArrayList<File> fileList = new ArrayList<>();
    PDFAdpter obj_adapter;
    public  static int REQUEST_PERMISSION = 1;
    boolean bolean_permission;
    File dir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        lv_pdf = view.findViewById(R.id.listView_pdf);
        dir = new File(Environment.getExternalStorageDirectory().toString());

        permisson_fn();

        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ViewPdfFiles.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void permisson_fn() {
        if((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED)) {

            if((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
            }
            else {
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
        else {
            bolean_permission = true;
            getfile(dir);
            obj_adapter = new PDFAdpter(getContext(), fileList);
            lv_pdf.setAdapter(obj_adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bolean_permission = true;
                getfile(dir);
                obj_adapter = new PDFAdpter(getContext(), fileList);
                lv_pdf.setAdapter(obj_adapter);
            }
            else {
                Toast.makeText(getActivity(), "Please allow permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayList<File> getfile(File dir) {

        File listFile[] = dir.listFiles();

        if(listFile != null && listFile.length > 0) {

            for(int i = 0; i < listFile.length; i++) {

                if(listFile[i].isDirectory()) {
                    getfile(listFile[i]);
                }
                else {
                    boolean booleanpdf = false;

                    if(listFile[i].getName().endsWith(".pdf")) {

                        for(int j = 0; j < fileList.size(); ++j) {
                            if(fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            }
                            else {

                            }

                        }

                        if(booleanpdf) {
                            booleanpdf = false;
                        }
                        else {
                            fileList.add(listFile[i]);
                        }
                    }
                }
            }
        }
        return fileList;
    }

}
