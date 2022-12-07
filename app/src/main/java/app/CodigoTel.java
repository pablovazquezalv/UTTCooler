package app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

//CLASE DE CODIGO DE VERIFICACION
public class CodigoTel extends AppCompatActivity {

    Button botoncreocuenta;
    EditText codigoverificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_tel);

        botoncreocuenta=(Button) findViewById(R.id.creocuentas);
        codigoverificacion=findViewById(R.id.codigodeverificacion);
        //BOTON DE VERIFICAR CODIGO SI ES CORRECTO ACCEDERA A LA PANTALLA PRINCIPAL
        botoncreocuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String codigo=codigoverificacion.getText().toString().trim();

                if(TextUtils.isEmpty(codigo))
                {
                    codigoverificacion.setError("El codigo es requerido");
                    return;
                }


                String urlcodigo="http://192.168.0.9:8000/api/verificarCodigo";
                JSONObject jsonbody= new JSONObject();
                try {
                    jsonbody.put("Code",codigoverificacion.getText());
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlcodigo, jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status= Integer.parseInt(response.getString("status"));
                            if(status==201)
                            {
                                Toast.makeText( CodigoTel.this, "TU CUENTA FUE ACTIVADA INICIA SESION"+status, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                startActivity(new Intent(getApplicationContext(), Menus.class));
            }
        });
    }
}