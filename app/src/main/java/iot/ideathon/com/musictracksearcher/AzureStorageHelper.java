package iot.ideathon.com.musictracksearcher;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

import java.io.File;
import java.io.FileInputStream;

import model.TrackEntity;
import model.TrackInfo;

/**
 * Created by NRamasamy on 4/17/2017.
 */

public class AzureStorageHelper extends AsyncTask<Void,Void,Void>
{

    /**
     * MODIFY THIS!
     *
     * Stores the storage connection string.
     */
    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
            + "AccountName=songs;"
            + "AccountKey=jtyF+pByN1sjnaenMAIhPV1eQ2qWNdOrO+Ih3TrXJroEfy7AOEBQirRPsz8bfa9a//WfnkGdi22DJql3MajaVQ==";

    TrackInfo _trackInfo;
    String _filePath;

    public AzureStorageHelper(TrackInfo trackInfo,String filePath)
    {
        _trackInfo = trackInfo;
        _filePath = filePath;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Get a reference to a container.
            // The container name must be lower case
            CloudBlobContainer container = blobClient.getContainerReference("songs");

            // Create the container if it does not exist.
            container.createIfNotExists();

            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Include public access in the permissions object.
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

            // Set the permissions on the container.
            container.uploadPermissions(containerPermissions);

            // Define the path to a local file.
            final String filePath = _filePath;

            // Create or overwrite the "myimage.jpg" blob with contents from a local file.
            CloudBlockBlob blob = container.getBlockBlobReference(_trackInfo.getTrack()+".mp3");
            File source = new File(filePath);
            blob.upload(new FileInputStream(source), source.length());


            String trackUrl = "https://songs.core.windows.net/songs/" +_trackInfo.getTrack()+".mp3";

            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            // Create a cloud table object for the table.
            CloudTable cloudTable = tableClient.getTableReference("songs");

            // Create a new customer entity.
            TrackEntity trackEntity = new TrackEntity(_trackInfo.getTrack(), _trackInfo.getAlbum(),
                                                _trackInfo.getAlbum(),trackUrl);

            // Create an operation to add the new customer to the people table.
            TableOperation track = TableOperation.insertOrReplace(trackEntity);

            // Submit the operation to the table service.
            cloudTable.execute(track);
        }
        catch (Exception e)
        {
            // Output the stack trace.
            Log.d("BG",e.getMessage());

            return null;
        }

        return null;
    }
}
