package com.example.vhsince81;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.vhsince81.POJOpackage.PojoDTO;

public class VHViewModel extends AndroidViewModel {

    private final LiveData<PojoDTO> dtoObject;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public VHViewModel(Application application)
    {
        super(application);
        dtoObject = new VHRepository(this).getCategoryList_Rep("");


    }

    public LiveData<PojoDTO> getCategoryList_VM()
    {
        return dtoObject;
    }
}
