package ir.mahdidev.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.mahdidev.mediaplayer.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout , new MainFragment())
        .commit();
    }
}
