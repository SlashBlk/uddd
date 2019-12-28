package nhi.uddd.activities.DiaDanhComment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nhi.uddd.R;
import nhi.uddd.models.Comment;

public class CommentListAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private List<Comment> comments;

    public CommentListAdapter() {
        this.comments = new ArrayList<>();
    }

    public CommentListAdapter(List<Comment> comments){
        this.comments=comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.notifyDataSetChanged();
    }

    public List<Comment> getComments() {
        return comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view_holder, parent, false);
        CommentViewHolder viewHolder = new CommentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.txtTenNguoiDung.setText(comment.getTenNguoiDung());
        holder.txtNoiDung.setText(comment.getNoiDung());
        holder.txtNgayTao.setText(format.format(comment.getNgayTao()));
        String urlHinhAnhNguoiDung = comment.getHinhAnhNguoiDung();
        Picasso.get().load(urlHinhAnhNguoiDung).into(holder.imgHinhAnhNguoiDung);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
