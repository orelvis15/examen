package com.examen.examenandroid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class ResumenFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "ResumenFragment";

    private OnFragmentInteractionListener mListener;

    public ResumenFragment() {
        // Required empty public constructor
    }

    public static ResumenFragment newInstance(String param1, String param2) {
        ResumenFragment fragment = new ResumenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_resumen, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Resumen");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ViewModel viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

        TextView monto = layout.findViewById(R.id.id_monto);
        TextView medioPago = layout.findViewById(R.id.id_medio_pago);
        ImageView medioPagoLogo = layout.findViewById(R.id.id_medio_pago_logo);
        TextView banco = layout.findViewById(R.id.id_banco);
        ImageView bacoLogo = layout.findViewById(R.id.id_banco_logo);
        TextView cuotas = layout.findViewById(R.id.id_cuotas);
        Button back = layout.findViewById(R.id.id_back);

        monto.setText(String.valueOf(viewModel.getMonto()));
        medioPago.setText(viewModel.getMedioPagoSelect().getNombre());
        banco.setText(viewModel.getBancoSelect().getNombre());
        cuotas.setText(viewModel.getCuotasSelect().getMessage());

        Picasso.get().load(viewModel.getMedioPagoSelect().getUrlBitmap()).into(medioPagoLogo);
        Picasso.get().load(viewModel.getBancoSelect().getUrlBitmap()).into(bacoLogo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MontoFragment(),MontoFragment.TAG);
                fragmentTransaction.addToBackStack(MontoFragment.TAG);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
                ((MainActivity)getActivity()).currentFragment = MontoFragment.TAG;

            }
        });

        return layout;
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
        void onFragmentInteraction(Uri uri);
    }
}
