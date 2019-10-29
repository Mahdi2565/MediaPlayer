package ir.mahdidev.mediaplayer.model;

import java.io.Serializable;

public class ArtistModel implements Serializable {
    private String artistName;
    private String artistKey;
    private int numberOfAlbum;
    private int numberOfTrack;

    public ArtistModel(String artistName, String artistKey, int numberOfAlbum, int numberOfTrack) {
        this.artistName = artistName;
        this.artistKey = artistKey;
        this.numberOfAlbum = numberOfAlbum;
        this.numberOfTrack = numberOfTrack;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistKey() {
        return artistKey;
    }

    public void setArtistKey(String artistKey) {
        this.artistKey = artistKey;
    }

    public int getNumberOfAlbum() {
        return numberOfAlbum;
    }

    public void setNumberOfAlbum(int numberOfAlbum) {
        this.numberOfAlbum = numberOfAlbum;
    }

    public int getNumberOfTrack() {
        return numberOfTrack;
    }

    public void setNumberOfTrack(int numberOfTrack) {
        this.numberOfTrack = numberOfTrack;
    }
}
