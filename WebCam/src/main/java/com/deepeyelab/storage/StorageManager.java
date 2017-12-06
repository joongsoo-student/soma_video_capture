package com.deepeyelab.storage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class StorageManager {
	public static final String storageConnectionString ="DefaultEndpointsProtocol=https;AccountName=devdogs;AccountKey=AWxIi1ALGZXb3Pzq1Y+uQOanOoGx/kwOwztoLwYjyFR+IS52h1mpyIraCVHytM0MVCgy2GRLAadZby0n4wyj7g==;EndpointSuffix=core.windows.net";
		   
	public static final void upload(String oldFilePath, InputStream source, String filePath, long size) {
		try {
		    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

		    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

		    CloudBlobContainer container = blobClient.getContainerReference("devdogs");
		    
		    CloudBlockBlob blob = container.getBlockBlobReference(oldFilePath);
		    blob.deleteIfExists();
		    
		    blob = container.getBlockBlobReference(filePath);
		    blob.upload(source, size);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static final List<String> fileList(String filePath) {
		try {
		    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		    CloudBlobContainer container = blobClient.getContainerReference("devdogs");
		    Iterable<ListBlobItem> blob = container.getDirectoryReference(filePath).listBlobs();
		    
		    List<String> resultList = new ArrayList<>();
		    for(ListBlobItem item : blob) {
		    		if (item instanceof CloudBlockBlob) {
				    	CloudBlockBlob retrievedBlob = (CloudBlockBlob) item;
				    	resultList.add(retrievedBlob.getName());
			    }
		    }
		    
		    return resultList;
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}
}
