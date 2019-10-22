package ir.mahdidev.mediaplayer.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ir.mahdidev.mediaplayer.fragment.AlbumFragment;
import ir.mahdidev.mediaplayer.fragment.ArtistFragment;
import ir.mahdidev.mediaplayer.fragment.MusicFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments = {MusicFragment.newInstance() , AlbumFragment.newInstance() , ArtistFragment.newInstance()};
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Songs";
            case 1: return "Albums";
            case 2: return "Artists";
        }
        return super.getPageTitle(position);
    }
}
