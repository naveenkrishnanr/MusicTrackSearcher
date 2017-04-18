package model;

import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Created by NRamasamy on 4/17/2017.
 */

public class TrackEntity extends TableServiceEntity
{

    public TrackEntity(String track,String alb,
                       String art,String url)
    {
        Track = track;
        Album = alb;
        Artist = art;
        Url = url;

        setPartitionKey(track);
        setRowKey(UUID.randomUUID().toString());
    }

    String Track;
    String Album;
    String Artist;
    String Url;

    public String getArtist() {
        return Artist;
    }

    public void setTrack(String track) {
        Track = track;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getTrack() {
        return Track;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
