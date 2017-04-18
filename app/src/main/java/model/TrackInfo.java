package model;

/**
 * Created by NRamasamy on 4/17/2017.
 */

public class TrackInfo
{
    String track;
    String album;
    String artist;

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getTrack() {
        return track;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
