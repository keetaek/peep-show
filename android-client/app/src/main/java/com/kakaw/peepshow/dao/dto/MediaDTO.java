package com.kakaw.peepshow.dao.dto;

/**
 *
 * "media": [
        {
            "type": "image/png",
            "url": "http: //s3.aws.amazons.com/peepshow/drops/10231/image1.png",
            "title": "Beautiful image title"
        }
    ]
 */
public class MediaDTO {

    private String type;
    private String url;
    private String title;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
