package com.ashin.vplayer.glideLea;

import com.bumptech.glide.load.model.GlideUrl;

public class PhotoData {
    private GlideUrl url;

    public PhotoData(GlideUrl url) {
        this.url = url;
    }

    public GlideUrl getUrl() {
        return url;
    }

    public void setUrl(GlideUrl url) {
        this.url = url;
    }
}
