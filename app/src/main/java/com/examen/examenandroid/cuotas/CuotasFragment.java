package com.examen.examenandroid.cuotas;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.examen.examenandroid.MainActivity;
import com.examen.examenandroid.R;
import com.examen.examenandroid.ViewModel;
import com.examen.examenandroid.Model.Model;

import java.util.ArrayList;

public class CuotasFragment extends Fragment implements Model.ModelUpdate {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "CuotasFragment";

    private OnFragmentInteractionListener mListener;

    public CuotasFragment() {
        // Required empty public constructor
    }

    public static CuotasFragment newInstance(String param1, String param2) {
        CuotasFragment fragment = new CuotasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView RecyclerCuota;
    private ArrayList listCuota;

    private RequestQueue requestQueue;

    private ViewModel viewModel;

    private TextView smsEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cuotas, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Cuotas");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(getActivity());

        smsEmpty = layout.findViewById(R.id.id_sms_empty);

        listCuota = new ArrayList();

        RecyclerCuota = layout.findViewById(R.id.id_listCuotas);
        RecyclerCuota.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerCuota.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

        LoadData();

        return layout;
    }

    private void LoadData() {
        Model model = new Model(viewModel,getActivity(),this);
        model.getCuotasData();
    }


    /*private void LoadData() {

        String payment_method_id = viewModel.getMedioPagoSelect().getId();
        String amount = viewModel.getMonto().toString();
        String banco = viewModel.getBancoSelect().getId();

        String url = "https://api.mercadopago.com/v1/payment_methods/installments?public_key=444a9ef5-8a6b-429f-abdf-587639155d88&payment_method_id="
                + payment_method_id + "&amount=" + amount + "&issuer.id="+banco;

        if (viewModel.getListCuotas().isEmpty()) {
            Log.d("salida1", "rest");
            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if (response.length() != 0) {
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
                                CuotasAdapter adapter = new CuotasAdapter(getActivity(), listCuota);
                                RecyclerCuota.setAdapter(adapter);

                            }else{
                                smsEmpty.setVisibility(View.VISIBLE);
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("salida", String.valueOf(error.getMessage()));

                        }
                    });


            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjReq);
        } else {
            Log.d("salida1", "cache");
            CuotasAdapter adapter = new CuotasAdapter(getActivity(), viewModel.getListCuotas());
            RecyclerCuota.setAdapter(adapter);

        }
    }*/


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void UpdateView(ArrayList data) {

        if (data != null){
            CuotasAdapter adapter = new CuotasAdapter(getActivity(), data);
            RecyclerCuota.setAdapter(adapter);
        }else{
            smsEmpty.setVisibility(View.VISIBLE);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
