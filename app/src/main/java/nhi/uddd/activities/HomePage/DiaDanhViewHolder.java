package nhi.uddd.activities.HomePage;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;

import nhi.uddd.R;
import nhi.uddd.activities.DiaDanhInfo.DiaDanhInfoActivity;
import nhi.uddd.models.DiaDanh;
import nhi.uddd.services.DiaDanhService;

public class DiaDanhViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTenDiaDanh;
    public TextView txtMoTa;
    public ImageView imgHinhAnhDiaDanh;
    public String maDiaDanh;
    private DiaDanh diaDanh;
    public DiaDanhListAdapter diaDanhListAdapter;
    private DiaDanhService diaDanhService = new DiaDanhService(1);
    ImageButton btnXoa;

    public void setDiaDanh(DiaDanh diaDanh) {
        this.diaDanh = diaDanh;
    }

    public DiaDanh getDiaDanh() {
        return diaDanh;
    }

    public DiaDanhViewHolder(final View v) {
        super(v);
        txtTenDiaDanh = v.findViewById(R.id.txtTenDiaDanh);
        txtMoTa = v.findViewById(R.id.txtMoTa);
        imgHinhAnhDiaDanh = v.findViewById(R.id.imgHinhAnhDiaDanh);
        btnXoa= v.findViewById(R.id.btnXoa);

        imgHinhAnhDiaDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DiaDanhInfoActivity.class);
                intent.putExtra("diaDanh",diaDanh);
                CardView card = v.getRootView().findViewById(R.id.diaDanhInfoDetail);
                Pair pair1 = Pair.create(imgHinhAnhDiaDanh, "imageMain");
                Pair pair2 = Pair.create(card, "diaDanhInfoTrans");
                ActivityOptions activityOptionsCompat = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(), pair1, pair2);
                v.getContext().startActivity(intent, activityOptionsCompat.toBundle());
            }
        });


        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View _v) {
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle("Xóa")
                        .setMessage("Bạn muốn xóa?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                diaDanhService.xoa(diaDanh).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        diaDanhListAdapter.xoaDiaDanh(diaDanh);
                                    }
                                });
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
