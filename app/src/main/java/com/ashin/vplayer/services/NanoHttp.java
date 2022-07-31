package com.ashin.vplayer.services;


import android.util.Log;

import com.sun.xfile.XFile;
import com.sun.xfile.XFileInputStream;

import com.ashin.vplayer.utils.Util;
import com.emc.ecs.nfsclient.nfs.io.Nfs3File;
import com.emc.ecs.nfsclient.nfs.io.NfsFileInputStream;
import com.emc.ecs.nfsclient.nfs.nfs3.Nfs3;
import com.emc.ecs.nfsclient.rpc.CredentialUnix;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class NanoHttp extends NanoHTTPD {
    public static final int DEFAULT_SERVER_PORT = 25757;//端口号
    private static final String NFS_IP = "192.168.31.240";
    private static final String NFS_DIR = "/home/mi/Videos";
    private String TAG = "NanoHttp";
    Nfs3File nfsFile;

    public NanoHttp() throws IOException {
        super(DEFAULT_SERVER_PORT);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        Log.d(TAG, "启动成功");
    }

    @Override
    public Response handle(IHTTPSession session) {
        String uri = session.getUri();
        String NfsFileDir = "/b/b.mkv";
        InputStream inputStream = null;
        try {
            String park="nfs://" + "192.168.31.240" + "/media/mi/581B65F3EBFAC14D/测试资源/[4k延时摄影之国家公园]TimeScapes.2012.2304p.CineForm.x264-WiKi(4096x2304).mkv";
            String crowd="nfs://" + "192.168.31.240" + "/media/mi/581B65F3EBFAC14D/测试资源/4K-H264-60fps/[4KH264_50.000fps_114Mbps_8bit]CrowdRun_2160p50.x264.CRF24.mkv";
            String ride="nfs://" + "192.168.31.240" + "/media/mi/581B65F3EBFAC14D/测试资源/4K-H265-10bit/[4KH265_59.940fps_10bit]03.UHD_RideOnBoard_Chinese(皇帝位使用）.ts";
//[4k延时摄影之国家公园]TimeScapes.2012.2304p.CineForm.x264-WiKi(4096x2304).mkv
            XFile xFile = new XFile(park);
            long l = xFile.lastModified();
            Log.d(TAG, "lastModified: " + l);
            inputStream = new XFileInputStream(xFile);
            long length = xFile.length();


//            Nfs3 nfs3 = new Nfs3(NFS_IP, NFS_DIR, new CredentialUnix(0, 0, null), 3);
//            nfsFile = new Nfs3File(nfs3, NfsFileDir);
//            inputStream = new NfsFileInputStream(nfsFile, 4 * 1024 * 1024);
//            long length = nfsFile.length();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1024 * 1024);
            Log.d(TAG, "length: " + length);
            String rangHeader = readRequestHeaders(session);
            long[] range = Util.getRange(rangHeader, length);
            if (range.length == 2) { //must
                return getPartialResponse(bufferedInputStream, range, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response res = Response.newChunkedResponse(Status.OK, "*", inputStream);
        res.addHeader("Connection", "close");
        return res;
    }

    private Response getPartialResponse(InputStream videoStream, long[] range, long sourceLength) {
        long skipLen = range[0];
        Log.i(TAG, "Line ==> skip len = " + skipLen);
        if (skipLen != 0) {
            try {
                Log.i(TAG, "Line ==> start skip");
                videoStream.skip(skipLen);
                Log.i(TAG, "Line ==> end skip");
            } catch (IOException e) {
                Log.i(TAG, "Line ==> " + e);
            }
        }

        Response response = Response.newFixedLengthResponse(Status.PARTIAL_CONTENT, "video/*", videoStream, sourceLength);

        response.addHeader("Accept-Ranges", "bytes");
        response.addHeader("Content-Range", "bytes " + range[0] + "-" + range[1] + "/" + sourceLength);
        response.addHeader("Content-Length", String.valueOf((range[1] - range[0]) + 1));
        response.addHeader("Connection", "keep-alive");

        return response;
    }

    private String readRequestHeaders(IHTTPSession session) {
        if (session == null) {
            return null;
        }
        String result = null;
        Log.i(TAG, "Line ==> --------------------request header start! ");
        Log.i(TAG, "Line ==> |method: " + session.getMethod());
        Map<String, String> headers = session.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            Log.i(TAG, "Line ==> |" + entry.getKey() + ":" + entry.getValue());
            if (entry.getKey().equalsIgnoreCase("range")) {
                result = entry.getValue();
            }
        }
        Log.i(TAG, "Line ==> --------------------request header end! ");
        return result;
    }


}
