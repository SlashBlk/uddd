package nhi.uddd.activities.DiaDanhInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nhi.uddd.R;
import nhi.uddd.activities.DiaDanhComment.DiaDanhCommentActivity;
import nhi.uddd.models.DiaDanh;

public class DiaDanhInfoActivity extends AppCompatActivity {
    ImageView imgHinhAnhDiaDanh;
    TextView txtMoTa;
    TextView txtTenDiaDanh;
    DiaDanh diaDanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaDanh= getIntent().getExtras().getParcelable("diaDanh");
        setContentView(R.layout.activity_dia_danh_info);

        imgHinhAnhDiaDanh = findViewById(R.id.imgHinhAnhDiaDanh);
        txtMoTa = findViewById(R.id.txtMoTa);
        txtTenDiaDanh= findViewById(R.id.txtTenDiaDanh);

        Picasso.get().load(diaDanh.getHinhAnh()).into(imgHinhAnhDiaDanh);
        txtMoTa.setText(diaDanh.getMoTa());
        txtTenDiaDanh.setText(diaDanh.getTenDiaDanh());
    }
    public void chuyenTrangComment(View v){
        Intent intent = new Intent(this, DiaDanhCommentActivity.class);
        intent.putExtra("diaDanh",diaDanh);
        this.startActivity ( intent );
    }
}
