package com.ashin.vplayer.utils;

import android.os.SystemClock;
import android.util.Log;

import com.emc.ecs.nfsclient.nfs.io.Nfs3File;
import com.emc.ecs.nfsclient.nfs.io.NfsFileInputStream;
import com.emc.ecs.nfsclient.nfs.nfs3.Nfs3;
import com.emc.ecs.nfsclient.rpc.CredentialUnix;
import com.sun.xfile.XFile;
import com.sun.xfile.XFileInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class NetFileUtil {
    private String TAG = "NetFileUtil";

    private long time;
    //网络文件下载测试路径
    private String url = "http://cachefly.cachefly.net/100mb.test";

    public void downloadUrl(String urlStr, String fileName, String savePath) {
        URL url = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            Log.d(TAG, "info:" + url + " download success");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    // /storage/emulated/0/Download/a
    public static java.io.File createFile(String fileName) throws IOException {
        java.io.File file = new java.io.File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            Log.i("TAG", "Line ==> create status = " + file.exists());
        } else {
            Log.i("TAG", "Line ==> file is exist");
        }
        return file;
    }


    public boolean delete(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = delete(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    private CIFSContext createContext() {
        Properties prop = new Properties();

        prop.put("jcifs.smb.client.disableSMB1", "false");
        prop.put("jcifs.smb.client.enableSMB2", "true");
        prop.put("jcifs.smb.client.dfs.disabled", "true");
        //prop.put("jcifs.smb.client.useSMB2Negotiation", "false");
        //prop.put("jcifs.netbios.retryCount", "5");
        //prop.put("jcifs.netbios.retryTimeout", "5000");

        prop.put("jcifs.smb.client.rcv_buf_size", String.valueOf(512 * 1024));//seem not work..
        //prop.put("jcifs.resolveOrder", "BCAST,LMHOSTS,WINS,DNS");
        prop.put("jcifs.smb.client.ipcSigningEnforced", "false");// for guest login on win10 with smb2?
        //prop.put("jcifs.smb.useRawNTLM", "true"); // for win ?

        BaseContext cifsContext = null;
        try {
            cifsContext = new BaseContext(new PropertyConfiguration(prop));
        } catch (CIFSException e) {
            //Log.i(TAG, "Line ==> createContext, " + e);
        }
        return cifsContext;
    }

    public void downloadSmbFile() {
        CIFSContext cifsContext = createContext().withCredentials(new NtlmPasswordAuthenticator("mi", "1"));
        Log.d(TAG, "111111111111111");
        SmbFile smbFile = null;
        try {
            smbFile = new SmbFile("smb://192.168.31.240/mi/1.pdf", cifsContext);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {

            if (smbFile != null && smbFile.isFile()) {
                long start = SystemClock.currentThreadTimeMillis();
                //文件保存位置
                File saveDir = new File("/storage/emulated/0/Download/DOC");
                if (!saveDir.exists()) {
                    boolean dirIsEx = saveDir.mkdir();
                    Log.d(TAG, "saveDir is not exists ,create: " + dirIsEx);
                }
                String fileUrl = "/storage/emulated/0/Download/DOC/2.pdf";
                //Log.d(TAG, "fileUrl： " + fileUrl);
                File file = new File(fileUrl);
//                SmbFileInputStream fis = new SmbFileInputStream(smbFile);
//                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream fis = new BufferedInputStream(smbFile.getInputStream());
                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file), 1024 * 1024);
                //缓冲内存
                byte[] buffer = new byte[1024 * 1024];
//                while (fis.read(buffer) != -1) {
//                    fos.write(buffer);
//                }
                int hasRead = 0;
                Log.i(TAG, "Line ==> start to downLoad, file size = " + smbFile.getContentLengthLong() + " bytes");
                while (true) {
                    int read = fis.read(buffer, 0, buffer.length);
                    hasRead += read;
                    if (read <= 0) {
                        Log.i(TAG, "Line ==> read completed!");
                        fos.flush();//must
                        break;
                    }
                    fos.write(buffer, 0, read);
                    Log.i(TAG, "Line ==> write()  write " + read + " bytes   " + SystemClock.currentThreadTimeMillis());
                }
                long end = SystemClock.currentThreadTimeMillis();
                Log.d(TAG, "download ok use time: " + (end - start));
            }
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final String NFS_IP = "192.168.31.240";
    private static final String NFS_DIR = "/home/mi/Videos";
    //从Nfs服务器上下载指定的文件到本地目录
    public void downLoadFileFromNfs() {
        String NfsFileDir = "/1.mkv";
        String localDir = "/storage/emulated/0/Download/";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            long start = System.currentTimeMillis();
            Nfs3 nfs3 = new Nfs3(NFS_IP, NFS_DIR, new CredentialUnix(0, 0, null), 3);
            //创建远程服务器上Nfs文件对象
            Nfs3File nfsFile = new Nfs3File(nfs3, NfsFileDir);
            String localFileName = localDir + nfsFile.getName();
            //创建一个本地文件对象
            File localFile = new File(localFileName);
            //打开一个文件输入流
            inputStream = new NfsFileInputStream(nfsFile);
            //打开一个远程Nfs文件输出流，将文件复制到的目的地
            outputStream = new FileOutputStream(localFile);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            //缓冲内存
            byte[] buffer = new byte[1024 * 1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                Log.d(TAG, String.valueOf(System.currentTimeMillis()));
                bufferedOutputStream.write(buffer, 0, len);
            }
            long end = System.currentTimeMillis();
            time = end - start;
            Log.d(TAG, "文件下载完成！" + "   执行时间" + time);
            if (downloadFinListener != null) {
                downloadFinListener.onDownloadFinish(time);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void downFileByYaNfs() {
        Log.d(TAG,"downFileByYaNfs");
        String localDir = "/storage/emulated/0/Download/";
        OutputStream outputStream = null;
        XFileInputStream xFileInputStream = null;
        try {
            long start = System.currentTimeMillis();
            XFile xFile = new XFile("nfs://" + "192.168.31.240" + "/home/mi/Videos/Moana.mp4");
            String localFileName = localDir + xFile.getName();
            //创建一个本地文件对象
            File localFile = new File(localFileName);
            xFileInputStream = new XFileInputStream(xFile);
            outputStream = new FileOutputStream(localFile);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(xFileInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            //缓冲内存
            byte[] buffer = new byte[1024 * 1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                Log.d(TAG, String.valueOf(System.currentTimeMillis()));
                bufferedOutputStream.write(buffer, 0, len);
            }
            long end = System.currentTimeMillis();
            time = end - start;
            Log.d(TAG, "文件下载完成！" + "   执行时间" + time);
            if (downloadFinListener != null) {
                downloadFinListener.onDownloadFinish(time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DownloadFinListener downloadFinListener;

    public interface DownloadFinListener {
        void onDownloadFinish(long time);
    }

    public void setDownloadFinListener(DownloadFinListener downloadFinListener) {
        this.downloadFinListener = downloadFinListener;
    }
}
