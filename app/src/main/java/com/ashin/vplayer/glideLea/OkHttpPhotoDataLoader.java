package com.ashin.vplayer.glideLea;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class OkHttpPhotoDataLoader implements ModelLoader<PhotoData, InputStream> {

    private OkHttpClient okHttpClient;



    public static class Factory implements ModelLoaderFactory<PhotoData, InputStream> {

        private OkHttpClient client;

        public Factory() {
        }

        public Factory(OkHttpClient client) {
            this.client = client;
        }

        private synchronized OkHttpClient getOkHttpClient() {
            if (client == null) {
                client = new OkHttpClient();
            }
            return client;
        }

        @Override
        public ModelLoader<PhotoData, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OkHttpPhotoDataLoader(getOkHttpClient());
        }

        @Override
        public void teardown() {
        }
    }

    public OkHttpPhotoDataLoader(OkHttpClient client) {
        this.okHttpClient = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(PhotoData model, int width, int height) {
        return new OkHttpPhotoDataFetcher(okHttpClient, model);
    }
}
