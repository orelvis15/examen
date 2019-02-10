package com.examen.examenandroid.medioPago;

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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.examen.examenandroid.MainActivity;
import com.examen.examenandroid.Model.Model;
import com.examen.examenandroid.R;
import com.examen.examenandroid.ViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedioPagoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedioPagoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedioPagoFragment extends Fragment implements Model.ModelUpdate {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "MedioPagoFragment";

    private static final String SAVED_RECYCLER_VIEW_STATUS_ID = "saveStateRecycler";

    private OnFragmentInteractionListener mListener;

    public MedioPagoFragment() {
        // Required empty public constructor
    }


    public static MedioPagoFragment newInstance(String param1, String param2) {
        MedioPagoFragment fragment = new MedioPagoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView RecyclerMedioPago;
    private ArrayList listMedioPago;

    private RequestQueue requestQueue;

    private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_medio_pago, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Medio de Pago");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(getActivity());

        listMedioPago = new ArrayList();

        RecyclerMedioPago = layout.findViewById(R.id.id_listMedioPago);
        RecyclerMedioPago.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerMedioPago.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

        LoadData();

        return layout;
    }

    public void LoadData(){

        Model model = new Model(viewModel,getActivity(),this);
        model.getMedioPagoData();

    }

    /*public void LoadData(){

        String url = "https://api.mercadopago.com/v1/payment_methods?public_key=444a9ef5-8a6b-429f-abdf-587639155d88";

        if (viewModel.getListMedioPago().isEmpty()) {
            Log.d("salida1", "rest");
            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

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
                            MedioPagoAdapter adapter = new MedioPagoAdapter(getActivity(), listMedioPago);
                            RecyclerMedioPago.setAdapter(adapter);

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
        }else {
            Log.d("salida1", "cache");
            MedioPagoAdapter adapter = new MedioPagoAdapter(getActivity(), viewModel.getListMedioPago());
            RecyclerMedioPago.setAdapter(adapter);

        }
    }*/

    // TODO: Rename method, update argument and hook method into UI event
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
            MedioPagoAdapter adapter = new MedioPagoAdapter(getActivity(), data);
            RecyclerMedioPago.setAdapter(adapter);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
