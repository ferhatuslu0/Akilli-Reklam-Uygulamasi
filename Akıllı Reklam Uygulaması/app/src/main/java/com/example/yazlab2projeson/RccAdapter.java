package com.example.yazlab2projeson;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RccAdapter extends RecyclerView.Adapter<RccAdapter.MyViewHolder>{

    ArrayList<firma> list;
    public RccAdapter(ArrayList<firma> list){
        this.list=list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from (viewGroup.getContext ()).inflate (R.layout.gridview,viewGroup,false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.firmaAd.setText (list.get (i).getFirmaAdi ());
        myViewHolder.x.setText (list.get (i).getxLokasyon());
        myViewHolder.y.setText (list.get (i).getyLokasyon ());
        myViewHolder.icerik.setText (list.get (i).getKampanyaIcerik ());
        myViewHolder.sure.setText (list.get (i).getKampanyaSuresi ());
        myViewHolder.kategori.setText (list.get (i).getKategori());

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView firmaAd,x,y,sure,icerik,kategori;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            firmaAd =itemView.findViewById (R.id.textAd);
            x =itemView.findViewById (R.id.textX);
            y =itemView.findViewById (R.id.textY);
            icerik=itemView.findViewById (R.id.textIcerik);
            sure=itemView.findViewById (R.id.textSure);
            kategori=itemView.findViewById (R.id.textKategori);
        }
    }
}
