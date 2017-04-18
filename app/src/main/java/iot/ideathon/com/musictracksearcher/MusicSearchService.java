package iot.ideathon.com.musictracksearcher;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import model.TrackInfo;
import okhttp3.OkHttpClient;

/**
 * Created by NRamasamy on 4/17/2017.
 */

public class MusicSearchService
{

    TrackInfo _trackInfo;
    final OkHttpClient client = new OkHttpClient();

    final String URL = "";

    public MusicSearchService()
    {

    }

    public void StartSearching(Context context, String trackInfo)
    {
        _trackInfo = new GsonBuilder().create().fromJson(trackInfo,TrackInfo.class);

        Log.d("BG",_trackInfo.getTrack());

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA
        };


        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,null,null,null);

        try {

            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String displayName = cursor.getString(1);
                String artist = cursor.getString(2);
                String album = cursor.getString(3);
                String path = cursor.getString(4);

                if (name.contains(_trackInfo.getTrack()) || displayName.contains(_trackInfo.getTrack()))
                {
                    Log.d("BG", path);
                    uploadAudioFile(path);
                    return;
                }

//                if (artist.contains(_trackInfo.getArtist())) {
//                    uploadAudioFile(path);
//                    return;
//                }
//
//                if (album.contains(_trackInfo.getAlbum())) {
//                    uploadAudioFile(path);
//                    return;
//                }
            }
        }
        catch (IOException e)
        {
            Log.e("BG",e.getMessage());
        }
    }

    private void uploadAudioFile(String path) throws IOException
    {
        AzureStorageHelper azureStorageHelper = new AzureStorageHelper(_trackInfo,path);
        azureStorageHelper.execute();
    }
}
