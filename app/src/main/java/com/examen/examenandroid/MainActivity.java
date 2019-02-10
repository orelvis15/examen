package com.examen.examenandroid;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.examen.examenandroid.banco.Banco;
import com.examen.examenandroid.banco.BancoFragment;
import com.examen.examenandroid.cuotas.Cuotas;
import com.examen.examenandroid.cuotas.CuotasFragment;
import com.examen.examenandroid.medioPago.MedioPago;
import com.examen.examenandroid.medioPago.MedioPagoFragment;

public class MainActivity extends AppCompatActivity implements MontoFragment.OnFragmentInteractionListener,
        MedioPagoFragment.OnFragmentInteractionListener,
        BancoFragment.OnFragmentInteractionListener,
        CuotasFragment.OnFragmentInteractionListener,
        ResumenFragment.OnFragmentInteractionListener{

    public  String currentFragment = "";
    private ViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Trace.beginSection("mercado libre examen");

        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState != null ){

            Fragment f = fragmentManager.findFragmentByTag(savedInstanceState.getString("currentFragment"));
            fragmentTransaction.remove(f);
            fragmentTransaction.add(R.id.container, f,savedInstanceState.getString("currentFragment"));
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
            currentFragment = savedInstanceState.getString("currentFragment");

        }else {

            fragmentTransaction.replace(R.id.container, new MontoFragment(),MontoFragment.TAG);
            fragmentTransaction.addToBackStack(MontoFragment.TAG);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
            currentFragment = MontoFragment.TAG;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {

        String name = getSupportFragmentManager().findFragmentById(R.id.container).getClass().getSimpleName();

        switch (name){
            case MedioPagoFragment.TAG:{
                currentFragment = MontoFragment.TAG;
            }
            case BancoFragment.TAG:{
                currentFragment = MedioPagoFragment.TAG;
            }
            case CuotasFragment.TAG:{
                currentFragment = BancoFragment.TAG;
            }
        }
        if (!name.equals(MontoFragment.TAG) && !name.equals(ResumenFragment.TAG)){
            onBackPressed();
        }
        return super.onSupportNavigateUp();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("currentFragment",currentFragment);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public MedioPago getMedioPagoSelect() {
        return viewModel.getMedioPagoSelect();
    }

    public void setMedioPagoSelect(MedioPago medioPagoSelect) {
        viewModel.setMedioPagoSelect(medioPagoSelect);
    }

    public Cuotas getCuotasSelect() {
        return viewModel.getCuotasSelect();
    }

    public void setCuotasSelect(Cuotas cuota) {
        viewModel.setCuotasSelect(cuota);
    }

    public Banco getBancoSelect() {
        return viewModel.getBancoSelect();
    }

    public void setBancoSelect(Banco banco) {
        viewModel.setBancoSelect(banco);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onStop() {
        super.onStop();
        Trace.endSection();
    }
}
