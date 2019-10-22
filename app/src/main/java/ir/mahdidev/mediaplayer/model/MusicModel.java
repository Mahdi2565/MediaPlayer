package ir.mahdidev.mediaplayer.model;

public class MusicModel {
    private int id;
    private int year;
    private String songName;
    private String album;
    private String artist;
    private String albumKey;

    public MusicModel(int id, int year, String songName, String album, String artist, String albumKey) {
        this.id = id;
        this.year = year;
        this.songName = songName;
        this.album = album;
        this.artist = artist;
        this.albumKey = albumKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumKey() {
        return albumKey;
    }

    public void setAlbumKey(String albumKey) {
        this.albumKey = albumKey;
    }
}
