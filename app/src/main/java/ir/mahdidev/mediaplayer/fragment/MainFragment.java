package ir.mahdidev.mediaplayer.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.adapter.ViewPagerAdapter;
import ir.mahdidev.mediaplayer.utils.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initViewPager();
    }

    private void initViewPager() {
        if (checkPermission()){
            viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager() , 0);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOffscreenPageLimit(3);
        }else checkPermission();
    }

    private void initViews(View v) {
        viewPager = v.findViewById(R.id.viewPager);
        tabLayout = v.findViewById(R.id.tab_Layout);
    }

    private boolean checkPermission (){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M) return true;
        if (ActivityCompat.checkSelfPermission(getActivity() , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , Const.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
            return false;
        }else return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == Const.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION){
                initViewPager();
            }
        }else checkPermission();
    }
}
