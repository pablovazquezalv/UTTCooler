package com.ramiro174.controlbluetooth.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ramiro174.controlbluetooth.Models.Bluetooth;

public class LiveDataBluetoothViewModel extends ViewModel {

    private MutableLiveData<Bluetooth> bluetoothMutableLiveData; ;

    public LiveData<Bluetooth> getBluetoothMutableLiveData() {
        if (bluetoothMutableLiveData == null) {
            bluetoothMutableLiveData = new MutableLiveData<>();
        }
        return bluetoothMutableLiveData;
    }
    public void setBluetoothMutableLiveData(Bluetooth bluetooth){
        bluetoothMutableLiveData.setValue(bluetooth);
    }
}
