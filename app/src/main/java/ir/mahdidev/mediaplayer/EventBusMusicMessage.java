package ir.mahdidev.mediaplayer;

import java.util.List;

import ir.mahdidev.mediaplayer.model.MusicModel;

public class EventBusMusicMessage {
    private MusicModel musicModel;
    private int position;
    private List<MusicModel> musicModels;

    public EventBusMusicMessage(MusicModel musicModel, int position, List<MusicModel> musicModels) {
        this.musicModel = musicModel;
        this.position = position;
        this.musicModels = musicModels;
    }

    public MusicModel getMusicModel() {
        return musicModel;
    }

    public int getPosition() {
        return position;
    }

    public List<MusicModel> getMusicModels() {
        return musicModels;
    }
}
