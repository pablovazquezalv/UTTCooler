package com.ramiro174.controlbluetooth.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.UTTCOOLER.Integradora.R;
import com.ramiro174.controlbluetooth.Models.Bluetooth;
//import com.ramiro174.controlbluetooth.R;

import java.util.List;


public class BluetoothAdapterLista extends ArrayAdapter<Bluetooth> {

private  List<Bluetooth> ListaBluetooth;
private Context context;
private  int resource;

    public BluetoothAdapterLista(@NonNull Context context, int resource, @NonNull List<Bluetooth> ListaBluetooth) {
        super(context, resource, ListaBluetooth);
        this.ListaBluetooth = ListaBluetooth;
        this.context = context;
        this.resource = resource;
    }

    public List<Bluetooth> getListaBluetooth() {
        return ListaBluetooth;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        view = LayoutInflater.from(context).inflate(resource,  parent, false);
        Bluetooth bluetooth = ListaBluetooth.get(position);
        TextView txtNombre = view.findViewById(R.id.txtNameBluetooth);
        txtNombre.setText(bluetooth.getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        view = LayoutInflater.from(context).inflate(resource,  parent, false);
        Bluetooth bluetooth = ListaBluetooth.get(position);
        TextView txtNombre = view.findViewById(R.id.txtNameBluetooth);
        txtNombre.setText(bluetooth.getName());
        return view;
    }
}
