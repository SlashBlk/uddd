package nhi.uddd.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

import nhi.uddd.activities.DiaDanhComment.CommentListAdapter;
import nhi.uddd.models.Comment;
import nhi.uddd.models.DiaDanh;

public class DiaDanhService {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query layDs;
    CollectionReference diaDanhs;
    int maLoaiDiaDanh;

    public DiaDanhService(int maLoaiDiaDanh) {
        this.diaDanhs = db.collection("diaDanhs");
        this.maLoaiDiaDanh = maLoaiDiaDanh;
        this.layDs = diaDanhs.whereEqualTo("maLoaiDiaDanh", this.maLoaiDiaDanh)
                .orderBy("ngayTao", Query.Direction.DESCENDING);
    }

    public List<DiaDanh> layDs(final int pageSize, final int maLoaiDiaDanh) throws InterruptedException, ExecutionException {
        this.layDs = this.layDs.limit(pageSize);
        final List<DiaDanh> result = new ArrayList<>();
        Task task = this.layDs.get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                Map<String, Object> data = document.getData();
                                DiaDanh diaDanh = new DiaDanh();
                                diaDanh.setMaDiaDanh(document.getId());
                                diaDanh.setHinhAnh((String) data.get("hinhAnh"));
                                diaDanh.setMoTa((String) data.get("moTa"));
                                diaDanh.setTenDiaDanh((String) data.get("tenDiaDanh"));
                                result.add(diaDanh);
                            }
                            List<DocumentSnapshot> docs = querySnapshot.getDocuments();
                            if (docs.size() > 0) {
                                DocumentSnapshot lastItem = docs.get(docs.size() - 1);
                                layDs = diaDanhs.whereEqualTo("maLoaiDiaDanh", maLoaiDiaDanh)
                                        .orderBy("ngayTao", Query.Direction.DESCENDING).startAfter(lastItem);
                            }
                        } else {
                            Exception e = task.getException();
                            e.printStackTrace();
                        }
                    }
                }
        );
        Tasks.await(task);
        return result;
    }

    public void loadDanhSachComment(final CommentListAdapter adapter, String maDiaDanh) {
        db.collection("diaDanhs/" + maDiaDanh + "/comments").orderBy("ngayTao", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        List<Comment> comments = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Comment comment = new Comment();
                            comment.setHinhAnhNguoiDung((String) doc.getData().get("hinhAnhNguoiDung"));
                            comment.setNoiDung((String) doc.getData().get("noiDung"));
                            comment.setTenNguoiDung((String) doc.getData().get("tenNguoiDung"));
                            comment.setNgayTao(((Timestamp) doc.getData().get("ngayTao")).toDate());
                            comments.add(comment);
                        }
                        adapter.setComments(comments);
                    }
                });
    }

    public Task<Void> luu(DiaDanh diaDanh) {

        Map<String, Object> _diaDanh = new HashMap<>();
        _diaDanh.put("hinhAnh", diaDanh.getHinhAnh());
        _diaDanh.put("maLoaiDiaDanh", diaDanh.getMaLoaiDiaDanh());
        _diaDanh.put("moTa", diaDanh.getMoTa());
        _diaDanh.put("tenDiaDanh", diaDanh.getTenDiaDanh());
        _diaDanh.put("ngayTao",diaDanh.getNgayTao());

        return db.collection("diaDanhs/").document()
                .set(_diaDanh);
    }

    public static Task<Void> xoa(DiaDanh diaDanh) {
        return db.document("diaDanhs/"+diaDanh.getMaDiaDanh()).delete();
    }

    public void themComment(String maDiaDanh, Comment comment) {
        Map<String, Object> _comment = new HashMap<>();
        _comment.put("hinhAnhNguoiDung", comment.getHinhAnhNguoiDung());
        _comment.put("noiDung", comment.getNoiDung());
        _comment.put("ngayTao", comment.getNgayTao());
        _comment.put("tenNguoiDung", comment.getTenNguoiDung());

        db.collection("diaDanhs/" + maDiaDanh + "/comments").document()
                .set(_comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Thêm comment", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Thêm comment", "Error writing document", e);
                    }
                });
    }
}
