package nhi.uddd.activities.HomePage;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nhi.uddd.R;
import nhi.uddd.models.DiaDanh;
import nhi.uddd.services.DiaDanhService;


public class DiaDanhPageFragment extends Fragment {
    private int maLoaiDiaDanh;
    private RecyclerView recyclerView;
    private DiaDanhListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView txtTenLoaiDiaDanh;
    boolean isLoading;
    private DiaDanhService diaDanhService;


    public DiaDanhPageFragment(int maLoaiDiaDanh) {
        this.maLoaiDiaDanh = maLoaiDiaDanh;
        diaDanhService = new DiaDanhService(maLoaiDiaDanh);
        isLoading = false;
    }

    public int layDs() {
        try {
            mAdapter = new DiaDanhListAdapter();
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
            diaDanhService = new DiaDanhService(maLoaiDiaDanh);
            new LayDs().execute();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_dia_danh, container, false);
        diaDanhService = new DiaDanhService(maLoaiDiaDanh);
        txtTenLoaiDiaDanh = rootView.findViewById(R.id.txtTenLoaiDiaDanh);
        String tenLoaiDiaDanh = "";
        switch (this.maLoaiDiaDanh) {
            case 1:
                tenLoaiDiaDanh = "Địa danh";
                break;
            case 2:
                tenLoaiDiaDanh = "Ẩm thực";
                break;
            case 3:
                tenLoaiDiaDanh = "Sự kiện";
                break;
        }
        txtTenLoaiDiaDanh.setText(tenLoaiDiaDanh);
        recyclerView = rootView.findViewById(R.id.lstDiaDanh);
        initRecyclerAdapter(container);
        initRecyclerScrollBottom();
        return rootView;
    }

    private void initRecyclerScrollBottom() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int itemCount = mAdapter.getItemCount();
                if (!isLoading && linearLayoutManager != null && lastItemPosition == itemCount - 1) {
                    //bottom of list!
                    isLoading = true;
                    new LayDs().execute();
                }
            }
        });
    }

    private void initRecyclerAdapter(ViewGroup container) {
        layoutManager = new LinearLayoutManager(container.getContext());
        mAdapter = new DiaDanhListAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        new LayDs().execute();
    }

    class LayDs extends AsyncTask<Void, Void, Void> {
        List<DiaDanh> diaDanhs;

        public LayDs() {
            diaDanhs = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                diaDanhs = diaDanhService.layDs(2, maLoaiDiaDanh);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.addDiaDanh(this.diaDanhs);
            isLoading = false;
        }
    }

}
