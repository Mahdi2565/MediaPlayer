package ir.mahdidev.mediaplayer.model;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ir.mahdidev.mediaplayer.utils.Global;

public class Repository {
    public static Repository repository;

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private List<MusicModel> musicList;
    private List<AlbumModel> albumList;
    private List<ArtistModel> artistList;

    public List<MusicModel> getMusicList() {
        musicList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.TITLE
                , MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM_KEY,
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.YEAR
        };
        Cursor cursor = Global.getContext().getContentResolver().query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            {
                int musicId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID));
                int year = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR));
                String trackName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
                String trackAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
                String trackArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
                String albumKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY));
                musicList.add(new MusicModel(musicId, year, trackName, trackAlbum, trackArtist, albumKey));
            }
        }
        cursor.close();
        return musicList;
    }

    public List<MusicModel> getMusicList(String mAlbumKey) {
        musicList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.TITLE
                , MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM_KEY,
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.YEAR
        };
        Cursor cursor = Global.getContext().getContentResolver().query(uri, projection
                , MediaStore.Audio.AudioColumns.ALBUM_KEY + "=?", new String[]{mAlbumKey}, null);
        while (cursor.moveToNext()) {
            {
                int musicId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID));
                int year = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR));
                String trackName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
                String trackAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
                String trackArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
                String albumKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY));
                musicList.add(new MusicModel(musicId, year, trackName, trackAlbum, trackArtist, albumKey));
            }
        }
        cursor.close();
        return musicList;
    }


    public List<AlbumModel> getAlbumList() {
        albumList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AlbumColumns.ALBUM
                , MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS,
                MediaStore.Audio.AlbumColumns.FIRST_YEAR,
                MediaStore.Audio.AlbumColumns.ALBUM_KEY,
                MediaStore.Audio.AlbumColumns.ARTIST};
        Cursor cursor = Global.getContext().getContentResolver().query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM));
            String artistOfAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST));
            String albumKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_KEY));
            int year = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.FIRST_YEAR));
            int numberOfSongs = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS));
            albumList.add(new AlbumModel(albumName, artistOfAlbum, albumKey, numberOfSongs, year));
        }
        cursor.close();
        return albumList;
    }

    public List<ArtistModel> getArtistList() {
        artistList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.ArtistColumns.ARTIST
                , MediaStore.Audio.ArtistColumns.ARTIST_KEY,
                MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS
                , MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS
        };
        Cursor cursor = Global.getContext().getContentResolver().query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.ARTIST));
            String artistKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.ARTIST_KEY));
            int numberOfAlbum = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS));
            int numberOfTrack = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS));
            artistList.add(new ArtistModel(artistName, artistKey, numberOfAlbum, numberOfTrack));
        }
        cursor.close();
        return artistList;
    }

}
