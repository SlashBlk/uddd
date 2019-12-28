package nhi.uddd.activities.DiaDanhComment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nhi.uddd.R;
import nhi.uddd.activities.HomePage.DiaDanhListAdapter;
import nhi.uddd.models.Comment;
import nhi.uddd.models.DiaDanh;
import nhi.uddd.services.DiaDanhService;

public class DiaDanhCommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DiaDanhService diaDanhService;
    private DiaDanh diaDanh;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText inputNoiDungComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_danh_comment);
        mAuth= FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        diaDanhService= new DiaDanhService(1);
        recyclerView = findViewById(R.id.lstComment);
        inputNoiDungComment = findViewById(R.id.inputNoiDungComment);
        diaDanh = getIntent().getExtras().getParcelable("diaDanh");
        initRecyclerAdapter();
    }
    private void initRecyclerAdapter() {
        layoutManager = new LinearLayoutManager(this);
//        List<Comment> comments = new ArrayList<>();
//        comments.add(new Comment("Nguyễn Xuân Nhị","https://scontent.fvca1-2.fna.fbcdn.net/v/t1.0-1/c76.33.1054.1054a/s160x160/67831654_2629943697024916_9156585531625177088_o.jpg?_nc_cat=100&_nc_ohc=E5bOpDBNK4IAQmg8ItI2v-myh9YMZBkQySSrJHpcPyTO495kbK14W-JUw&_nc_ht=scontent.fvca1-2.fna&oh=0e857e326849719f0f74503954cb9d8c&oe=5E782AE0",new Date(),"Comment này hay quá! Comment này hay quá! Comment này hay quá! Comment này hay quá! Comment này hay quá!"));
//        comments.add(new Comment("Nguyễn Xuân Nhị","https://scontent.fvca1-2.fna.fbcdn.net/v/t1.0-1/c76.33.1054.1054a/s160x160/67831654_2629943697024916_9156585531625177088_o.jpg?_nc_cat=100&_nc_ohc=E5bOpDBNK4IAQmg8ItI2v-myh9YMZBkQySSrJHpcPyTO495kbK14W-JUw&_nc_ht=scontent.fvca1-2.fna&oh=0e857e326849719f0f74503954cb9d8c&oe=5E782AE0",new Date(),"Comment này hay quá!"));
//        comments.add(new Comment("Nguyễn Xuân Nhị","https://scontent.fvca1-2.fna.fbcdn.net/v/t1.0-1/c76.33.1054.1054a/s160x160/67831654_2629943697024916_9156585531625177088_o.jpg?_nc_cat=100&_nc_ohc=E5bOpDBNK4IAQmg8ItI2v-myh9YMZBkQySSrJHpcPyTO495kbK14W-JUw&_nc_ht=scontent.fvca1-2.fna&oh=0e857e326849719f0f74503954cb9d8c&oe=5E782AE0",new Date(),"Comment này hay quá!"));
        mAdapter = new CommentListAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        diaDanhService.loadDanhSachComment(mAdapter,diaDanh.getMaDiaDanh());
    }
    public void themComment(View v){
        Comment comment = new Comment();
        comment.setNgayTao(new Date());
        comment.setTenNguoiDung(currentUser.getDisplayName());
        comment.setHinhAnhNguoiDung(currentUser.getPhotoUrl().toString());
        comment.setNoiDung(inputNoiDungComment.getText().toString());
        diaDanhService.themComment(diaDanh.getMaDiaDanh(),comment);
        inputNoiDungComment.setText(null);
    }
}
