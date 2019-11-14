package com.example.crudroom.adapters;

import android.app.Activity;
import android.app.Dialog;
import androidx.room.Room;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.crudroom.R;
import com.example.crudroom.CreateActivity;
import com.example.crudroom.ReadActivity;
import com.example.crudroom.AppDatabase;
import com.example.crudroom.model.Barang;

public class AdapterBarangRecyclerView extends RecyclerView.Adapter<AdapterBarangRecyclerView.ViewHolder> {
    private ArrayList<Barang> daftarBarang;
    private Context context;
    private AppDatabase db;
    public AdapterBarangRecyclerView(ArrayList<Barang> barangs, Context
            ctx){
/**
 * Inisiasi data dan variabel yang akan digunakan
 */
        daftarBarang = barangs;
        context = ctx;
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "barangdb").allowMainThreadQueries().build();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap
         item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle;
        CardView cvMain;ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_namabarang);
            cvMain = v.findViewById(R.id.cv_main);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/**
 * Inisiasi ViewHolder
 */
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang,
                        parent, false);
// mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
/**
 * Menampilkan data pada view
 */
        final String name = daftarBarang.get(position).getNamaBarang();
        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/**
 * Kodingan untuk tutorial Selanjutnya Read detail data
 */
                Barang barang =
                        db.barangDAO().selectBarangDetail(daftarBarang.get(position).getBarangId())
                        ;
                context.startActivity(ReadActivity.getActIntent((Activity)
                        context).putExtra("data", barang));
            }
        });
        holder.cvMain.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view) {
/**
 * Kodingan untuk tutorial Selanjutnya :p Delete dan
 update data
 */
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.view_dialog);
                dialog.setTitle("Pilih Aksi");
                dialog.show();
                Button editButton = dialog.findViewById(R.id.bt_edit_data);
                Button delButton =
                        dialog.findViewById(R.id.bt_delete_data);
//apabila tombol edit diklik
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        onEditBarang(position);
                    }
                }
         );
//apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                onDeteleBarang(position);
                            }
                        }
                );
                return true;
            }
        });
        holder.tvTitle.setText(name);
    }
    private void onDeteleBarang(int position){
        db.barangDAO().deleteBarang(daftarBarang.get(position));
        daftarBarang.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, daftarBarang.size());
    }
    private void onEditBarang(int position){
        context.startActivity(CreateActivity.getActIntent((Activity)
                context).putExtra("data", daftarBarang.get(position)));
    }
    @Override
    public int getItemCount() {
/**
 * Mengembalikan jumlah item pada barang
 */
        return daftarBarang.size();
    }
}
