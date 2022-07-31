package com.ashin.vplayer.services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ashin.vplayer.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class SmbActivity extends AppCompatActivity {
    private String TAG = "SmbAct";
    private String tempDir = "/home/mi/share";
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smb);
    }

    public void downloadViaShare(final String ip, final String user, final String password, final String dir) {
        Log.d(TAG, "Share(SMB) download!");

        String newDir = dir;
        String url = "";
        SmbFile[] fileList = null;
        FileOutputStream fos = null;
        SmbFileInputStream smbIs = null;
        byte[] buffer = new byte[8192];
        int readBytes = 0;
        int totalBytes = 0;

        if (!dir.endsWith("/"))  //directory must end with "/"        
            newDir = dir + "/";
        url = "smb://" + user + ":" + password + "@" + ip + "/" + newDir;

        long startTime = System.currentTimeMillis();
        try {
            SmbFile shareDir = new SmbFile(url);
            if (shareDir.isDirectory()) {
                fileList = shareDir.listFiles();
                for (SmbFile smbFile : fileList) {
                    if (smbFile.isFile()) {
                        smbIs = new SmbFileInputStream((SmbFile) smbFile);
                        fos = new FileOutputStream(new File(tempDir + File.separator + smbFile.getName()));
                        while ((readBytes = smbIs.read(buffer)) > 0) {
                            fos.write(buffer, 0, readBytes);
                            totalBytes += readBytes;
                        }
                        smbIs.close();
                        fos.close();
                        Log.d(TAG, smbFile.getName() + " is downloaded!");
                        try {
                            smbFile.delete();
                        } catch (SmbAuthException smbae) {
                            Log.d(TAG, smbFile.getName() + " can not be deleted！");
                        }
                    }
                }
                long endTime = System.currentTimeMillis();
                long timeTaken = endTime - startTime;
                Log.d(TAG, totalBytes + "bytes downloaded in " + timeTaken / 1000 + " seconds at " + ((totalBytes / 1000) / Math.max(1, (timeTaken / 1000))) + "Kb/sec");
            }
        } catch (MalformedURLException urle) {
            Log.d(TAG, "Incorrect URL format!");
        } catch (IOException smbe) {
            smbe.printStackTrace();
            Log.d(TAG, this.getClass().getName() + "||" + smbe.getMessage());
        } finally {
            try {
                smbIs.close();
                fos.close();
            } catch (Exception smbe) {
                Log.d(TAG, this.getClass().getName() + "||" + smbe.getMessage());
            }
        }

    }

}