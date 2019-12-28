package nhi.uddd.activities.ThemDiaDanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import nhi.uddd.R;
import nhi.uddd.models.DiaDanh;
import nhi.uddd.services.DiaDanhService;

public class ThemDiaDanhActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    ImageView imgHinhAnhDiaDanh;
    EditText txtTenDiaDanh;
    EditText txtMoTa;
    DiaDanhService diaDanhService;
    TextView txtTenLoaiDiaDanh;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Uri imageUri;
    private int maLoaiDiaDanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_dia_danh);
        this.maLoaiDiaDanh=this.getIntent().getIntExtra("maLoaiDiaDanh",0);
        imgHinhAnhDiaDanh = findViewById(R.id.imgHinhAnhDiaDanh);
        txtTenDiaDanh = findViewById(R.id.txtTenDiaDanh);
        txtTenLoaiDiaDanh = findViewById(R.id.txtTenLoaiDiaDanh);
        txtMoTa = findViewById(R.id.txtMoTa);
        diaDanhService = new DiaDanhService(1);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        txtTenLoaiDiaDanh.setText(getTenDiaDanh());
    }
    String getTenDiaDanh(){
        switch (this.maLoaiDiaDanh) {
            case 1:
                return "Địa danh";
            case 2:
                return  "Ẩm thực";
            case 3:
                return  "Sự kiện";
        }
        return "";
    }

    public void chonHinh(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void themDiaDanh(final View v){
        final DiaDanh diaDanh = new DiaDanh();
        diaDanh.setTenDiaDanh(txtTenDiaDanh.getText().toString());
        diaDanh.setMoTa(txtMoTa.getText().toString());
        diaDanh.setNgayTao(new Date());
        diaDanh.setMaLoaiDiaDanh(this.maLoaiDiaDanh);
        StorageReference storageRef = storage.getReference();
        final StorageReference imagesRef = storageRef.child(currentUser.getUid()).child(new Date().getTime()+"");
        Task<Uri> uploadTask = imagesRef.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imagesRef.getDownloadUrl();
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                diaDanh.setHinhAnh(uri.toString());
                diaDanhService.luu(diaDanh).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast toast = Toast.makeText(v.getContext(),"Thêm địa danh thành công", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(v.getContext(),ThemDiaDanhActivity.class);
                            intent.putExtra("maLoaiDiaDanh",maLoaiDiaDanh);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(v.getContext(),"Thêm địa danh thất bại", Toast.LENGTH_SHORT);
                            toast.show();
                            Log.d("ThemDiaDanh", "onComplete: "+task.getException());
                        }
                    }
                });
            }
        });



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    imgHinhAnhDiaDanh.setMinimumHeight(0);
                    imgHinhAnhDiaDanh.setImageURI(uri);
                }
            } else if (data.getData() != null) {
                imageUri = data.getData();
                imgHinhAnhDiaDanh.setMinimumHeight(0);
                imgHinhAnhDiaDanh.setImageURI(imageUri);
            }
        }
    }
}
