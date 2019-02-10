package com.examen.examenandroid.banco;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.examen.examenandroid.MainActivity;
import com.examen.examenandroid.R;
import com.examen.examenandroid.cuotas.CuotasFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BancoAdapter extends RecyclerView.Adapter<BancoAdapter.BancoViewHolder> {
    private final Context context;
    private final List<Banco> listBanco;

    public BancoAdapter(Context context, List<Banco> listBanco) {
        this.context = context;
        this.listBanco = listBanco;
    }

    @NonNull
    @Override
    public BancoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.banco_item_view,viewGroup,false);

        return new BancoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BancoViewHolder medioPagoViewHolder, int i) {

        medioPagoViewHolder.nombre.setText(listBanco.get(i).getNombre());
        Picasso.get().load(listBanco.get(i).getUrlBitmap()).into(medioPagoViewHolder.logo);

    }

    @Override
    public int getItemCount() {
        return listBanco.size();
    }

    class BancoViewHolder extends RecyclerView.ViewHolder{

        final TextView nombre;
        final ImageView logo;

        private BancoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.id_nombre);
            logo = itemView.findViewById(R.id.id_logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((MainActivity)context).setBancoSelect(listBanco.get(getLayoutPosition()));
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new CuotasFragment(),CuotasFragment.TAG);
                    fragmentTransaction.addToBackStack(CuotasFragment.TAG);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.commit();

                    ((MainActivity)context).currentFragment = CuotasFragment.TAG;
                }
            });

        }
    }

}
