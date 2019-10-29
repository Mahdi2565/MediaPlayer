package ir.mahdidev.mediaplayer.adapter;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ir.mahdidev.mediaplayer.EventBusMusicMessage;
import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.model.MusicModel;
import ir.mahdidev.mediaplayer.utils.Global;
import ir.mahdidev.mediaplayer.utils.PictureUtils;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {

    private List<MusicModel> musicList;
    private Context context;
    public SongRecyclerViewAdapter(List<MusicModel> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.song_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView coverMusic;
        private TextView titleMusic;
        private TextView artistMusic;
        private MaterialCardView musicCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coverMusic = itemView.findViewById(R.id.img_music);
            titleMusic = itemView.findViewById(R.id.title_music);
            artistMusic = itemView.findViewById(R.id.artist_music);
            musicCardView = itemView.findViewById(R.id.music_cardview);
        }

        public void onBind(final MusicModel musicModel) {
            titleMusic.setText(musicModel.getSongName());
            artistMusic.setText(musicModel.getArtist());
            coverMusic.setImageBitmap(PictureUtils.getCoverImage(musicModel.getId() , context));
            musicCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EventBusMusicMessage(musicModel , getAdapterPosition()
                            , musicList));
                }
            });
        }

    }

}
