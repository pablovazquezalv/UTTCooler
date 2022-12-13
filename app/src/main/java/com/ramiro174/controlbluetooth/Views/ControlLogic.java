package com.ramiro174.controlbluetooth.Views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.ramiro174.controlbluetooth.Adapters.BluetoothAdapterLista;
import com.ramiro174.controlbluetooth.Models.Bluetooth;
//import com.ramiro174.controlbluetooth.R;
import com.ramiro174.controlbluetooth.socket.ConnectedThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import app.singleton.Singleton;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class ControlLogic extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    //BATERIA
    Button incrase,decrease;
    DonutProgress donutProgress;
    int progress;
    private RequestQueue requestQueue;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final String KEY_ID="id";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";

    //CUSTOM DIALOG

    //BLUETOOTH
    TextView txtblue, mBluetoothStatus, mTextViewAngleRight, mTextViewStrengthRight, mTextViewCoordinateRight;
    Switch btnconectar;
    BluetoothAdapter mBluetoothAdapter;
    List<Bluetooth> ListaBluetooth;
    Spinner SpnListaBluetooth;
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    public final static int MESSAGE_READ = 2;
    private final static int CONNECTING_STATUS = 3;
    private BluetoothSocket mBTSocket = null;
    private Handler mHandler;
    private ConnectedThread mConnectedThread;

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferences= getApplicationContext().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String usernameadafruit= preferences.getString(KEY_USERADAFRUIT,null);
        String iokey= preferences.getString(KEY_IOKEY,null);
        editor=preferences.edit();

        //BATERIA
        requestQueue = Singleton.getInstance(ControlLogic.this).getRequestQueue();
       // updateProgressBar(usernameadafruit,iokey);

        //BLUETOOTH
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothStatus = (TextView) findViewById(R.id.mBluetoothStatus);
        SpnListaBluetooth = (Spinner) findViewById(R.id.SpnListaBluetooth);

        ListaBluetooth = new ArrayList<>();


        listPairedDevices();
        mHandler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    readMessage = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
                    txtblue.setText(readMessage);
                }

                if (msg.what == CONNECTING_STATUS) {
                    char[] sConnected;
                    if (msg.arg1 == 1)
                        mBluetoothStatus.setText(getString(R.string.BTConnected) + msg.obj);
                    else
                        mBluetoothStatus.setText("Fallo La Conexion");
                }
            }
        };
        SpnListaBluetooth.setOnItemSelectedListener(this);

        mTextViewAngleRight = (TextView) findViewById(R.id.textView_angle_right);
        mTextViewStrengthRight = (TextView) findViewById(R.id.textView_strength_right);
        mTextViewCoordinateRight = findViewById(R.id.textView_coordinate_right);

        final JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right);


        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener()
        {

            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {
                mTextViewAngleRight.setText(angle + "°");
                mTextViewStrengthRight.setText(strength + "%");
                mTextViewCoordinateRight.setText(String.format("x%03d:y%03d", joystickRight.getNormalizedX(), joystickRight.getNormalizedY()));

                JSONObject datosEnviar = new JSONObject();

                try {
                    datosEnviar.put("x", joystickRight.getNormalizedX());
                    datosEnviar.put("y", joystickRight.getNormalizedY());
                    datosEnviar.put("angulo", angle);
                    datosEnviar.put("strength", strength);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mBluetoothAdapter != null) {
                    if (mConnectedThread != null) {
                        mConnectedThread.write(datosEnviar.toString() + "?");

                    }
                }
            }
        }, 50);

    }


    private void updateProgressBar(String usernameadafruit,String iokey)
    {


        String urlbateria="https://io.adafruit.com/api/v2/"+usernameadafruit+"/dashboards";


      //  donutProgress=findViewById(R.id.donutprogress);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlbateria, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
              //  donutProgress.setText(response.toString());
                donutProgress.setDonut_progress(String.valueOf(response.toString()));
                try {
                    String userid=response.getString("name");
                    donutProgress.setText(userid);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-AIO-Key",iokey);

                return headers;
            }

        };
    }

    public void bluetoothOn()
    {

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_LONG).show();
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mGetContent.launch(enableBtIntent);
            }
        }
    }

    private void listPairedDevices()
    {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
        ListaBluetooth.clear();
        if (mBluetoothAdapter.isEnabled()) {

            mPairedDevices.forEach(device -> {
                BluetoothDevice mDevice = device;
//                Log.i("listPairedDevices: ",mDevice.getName()+" "+mDevice.getAddress());
                ListaBluetooth.add(new Bluetooth(mDevice.getName(), mDevice.getAddress(), "Paired"));
            });
//
            SpnListaBluetooth.setAdapter(new BluetoothAdapterLista(this, R.layout.itembluetooth, ListaBluetooth));

         //   Toast.makeText(getApplicationContext(), getString(R.string.show_paired_devices), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        BluetoothAdapterLista adapter = (BluetoothAdapterLista) adapterView.getAdapter();
        Bluetooth mDevice = adapter.getListaBluetooth().get(i);

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getBaseContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();
            return;
        }

        mBluetoothStatus.setText(getString(R.string.cConnet));
        // Get the device MAC address, which is the last 17 chars in the View

        final String name = mDevice.getName();
        final String address = mDevice.getAddress();

        // Spawn a new thread to avoid blocking the GUI one
        new Thread() {
            @Override
            public void run() {
                boolean fail = false;

                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

                try {
                    mBTSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    }
                    mBTSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                    }
                }
                if (!fail) {
                    mConnectedThread = new ConnectedThread(mBTSocket, mHandler);
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                            .sendToTarget();
                }
            }
        }.start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e("Error", "Could not create Insecure RFComm Connection", e);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        return device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }




}