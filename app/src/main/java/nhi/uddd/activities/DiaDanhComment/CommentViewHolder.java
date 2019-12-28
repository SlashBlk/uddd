package nhi.uddd.activities.DiaDanhComment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import nhi.uddd.R;

public class CommentViewHolder extends RecyclerView.ViewHolder{
    public TextView txtTenNguoiDung;
    public TextView txtNoiDung;
    public TextView txtNgayTao;
    public ImageView imgHinhAnhNguoiDung;
    public CommentViewHolder(View v) {
        super(v);
        txtNgayTao=v.findViewById(R.id.txtNgayTao);
        txtNoiDung= v.findViewById(R.id.txtNoiDung);
        txtTenNguoiDung=v.findViewById(R.id.txtTenNguoiDung);
        imgHinhAnhNguoiDung=v.findViewById(R.id.imgHinhAnhNguoiDung);
    }
}
