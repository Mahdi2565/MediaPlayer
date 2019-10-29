package ir.mahdidev.mediaplayer.model;

import java.io.Serializable;

public class AlbumModel implements Serializable {
    private String albumName;
    private String artistOfAlbum;
    private String albumKey;
    private int numberOfSong;
    private int yearAlbum;

    public AlbumModel(String albumName, String artistOfAlbum, String albumKey, int numberOfSong, int yearAlbum) {
        this.albumName = albumName;
        this.artistOfAlbum = artistOfAlbum;
        this.albumKey = albumKey;
        this.numberOfSong = numberOfSong;
        this.yearAlbum = yearAlbum;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistOfAlbum() {
        return artistOfAlbum;
    }

    public void setArtistOfAlbum(String artistOfAlbum) {
        this.artistOfAlbum = artistOfAlbum;
    }

    public String getAlbumKey() {
        return albumKey;
    }

    public void setAlbumKey(String albumKey) {
        this.albumKey = albumKey;
    }

    public int getNumberOfSong() {
        return numberOfSong;
    }

    public void setNumberOfSong(int numberOfSong) {
        this.numberOfSong = numberOfSong;
    }

    public int getYearAlbum() {
        return yearAlbum;
    }

    public void setYearAlbum(int yearAlbum) {
        this.yearAlbum = yearAlbum;
    }
}
