package nhi.uddd.activities.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import nhi.uddd.R;
import nhi.uddd.activities.ThemDiaDanh.ThemDiaDanhActivity;

public class HomePageActivity extends FragmentActivity {


    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    DiaDanhPageFragment trangDiaDanh;
    DiaDanhPageFragment trangAmThuc;
    DiaDanhPageFragment trangSuKien;
    private BottomNavigationView bottomNavigation;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView txtTenNguoiDung;
    private CircleImageView imgHinhAnhNguoiDung;
    private int currentCategory=1;
    private static final int THEM_DIA_DANH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_home_page);
            mAuth= FirebaseAuth.getInstance();
            currentUser= mAuth.getCurrentUser();

            txtTenNguoiDung= findViewById(R.id.txtTenNguoiDung);
            imgHinhAnhNguoiDung = findViewById(R.id.profile_image);

            txtTenNguoiDung.setText(currentUser.getDisplayName());
            Picasso.get().load(currentUser.getPhotoUrl()).into(imgHinhAnhNguoiDung);

            mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mPager.setOffscreenPageLimit(2);
            pagerAdapter = new DiaDanhPagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);

            bottomNavigation = findViewById(R.id.bottomNavigation);
            bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.menuDiaDanh:
                            currentCategory=1;
                            mPager.setCurrentItem(0,true);
                            return true;
                        case R.id.menuAmThuc:
                            currentCategory=2;
                            mPager.setCurrentItem(1,true);
                            return true;
                        case R.id.menuSuKien:
                            currentCategory=3;
                            mPager.setCurrentItem(2,true);
                            return true;
                    }

                    return false;
                }
            });
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            bottomNavigation.setSelectedItemId(R.id.menuDiaDanh);
                            break;
                        case 1:
                            bottomNavigation.setSelectedItemId(R.id.menuAmThuc);
                            break;
                        case 2:
                            bottomNavigation.setSelectedItemId(R.id.menuSuKien);
                            break;
                    }

                    currentCategory=position+1;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            this.trangDiaDanh=new DiaDanhPageFragment(1);
            this.trangAmThuc=new DiaDanhPageFragment(2);
            this.trangSuKien=new DiaDanhPageFragment(3);
        }
        catch(Exception e){
            e.printStackTrace();
          return;
        }


    }


    public void btnAddClick(View v){
        Intent intent = new Intent(v.getContext(), ThemDiaDanhActivity.class);
        intent.putExtra("maLoaiDiaDanh",currentCategory);
        this.startActivityForResult(intent,THEM_DIA_DANH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == THEM_DIA_DANH){
            if(resultCode ==RESULT_OK){
               int maLoaiDiaDanh = data.getIntExtra("maLoaiDiaDanh",0);
               switch (maLoaiDiaDanh)
               {
                   case 1:
                       this.trangDiaDanh.layDs();
                       break;
                   case 2:
                       this.trangAmThuc.layDs();
                       break;
                   case 3:
                       this.trangSuKien.layDs();
                       break;
               }
            }
        }
    }

    public void logOut(View v){
        mAuth.signOut();
        finish();
    }

    private class DiaDanhPagerAdapter extends FragmentStatePagerAdapter {

        public DiaDanhPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
//                    ObjectAnimator.ofInt(btnDiaDanh,"width",0,1000).setDuration(1000);
                    return trangDiaDanh;
//                    return new DiaDanhPageFragment(1);
                case 1:
//                    ObjectAnimator.ofInt(btnAmThuc,"width",0,1000).setDuration(1000);
                    return trangAmThuc;
//                    return new DiaDanhPageFragment(2);
                case 2:
//                    ObjectAnimator.ofInt(btnSuKien,"width",0,1000).setDuration(1000);
                    return trangSuKien;
//                    return new DiaDanhPageFragment(3);
                // code block
            }
            return null;
        }
        private void setWidth(Button btn, int width){
//            ViewGroup.LayoutParams params = btn.getLayoutParams();
//            params.width=width;
//            btn.setLayoutParams(params);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 1f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
//                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationY(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationY(-horzMargin + vertMargin / 2);
                }
//
//                // Scale the page down (between MIN_SCALE and 1)
//                view.setScaleX(scaleFactor);
//                view.setScaleY(scaleFactor);
//
//                // Fade the page relative to its size.
//                view.setAlpha(MIN_ALPHA +
//                        (scaleFactor - MIN_SCALE) /
//                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                // Scale nút

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
//                view.setAlpha(0f);
            }
        }

    }


}
