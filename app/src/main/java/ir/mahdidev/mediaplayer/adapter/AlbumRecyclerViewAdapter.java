package ir.mahdidev.mediaplayer.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.model.AlbumModel;
import ir.mahdidev.mediaplayer.model.MusicModel;
import ir.mahdidev.mediaplayer.model.Repository;
import ir.mahdidev.mediaplayer.utils.Global;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder> {

    private Repository repository = Repository.getInstance();
    private Context context;
    private List<AlbumModel> albumList;

    public AlbumRecyclerViewAdapter(Context context, List<AlbumModel> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.album_items , parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.onBind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView albumCover;
        private TextView albumName;
        private TextView artistName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.album_name);
            artistName = itemView.findViewById(R.id.artist_name);
            albumCover = itemView.findViewById(R.id.img_album);
        }
        public void onBind(AlbumModel albumModel){
            albumName.setText(albumModel.getAlbumName());
            artistName.setText(albumModel.getArtistOfAlbum());
            albumCover.setImageBitmap(getCoverImage(albumModel.getAlbumKey()));
        }

        private Bitmap getCoverImage(String albumKey){
            List<MusicModel> musicList = repository.getMusicList(albumKey);
            MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
            mediaMetadata.setDataSource(Global.getContext().getApplicationContext() ,
                    getTrackUri(musicList.get(0).getId()));
            byte [] imageByte = mediaMetadata.getEmbeddedPicture();
            if (imageByte !=null)
                return BitmapFactory.decodeByteArray(imageByte , 0 , imageByte.length);
           else return null;

        }
        private Uri getTrackUri (int id){
            return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , id);

        }
    }
}
