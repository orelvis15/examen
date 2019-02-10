package com.examen.examenandroid.cuotas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examen.examenandroid.MainActivity;
import com.examen.examenandroid.R;
import com.examen.examenandroid.ResumenFragment;

import java.util.List;

public class CuotasAdapter extends RecyclerView.Adapter<CuotasAdapter.CuotaViewHolder> {
    private final Context context;
    private final List<Cuotas> listCoutas;

    public CuotasAdapter(Context context, List<Cuotas> listCuotas) {
        this.context = context;
        this.listCoutas = listCuotas;
    }

    @NonNull
    @Override
    public CuotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cuotas_item_view,viewGroup,false);

        return new CuotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuotaViewHolder cuotaViewHolder, int i) {

        cuotaViewHolder.nombre.setText(listCoutas.get(i).message);

    }

    @Override
    public int getItemCount() {
        return listCoutas.size();
    }

    class CuotaViewHolder extends RecyclerView.ViewHolder{

        final TextView nombre;

        private CuotaViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.id_message);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).setCuotasSelect(listCoutas.get(getLayoutPosition()));

                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new ResumenFragment(),ResumenFragment.TAG);
                    fragmentTransaction.addToBackStack(ResumenFragment.TAG);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.commit();

                    ((MainActivity)context).currentFragment = ResumenFragment.TAG;
                }
            });
        }
    }
}
