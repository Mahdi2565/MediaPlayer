package ir.mahdidev.mediaplayer.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.adapter.ViewPagerAdapter;

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
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager() , 0);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initViews(View v) {
        viewPager = v.findViewById(R.id.viewPager);
        tabLayout = v.findViewById(R.id.tab_Layout);
    }
}
