package com.example.vhsince81;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.vhsince81.POJOpackage.PojoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiresApi(api = Build.VERSION_CODES.O)
public class VHRepository
{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String fileData;
    PojoDTO refDTO;

    public VHRepository(VHViewModel vhViewModel) {
        try {
            InputStream is =vhViewModel.getApplication().getApplicationContext().getAssets().open("VHlistitem.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            fileData = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
           // return null;
        }

        refDTO = gson.fromJson(fileData, PojoDTO.class);

    }


    public LiveData<PojoDTO> getCategoryList_Rep(String str)
    {
        final MutableLiveData<PojoDTO> data = new MutableLiveData<PojoDTO>();
        data.setValue(refDTO);

        return data;
    }
}
