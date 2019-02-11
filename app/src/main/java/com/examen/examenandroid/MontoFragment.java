package com.examen.examenandroid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.examen.examenandroid.medioPago.MedioPagoFragment;

public class MontoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "MontoFragment";

    private OnFragmentInteractionListener mListener;

    public MontoFragment() {
        // Required empty public constructor
    }

    public static MontoFragment newInstance(String param1, String param2) {
        MontoFragment fragment = new MontoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText montoEt;
    private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_monto, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Monto");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        montoEt = layout.findViewById(R.id.id_monto);
        Button nextBtn = layout.findViewById(R.id.id_next);

        if (viewModel.getMonto() != null)
            montoEt.setText(String.valueOf(viewModel.getMonto()));

        montoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try{
                    viewModel.setMonto(Integer.valueOf(s.toString()));
                }catch (Exception e){
                    viewModel.setMonto(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (CheckMonto(viewModel.getMonto())){

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MedioPagoFragment(),MedioPagoFragment.TAG);
                fragmentTransaction.addToBackStack(MedioPagoFragment.TAG);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();

                ((MainActivity)getActivity()).currentFragment = MedioPagoFragment.TAG;

            }else {
                montoEt.setError(getString(R.string.error_monto));
            }

            }
        });

        return layout;
    }

    private boolean CheckMonto(Integer monto){

        return monto != null && monto >= 10;

    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
