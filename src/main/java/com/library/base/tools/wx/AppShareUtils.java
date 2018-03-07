package com.library.base.tools.wx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.androidtools.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;


/**
 * Created by Administrator on 2018/3/7.
 */

public class AppShareUtils {


    /*IWXAPI api
      api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);*/
    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 分享视频
     * @param context
     * @param api
     * @param helper
     */
    public static void WXVideo(Context context, IWXAPI api, ShareHelper.WebHelper helper) {
        if(notShare(context,api)){
            return;
        }
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();
        Bitmap bmp;
        if (helper.getBitmap() == null) {
            bmp = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bmp = helper.getBitmap();
        }
        msg.thumbData = Util.bmpToByteArray(bmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = helper.getScene();
        api.sendReq(req);
    }

    /**
     * 分享视频
     * @param context
     * @param api
     * @param helper
     */
    public static void WXVideo(Context context, IWXAPI api, ShareHelper.VideoHelper helper) {
        if(notShare(context,api)){
            return;
        }
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();
        Bitmap bmp;
        if (helper.getBitmap() == null) {
            bmp = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bmp = helper.getBitmap();
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, helper.getDstWidth(), helper.getDstHeight(), true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = helper.getScene();
        api.sendReq(req);
    }

    /**
     * 分享网页
     * @param context
     * @param api
     * @param helper
     */
    public static void WXWeb(Context context, IWXAPI api, ShareHelper.WebHelper helper) {
        if(notShare(context,api)){
            return;
        }
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage(wxWebpageObject);
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();
        Bitmap bitmap;
        if (helper.getBitmap() == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bitmap = helper.getBitmap();
        }
        msg.thumbData = Util.bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webPage");
        req.message = msg;
        /*
        * WXSceneSession = 0;会话
        * WXSceneTimeline = 1;朋友圈
        * WXSceneFavorite = 2;收藏
        * */
        req.scene = helper.getScene();
        api.sendReq(req);
    }

    /**
     * 分享音乐
     * @param context
     * @param api
     * @param helper
     */
    public static void WXMusic(Context context, IWXAPI api, ShareHelper.WebHelper helper) {
        if(notShare(context,api)){
            return;
        }
        WXMusicObject music = new WXMusicObject();
        music.musicUrl=helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();

        Bitmap bitmap;
        if (helper.getBitmap() == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bitmap = helper.getBitmap();
        }

        msg.thumbData = Util.bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = helper.getScene();
        api.sendReq(req);
    }

    /**
     * 分享音乐
     * @param context
     * @param api
     * @param helper
     */
    public static void WXMusic(Context context, IWXAPI api, ShareHelper.VideoHelper helper) {
        if(notShare(context,api)){
            return;
        }
        WXMusicObject music = new WXMusicObject();
        music.musicUrl=helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();

        Bitmap bmp;
        if (helper.getBitmap() == null) {
            bmp = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bmp = helper.getBitmap();
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, helper.getDstWidth(), helper.getDstHeight(), true);
        bmp.recycle();

        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = helper.getScene();
        api.sendReq(req);
    }

    /**
     * 分享图片
     * @param context
     * @param api
     * @param helper
     */
    public static void WXImage(Context context, IWXAPI api, ShareHelper.ImageHelper helper) {
        if(notShare(context,api)){
            return;
        }
        Bitmap bmp;
        if(helper.getBitmap()==null){
            bmp = BitmapFactory.decodeResource(context.getResources(),helper.getBitmapResId());
        }else{
            bmp=helper.getBitmap();
        }
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, helper.getDstWidth(), helper.getDstHeight(), true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("image");
        req.message = msg;
        req.scene = helper.getScene();
        api.sendReq(req);
    }

    /**
     * 分享文本
     * @param api
     * @param helper
     */
    public static void WXText(Context context,IWXAPI api, ShareHelper.TextHelper helper) {
        if(notShare(context,api)){
            return;
        }
        WXTextObject textObj = new WXTextObject();
        textObj.text = helper.getText();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;

        if(helper.getTitle()!=null){
            msg.title = helper.getTitle();
        }
        msg.description = helper.getDescription();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = helper.getScene();

        api.sendReq(req);
    }
    private static boolean notShare(Context context,IWXAPI api){
        /* boolean b = api.registerApp(app_id);
        if(b==false){
            Toast.makeText(mContext,"注册微信失败，无法打开微信!",Toast.LENGTH_SHORT).show();
            return false;
        }*/
        boolean notShare=false;
        if (!api.isWXAppInstalled()) {
            ToastUtils.showToast(context,"亲,您还没有安装微信APP哦!");
            notShare= true;
        } else {
            if (!api.isWXAppSupportAPI()) {
                ToastUtils.showToast(context,"亲,当前版本不支持微信相关功能!");
                notShare= true;
            }
        }
        return notShare;
    }
}
