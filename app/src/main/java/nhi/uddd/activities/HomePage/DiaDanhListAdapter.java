package nhi.uddd.activities.HomePage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nhi.uddd.R;
import nhi.uddd.models.DiaDanh;

public class DiaDanhListAdapter extends RecyclerView.Adapter<DiaDanhViewHolder> {
    private List<DiaDanh> diaDanhs;

    public DiaDanhListAdapter() {
        this.diaDanhs = new ArrayList<>();
    }

    public DiaDanhListAdapter(List<DiaDanh> diaDanhs) {
        this.diaDanhs = diaDanhs;
    }

    public void addDiaDanh(List<DiaDanh> diaDanhs) {
        this.diaDanhs.addAll(diaDanhs);
        this.notifyDataSetChanged();
    }
    public void xoaDiaDanh(DiaDanh diaDanh){
        this.diaDanhs.remove(diaDanh);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiaDanhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.dia_danh_view_holder, parent, false);
        DiaDanhViewHolder viewHolder = new DiaDanhViewHolder(view);
        viewHolder.diaDanhListAdapter=this;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaDanhViewHolder holder, int position) {

        DiaDanh diaDanh = diaDanhs.get(position);
        holder.setDiaDanh(diaDanh);
        holder.txtTenDiaDanh.setText(diaDanh.getTenDiaDanh());
        holder.txtMoTa.setText(diaDanh.getMoTa());
        holder.maDiaDanh=diaDanh.getMaDiaDanh();
        String urlHinhAnh = diaDanh.getHinhAnh();
        Picasso.get().load(urlHinhAnh).into(holder.imgHinhAnhDiaDanh);
    }

    @Override
    public int getItemCount() {
        return diaDanhs.size();
    }


}