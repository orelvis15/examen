package com.examen.examenandroid.medioPago;

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
import com.examen.examenandroid.banco.BancoFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MedioPagoAdapter extends RecyclerView.Adapter<MedioPagoAdapter.MedioPagoViewHolder> {
    final private Context context;
    final private List<MedioPago> listMedioPago;

    public MedioPagoAdapter(Context context, List<MedioPago> listMedioPago) {
        this.context = context;
        this.listMedioPago = listMedioPago;
    }

    @NonNull
    @Override
    public MedioPagoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.medio_pago_item_view,viewGroup,false);

        return new MedioPagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedioPagoViewHolder medioPagoViewHolder, int i) {

        medioPagoViewHolder.nombre.setText(listMedioPago.get(i).getNombre());
        Picasso.get().load(listMedioPago.get(i).getUrlBitmap()).into(medioPagoViewHolder.logo);
        //new loadImage(listMedioPago.get(i).getUrlBitmap(),medioPagoViewHolder.logo).execute();

    }

    @Override
    public int getItemCount() {
        return listMedioPago.size();
    }

    class MedioPagoViewHolder extends RecyclerView.ViewHolder{

        final TextView nombre;
        final ImageView logo;

        private MedioPagoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.id_nombre);
            logo = itemView.findViewById(R.id.id_logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).setMedioPagoSelect(listMedioPago.get(getLayoutPosition()));

                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new BancoFragment(),BancoFragment.TAG);
                    fragmentTransaction.addToBackStack(BancoFragment.TAG);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.commit();

                    ((MainActivity)context).currentFragment = BancoFragment.TAG;
                }
            });
        }
    }

    /*private class loadImage extends AsyncTask<Void, Void, Bitmap> {

        final String url;
        final ImageView image;

        public loadImage(String url, ImageView image) {
            this.url = url;
            this.image = image;
        }

        @SuppressLint("WrongThread")
        @Override
        protected Bitmap doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(this.url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);

            image.setImageBitmap(bmp);

        }
    }*/

}
