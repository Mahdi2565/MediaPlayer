package ir.mahdidev.mediaplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.model.ArtistModel;

public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ViewHolder> {

    private List<ArtistModel> artistList;
    private Context context;

    public ArtistRecyclerViewAdapter(List<ArtistModel> artistList, Context context) {
        this.artistList = artistList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.artist_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.onBind(artistList.get(position));
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleArtist;
        private TextView albumCount;
        private TextView musicCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleArtist = itemView.findViewById(R.id.title_artist);
            albumCount  = itemView.findViewById(R.id.album_count);
            musicCount = itemView.findViewById(R.id.music_count);
        }
        public void onBind(ArtistModel artistModel){
            String albumCountTxt = artistModel.getNumberOfAlbum() + " Album";
            String musicCountTxt = artistModel.getNumberOfTrack() + " Song";
            titleArtist.setText(artistModel.getArtistName());
            albumCount.setText(albumCountTxt);
            musicCount.setText(musicCountTxt);
        }
    }
}
