//package com.ashin.vplayer.utils;
//
//import android.media.MediaDataSource;
//import android.os.Build;
//
//import androidx.annotation.RequiresApi;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.util.Properties;
//
////import jcifs.config.PropertyConfiguration;
////import jcifs.context.BaseContext;
////import jcifs.smb.SmbException;
////import jcifs.smb.SmbFile;
////import jcifs.smb.SmbRandomAccessFile;
//
//@RequiresApi(api = Build.VERSION_CODES.M)
//public class UrlMediaDataSource extends MediaDataSource {
//    String remoteUrl;
//    SmbFile smbFile;
//    BufferedInputStream stream;
//    SmbRandomAccessFile r;
//
//    public UrlMediaDataSource(String remoteUrl) throws IOException {
//        this.remoteUrl = remoteUrl;
//        Properties prop = new Properties();
//        prop.setProperty( "jcifs.smb.client.enableSMB2", "true");
//        prop.put( "jcifs.smb.client.disableSMB1", "false");
//      //  smbFile = new SmbFile(remoteUrl, new BaseContext(new PropertyConfiguration(prop)).withAnonymousCredentials());
//        r = new SmbRandomAccessFile(smbFile, "r");
//    }
//
//    @Override
//    public long getSize() throws SmbException {
//        return r.length();
//    }
//
//    @Override
//    public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
//        r.seek(position);
//        return r.read(buffer, offset, size);
//    }
//
//    @Override
//    public void close() throws IOException {
//        if (stream != null) {
//            stream.close();
//            stream = null;
//        }
//    }
//}