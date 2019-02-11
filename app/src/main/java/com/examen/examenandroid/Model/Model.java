package com.examen.examenandroid.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.examen.examenandroid.ViewModel;
import com.examen.examenandroid.banco.Banco;
import com.examen.examenandroid.cuotas.Cuotas;
import com.examen.examenandroid.medioPago.MedioPago;
import com.securepreferences.SecurePreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Model {

    private ModelUpdate viewCallback;
    private ViewModel viewModel;
    private RequestQueue requestQueue;
    private ArrayList listMedioPago;
    private ArrayList listBanco;
    private ArrayList listCuota;
    private Context context;

    public Model(ViewModel viewModel, Context context, ModelUpdate viewCallback) {
        this.viewModel = viewModel;
        requestQueue = Volley.newRequestQueue(context);
        listMedioPago = new ArrayList();
        listBanco = new ArrayList();
        listCuota = new ArrayList();
        this.viewCallback = viewCallback;
        this.context = context;

    }

    public void getMedioPagoData() {
        new AsyncLoadMedioPagoData(context).execute();
    }

    public void getBancoData() {
        new AsyncLoadBancoData(context).execute();
    }

    public void getCuotasData() {
        new AsyncLoadCuotasData(context).execute();
    }

    private class AsyncLoadMedioPagoData extends AsyncTask<Void, Void, ArrayList> {

        WeakReference<Context> context;

        public AsyncLoadMedioPagoData(Context context) {
            this.context = new WeakReference(context);
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {

            if (viewModel.getListMedioPago().isEmpty()) {

                SharedPreferences preferences = new SecurePreferences(context.get(), "lol", "my_user_prefs.xml");

                String public_key = preferences.getString("public_key","");

                String url = "https://api.mercadopago.com/v1/payment_methods?public_key=" + public_key;

                final RequestFuture<JSONArray> future = RequestFuture.newFuture();

                JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                        url, null, future, future);
                requestQueue.add(jsonObjReq);

                try {
                    JSONArray response = future.get();

                    for (int i = 0; i < response.length() - 1; i++) {

                        try {

                            if (response.getJSONObject(i).getString("payment_type_id").equals("credit_card")) {

                                String id = response.getJSONObject(i).getString("id");
                                String nombre = response.getJSONObject(i).getString("name");
                                String urlLogo = response.getJSONObject(i).getString("thumbnail");

                                listMedioPago.add(new MedioPago(id, nombre, urlLogo));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    viewModel.setListMedioPago(listMedioPago);
                    return listMedioPago;

                } catch (InterruptedException e) {
                    return null;
                } catch (ExecutionException e) {
                    return null;
                }

            }else {
                return viewModel.getListMedioPago();
            }

        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);

            viewCallback.UpdateView(arrayList);
        }
    }


    private class AsyncLoadBancoData extends AsyncTask<Void, Void, ArrayList> {

        WeakReference<Context> context;

        public AsyncLoadBancoData(Context context) {
            this.context = new WeakReference(context);
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {

            if (viewModel.getListBanco().isEmpty()) {

                SharedPreferences preferences = new SecurePreferences(context.get(), "lol", "my_user_prefs.xml");

                String public_key = preferences.getString("public_key","");

                String payment_method_id = viewModel.getMedioPagoSelect().getId();

                String url = "https://api.mercadopago.com/v1/payment_methods/card_issuers?public_key="+public_key+"&payment_method_id=" + payment_method_id;

                final RequestFuture<JSONArray> future = RequestFuture.newFuture();

                JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                        url, null, future, future);
                requestQueue.add(jsonObjReq);

                try {
                    JSONArray response = future.get();

                    for (int i = 0; i < response.length() - 1; i++) {

                        try {

                                String id = response.getJSONObject(i).getString("id");
                                String nombre = response.getJSONObject(i).getString("name");
                                String urlLogo = response.getJSONObject(i).getString("thumbnail");

                                listBanco.add(new Banco(id, nombre, urlLogo));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    viewModel.setListBanco(listBanco);
                    return listBanco;

                } catch (InterruptedException e) {
                    return null;
                } catch (ExecutionException e) {
                    return null;
                }

            }else {
                return viewModel.getListBanco();
            }

        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);

            viewCallback.UpdateView(arrayList);
        }
    }

    private class AsyncLoadCuotasData extends AsyncTask<Void, Void, ArrayList> {

        WeakReference<Context> context;

        public AsyncLoadCuotasData(Context context) {
            this.context = new WeakReference(context);
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {

            if (viewModel.getListCuotas().isEmpty()) {

                SharedPreferences preferences = new SecurePreferences(context.get(), "lol", "my_user_prefs.xml");

                String public_key = preferences.getString("public_key","");


                String payment_method_id = viewModel.getMedioPagoSelect().getId();
                String amount = viewModel.getMonto().toString();
                String banco = viewModel.getBancoSelect().getId();

                String url = "https://api.mercadopago.com/v1/payment_methods/installments?public_key="+public_key+"&payment_method_id="
                        + payment_method_id + "&amount=" + amount + "&issuer.id="+banco;

                final RequestFuture<JSONArray> future = RequestFuture.newFuture();

                JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                        url, null, future, future);
                requestQueue.add(jsonObjReq);

                try {
                    JSONArray response = future.get();

                    JSONArray json;
                    try {
                        json = response.getJSONObject(0).getJSONArray("payer_costs");
                    } catch (JSONException e) {
                        json = new JSONArray();
                        e.printStackTrace();
                    }

                    for (int i = 0; i < json.length() - 1; i++) {

                        try {

                            String message = json.getJSONObject(i).getString("recommended_message");

                            listCuota.add(new Cuotas(message));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    viewModel.setListCuotas(listCuota);
                    return listCuota;

                } catch (InterruptedException e) {
                    return null;
                } catch (ExecutionException e) {
                    return null;
                }

            }else {
                return viewModel.getListCuotas();
            }

        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);

            viewCallback.UpdateView(arrayList);
        }
    }

    public interface ModelUpdate{
        abstract void UpdateView(ArrayList data);
    }
}
