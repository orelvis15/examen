package com.examen.examenandroid;

import com.examen.examenandroid.banco.Banco;
import com.examen.examenandroid.cuotas.Cuotas;
import com.examen.examenandroid.medioPago.MedioPago;

import java.util.ArrayList;
import java.util.List;

public class ViewModel extends android.arch.lifecycle.ViewModel {

    private Integer monto;
    private ArrayList listMedioPago = new ArrayList();
    private ArrayList listBanco = new ArrayList();
    private ArrayList listCuotas = new ArrayList();

    private MedioPago medioPagoSelect;
    private Banco bancoSelect;
    private Cuotas CuotasSelect;

    public ArrayList getListCuotas() {
        return listCuotas;
    }

    public void setListCuotas(ArrayList listCuotas) {
        this.listCuotas = listCuotas;
    }

    public Banco getBancoSelect() {
        return bancoSelect;
    }

    public void setBancoSelect(Banco bancoSelect) {
        this.bancoSelect = bancoSelect;
    }

    public Cuotas getCuotasSelect() {
        return CuotasSelect;
    }

    public void setCuotasSelect(Cuotas cuotasSelect) {
        CuotasSelect = cuotasSelect;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public ArrayList getListMedioPago() {
        return listMedioPago;
    }

    public void setListMedioPago(ArrayList listMedioPago) {
        this.listMedioPago = listMedioPago;
    }

    public ArrayList getListBanco() {
        return listBanco;
    }

    public void setListBanco(ArrayList listBanco) {
        this.listBanco = listBanco;
    }

    public MedioPago getMedioPagoSelect() {
        return medioPagoSelect;
    }

    public void setMedioPagoSelect(MedioPago medioPagoSelect) {
        this.medioPagoSelect = medioPagoSelect;
    }
}
