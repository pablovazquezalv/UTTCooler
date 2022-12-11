package app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ramiro174.controlbluetooth.Views.ControlLogic;


public class Menus extends AppCompatActivity {

    //BOTON NAVEGACION QUE ES LA BARRRA DE ABAJO
    BottomNavigationView bottomNavigationView;

    //CUENTA CON FRAGMENTOS YA QUE NO SE PUEDE CON ACTIVITYS PARA HACER ESTE TIPO DE VISTA
    HomeFragment homeFragment= new HomeFragment();
    CoolerFragment coolerFragment= new CoolerFragment();
    PerfilFragment perfilFragment= new PerfilFragment();
    int REQUEST_CODE=200;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        //CASTEO DE LA BARRA DE NAVEGACION
        bottomNavigationView = findViewById(R.id.botton_navegation);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,homeFragment).commit();

        //METODO DE LA BARRA DE LA NAVEGACION
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //SE COMPONE POR ID QUE SE AGARRA DE MENU=MENU
                //EL ID DE LA PANTALLA INICIO ES ESTE
                if (item.getItemId() == R.id.home)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, homeFragment).commit();
                    return true;
                }
                //EL DEL CARRITO O COOLER O HIELERA
                if (item.getItemId() == R.id.carrito)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, coolerFragment).commit();
                    return true;
                }
                //EL DEL CONTROL
                if (item.getItemId() == R.id.control)
                {
                //startActivity(new Intent(getApplicationContext(), ControlLogic.class));

                    if(Build.VERSION.SDK_INT <  Build.VERSION_CODES.M)
                    {
                        startActivity(new Intent(getApplicationContext(), ControlLogic.class));

                    }else
                    {
                        if(ContextCompat.checkSelfPermission(Menus.this, Manifest.permission.BLUETOOTH)==  PackageManager.PERMISSION_GRANTED)
                        {
                            startActivity(new Intent(getApplicationContext(), ControlLogic.class));

                        }  else
                        {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(Menus.this,Manifest.permission.BLUETOOTH))
                            {

                            }
                            else
                            {
                                //EL USUARIO RECHAZO LOS PERMISOS 1 VEZ
                                //SE RECHAZARON
                                // RECIBE LOS PERMISOS Y LA ACCION Y RECIBE EL PERMISO (INT)
                            }ActivityCompat.requestPermissions(Menus.this,new String[]{Manifest.permission.BLUETOOTH},REQUEST_CODE);
                        }
                    }



                    return true;
                }
                //Y EL DEL PERFIL
                if (item.getItemId() == R.id.perfil)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, perfilFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE)
        {    //SI LOS PERMISOS FUERON HABILITADOS
            if (permissions.length>0    && grantResults[0]  ==    PackageManager.PERMISSION_GRANTED)
            {
                //ACCION DE LLAMAR
                startActivity(new Intent(getApplicationContext(), ControlLogic.class));

            }
            //SI LOS RECHAZA OTRA VEZ
            else
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Menus.this,Manifest.permission.BLUETOOTH))
                {
                    //
                }
                else
                {//SI LOS RECHAZO YA QUE ES LA ULTIMA VEZ DEBE ACTIVALOS MANUALMENTE
                    Toast.makeText(this, "activalos nuevamente manual", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}