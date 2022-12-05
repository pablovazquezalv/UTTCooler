package app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.UTTCOOLER.Integradora.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ramiro174.controlbluetooth.Views.ControlLogic;

//ESTE ES EL MENU CON INFORMACION, ES EL QUE MUESTRA EL PERFIL,MISHIELERAS,EL CONTROL Y EL INICIO
public class Menus extends AppCompatActivity {

    //BOTON NAVEGACION QUE ES LA BARRRA DE ABAJO
    BottomNavigationView bottomNavigationView;

    //CUENTA CON FRAGMENTOS YA QUE NO SE PUEDE CON ACTIVITYS PARA HACER ESTE TIPO DE VISTA
    HomeFragment homeFragment= new HomeFragment();
    CoolerFragment coolerFragment= new CoolerFragment();
    PerfilFragment perfilFragment= new PerfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        //CASTEO DE LA BARRA DE NAVEGACION
        bottomNavigationView = findViewById(R.id.botton_navegation);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,homeFragment).commit();

        //METODO DE LA BARRA DE LA NAVEGACION
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //SE COMPONE POR ID QUE SE AGARRA DE MENU=MENU
                //EL ID DE LA PANTALLA INICIO ES ESTE
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, homeFragment).commit();
                    return true;
                }
                //EL DEL CARRITO O COOLER O HIELERA
                if (item.getItemId() == R.id.carrito) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, coolerFragment).commit();
                    return true;
                }
                //EL DEL CONTROL
                if (item.getItemId() == R.id.control) {
                    startActivity(new Intent(getApplicationContext(), ControlLogic.class));
                    // getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, carFragment).commit();
                    return true;
                }
                //Y EL DEL PERFIL
                if (item.getItemId() == R.id.perfil) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, perfilFragment).commit();


                    // getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, carFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}