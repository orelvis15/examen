package com.examen.examenandroid.banco;

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


public class BancoFragment extends Fragment implements Model.ModelUpdate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "BancoFragment";

    private OnFragmentInteractionListener mListener;

    public BancoFragment() {
        // Required empty public constructor
    }

    public static BancoFragment newInstance(String param1, String param2) {
        BancoFragment fragment = new BancoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView RecyclerBanco;
    private ArrayList listBanco;

    private RequestQueue requestQueue;

    private ViewModel viewModel;

    private TextView smsEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_banco, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Banco");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(getActivity());

        smsEmpty = layout.findViewById(R.id.id_sms_empty);

        listBanco = new ArrayList();

        RecyclerBanco = layout.findViewById(R.id.id_listBanco);
        RecyclerBanco.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerBanco.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

        LoadData();

        return layout;
    }

    private void LoadData(){
        Model model = new Model(viewModel,getActivity(),this);
        model.getBancoData();
    }

    /*private void LoadData() {

        String payment_method_id = viewModel.getMedioPagoSelect().getId();

        String url = "https://api.mercadopago.com/v1/payment_methods/card_issuers?public_key=444a9ef5-8a6b-429f-abdf-587639155d88&payment_method_id=" + payment_method_id;

        if (viewModel.getListBanco().isEmpty()) {
            Log.d("salida1", "rest");
            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if (response.length()!=0) {
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
                                BancoAdapter adapter = new BancoAdapter(getActivity(), listBanco);
                                RecyclerBanco.setAdapter(adapter);
                            }else {
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
            BancoAdapter adapter = new BancoAdapter(getActivity(), viewModel.getListBanco());
            RecyclerBanco.setAdapter(adapter);

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

        if (data == null){
            smsEmpty.setVisibility(View.VISIBLE);
        }else {
            smsEmpty.setVisibility(View.INVISIBLE);
            BancoAdapter adapter = new BancoAdapter(getActivity(), data);
            RecyclerBanco.setAdapter(adapter);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
