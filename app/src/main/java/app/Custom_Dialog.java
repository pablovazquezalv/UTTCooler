package app;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.UTTCOOLER.Integradora.R;
import com.ramiro174.controlbluetooth.Models.Bluetooth;
import com.ramiro174.controlbluetooth.socket.ConnectedThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class Custom_Dialog extends AppCompatDialogFragment {


    CustomDialogInterface customDialogInterface;
    EditText nombredered,contraseñared,iokey,usuarioadafruit;


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

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                    }
                }
            });


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.custom_dialog,null);

        builder.setView(view)
                .setTitle("Vincular Carrito")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String enviarnombre=nombredered.getText().toString();
                        String enviocontraseña=contraseñared.getText().toString();
                        String envioiokey=iokey.getText().toString();
                        String enviousuario=usuarioadafruit.getText().toString();
                        customDialogInterface.appTexts(enviarnombre,enviocontraseña,envioiokey,enviousuario);

                        JSONObject datosEnviar = new JSONObject();

                        try {
                            datosEnviar.put("nombredered",enviarnombre);
                            datosEnviar.put("contraseñared", enviocontraseña);
                            datosEnviar.put("iokey", envioiokey);
                            datosEnviar.put("usuarioadafruit", enviousuario);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        if (mBluetoothAdapter != null) {
                            if (mConnectedThread != null) {
                                mConnectedThread.write(datosEnviar.toString() + "?");
                                Toast.makeText(getContext(), "mexico", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        //CASTEO DEL CUSTOM DIALOG QUE ES LA VISTA DE ¿L
        nombredered=view.findViewById(R.id.escribirnombredered);
        contraseñared=view.findViewById(R.id.escribircontraseñared);
        iokey=view.findViewById(R.id.escribiriokeyadafruit);
        usuarioadafruit=view.findViewById(R.id.escribirusuarioadafruit);
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        customDialogInterface =(CustomDialogInterface) context;
    }

    public interface CustomDialogInterface
    {
        void appTexts(String envionombre,String enviocontraseña,String envioiokey,String enviousuario);
    }

}
