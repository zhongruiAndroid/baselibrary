package com.library.base.tools.wx;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ShareHelper {
    /*
    * WXSceneSession = 0;会话
    * WXSceneTimeline = 1;朋友圈
    * WXSceneFavorite = 2;收藏
    * */
    public static final int friend= SendMessageToWX.Req.WXSceneSession;
    public static final int friendCircle= SendMessageToWX.Req.WXSceneTimeline;
    public static final int favorite= SendMessageToWX.Req.WXSceneFavorite;
    public static final int QQ= 12312312;
    public static final int Qzone= 123123123;

    @IntDef({friend,friendCircle,favorite,QQ,Qzone})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ParamType{};

    //分享到哪里
    public int scene;

    public void setScene(@ParamType int scene){
        this.scene=scene;
    }

    public int getScene() {
        return scene;
    }

    public static class WebHelper extends ShareHelper{
        public WebHelper(@ParamType int scene) {
            setScene(scene);
        }
        public String title;
        public String description;
        public String url;
        public Bitmap bitmap;
        public int bitmapResId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getBitmapResId() {
            return bitmapResId;
        }

        public void setBitmapResId(@DrawableRes int bitmapResId) {
            this.bitmapResId = bitmapResId;
        }
    }

    public static class VideoHelper extends WebHelper{
        public VideoHelper(@ParamType int scene) {
            super(scene);
        }
        public int dstWidth=150;
        public int dstHeight=150;
        public int getDstWidth() {
            return dstWidth;
        }
        public void setDstWidth(int dstWidth) {
            this.dstWidth = dstWidth;
        }

        public int getDstHeight() {
            return dstHeight;
        }

        public void setDstHeight(int dstHeight) {
            this.dstHeight = dstHeight;
        }
    }
    public static class ImageHelper extends ShareHelper{
        public Bitmap bitmap;
        public int bitmapResId;
        public int dstWidth=150;
        public int dstHeight=150;
        public ImageHelper(@ParamType int scene) {
            setScene(scene);
        }
        public int getDstWidth() {
            return dstWidth;
        }
        public void setDstWidth(int dstWidth) {
            this.dstWidth = dstWidth;
        }

        public int getDstHeight() {
            return dstHeight;
        }

        public void setDstHeight(int dstHeight) {
            this.dstHeight = dstHeight;
        }
        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getBitmapResId() {
            return bitmapResId;
        }

        public void setBitmapResId(int bitmapResId) {
            this.bitmapResId = bitmapResId;
        }
    }
    public static class TextHelper extends ShareHelper{
        public String title;
        public String text;
        public String description;
        public TextHelper(@ParamType int scene) {
            setScene(scene);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
