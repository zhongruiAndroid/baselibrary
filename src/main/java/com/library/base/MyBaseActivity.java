package com.library.base;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.ClickUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.StatusBarUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerListItem;
import com.github.baseclass.activity.IBaseActivity;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.MyLoadMoreAdapter;
import com.github.baseclass.permission.PermissionCallback;
import com.github.baseclass.view.MyDialog;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.library.BuildConfig;
import com.library.R;
import com.library.base.tools.CacheUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.FlowableEmitter;
import io.reactivex.annotations.NonNull;


/**
 * Created by Administrator on 2017/6/1.
 */

public abstract class MyBaseActivity extends IBaseActivity implements ProgressLayout.OnAgainInter, View.OnClickListener, LoadMoreAdapter.OnLoadMoreListener,MyLoadMoreAdapter.OnLoadMoreListener {
    protected final String TAG=this.getClass().getSimpleName();
    /*************************************************/
    protected NestedScrollView nsv;
    protected Toolbar toolbar;
    private boolean showNavigationIcon = true;
    private int navigationIcon = -1;
    protected int pageNum = 2;
    protected int pageSize = 20;
    protected int pagesize = 20;
    private String appTitle, appRightTitle;
    private int appTitleColor, appRightTitleColor;
    private int appRightImg;
    //临时变量处理分享
    protected boolean isShareImg;
    private int titleBackgroud = R.color.app_bar;
//    private int statusBarBackgroud = R.color.app_bar;
    protected TextView app_title, app_right_tv;
    protected ImageView app_right_iv;
    private View status_bar, v_bottom_line;
    private boolean hiddenBottomLine;
    protected PtrClassicFrameLayout pcfl;
    protected boolean isStop;
    protected boolean noSetTheme;
    protected ProgressLayout pl_load;
    /****************************************************/
    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void onViewClick(View v);

    protected void initRxBus() {
    }
    protected void myReStart() {
    }
    protected void getOtherData(){};
    protected void getData(int page, boolean isLoad) {
    }
    protected void setClickListener(){};

    @Override
    protected void onStop() {
        super.onStop();
        isStop =true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isStop){
            isStop =false;
            myReStart();
        }
    }
    protected void hiddenBottomLine() {
        hiddenBottomLine = true;
        if (v_bottom_line != null) {
            v_bottom_line.setVisibility(View.GONE);
        }
    }

    protected void setAppTitle(String title) {
        appTitle = title;
        if (app_title != null) {
            if(BuildConfig.DEBUG){
                app_title.setText(appTitle == null ? "" : appTitle+"(DeBug)");
            }else{
                app_title.setText(appTitle == null ? "" : appTitle);
            }
        }
    }

    public void setAppRightTitle(String appRightTitle) {
        this.appRightTitle = appRightTitle;
        if (app_right_tv != null) {
            app_right_tv.setText(appRightTitle == null ? "" : appRightTitle);
        }
    }

    public void setAppRightImg(@DrawableRes  int appRightImg) {
        this.appRightImg = appRightImg;
        if (app_right_iv != null && appRightImg != 0) {
            app_right_iv.setImageResource(appRightImg);

            app_right_tv.setVisibility(View.GONE);
            app_right_iv.setVisibility(View.VISIBLE);
            app_right_iv.setOnClickListener(this);
            if(isShareImg){
                app_right_iv.setTag("share");
            }
        }
    }


    public void setNoSetTheme(boolean noSetTheme) {
        this.noSetTheme = noSetTheme;
    }

    public void setTitleBackgroud(@ColorRes int titleBackgroud) {
        this.titleBackgroud = titleBackgroud;
        if(toolbar!=null){
            toolbar.setBackgroundColor(ContextCompat.getColor(mContext, titleBackgroud));
        }
    }

 /*   public void setStatusBarBackgroud(@ColorRes int statusBarBackgroud) {
        this.statusBarBackgroud = statusBarBackgroud;
        if (status_bar != null) {
            status_bar.setBackgroundColor(ContextCompat.getColor(mContext, statusBarBackgroud));
        }
    }*/

    public void setAppTitleColor(@ColorRes  int appTitleColor) {
        this.appTitleColor = appTitleColor;
    }

    public void setAppRightTitleColor(@ColorRes  int appRightTitleColor) {
        this.appRightTitleColor = appRightTitleColor;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mContext = this;
        if (getContentView() != 0) {
            setContentView(getContentView());
            StatusBarUtils.setColor(this,ContextCompat.getColor(mContext,R.color.app_status),0);
//            View rootView = ((ViewGroup) this.findViewById(android.R.id.content))
//                    .getChildAt(0);
//            int navigationBarHeight = PhoneUtils.getNavigationBarHeight(mContext);
//            if(navigationBarHeight>0){
//                rootView.setPadding(0,0,0,navigationBarHeight);
//            }
        }
        if(!noSetTheme){
            setTheme(R.style.AppTheme_NoActionBar);
        }

/*        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }*/

        ButterKnife.bind(this);
        /*if (null != findViewById(R.id.status_bar)) {
            status_bar = findViewById(R.id.status_bar);
            int statusBarHeight = getStatusBarHeight(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = statusBarHeight;
            status_bar.setLayoutParams(layoutParams);
            status_bar.setBackgroundColor(ContextCompat.getColor(mContext, statusBarBackgroud));
        }*/
        if (null != findViewById(R.id.v_bottom_line)) {
            v_bottom_line = findViewById(R.id.v_bottom_line);
            if (hiddenBottomLine) {
                v_bottom_line.setVisibility(View.GONE);
            }
        }
        if (null != findViewById(R.id.toolbar)) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else {
                        finish();
                    }
                }
            });
            toolbar.setBackgroundColor(ContextCompat.getColor(mContext, titleBackgroud));
        }
        if (null != findViewById(R.id.app_title)) {
            app_title = (TextView) findViewById(R.id.app_title);
            setAppTitle(appTitle);
            if(null!=findViewById(R.id.v_bottom_line)){
                if(TextUtils.isEmpty(appTitle)||hiddenBottomLine){
                    findViewById(R.id.v_bottom_line).setVisibility(View.GONE);
                }
            }

            if (appTitleColor != 0) {
                app_title.setTextColor(ContextCompat.getColor(mContext,appTitleColor));
            }
        }
        if (null != findViewById(R.id.app_right_tv)) {
            app_right_tv = (TextView) findViewById(R.id.app_right_tv);
        }
        if (null != findViewById(R.id.app_right_iv)) {
            app_right_iv = (ImageView) findViewById(R.id.app_right_iv);
        }
        if (appRightImg != 0&&app_right_iv!=null) {
            app_right_iv.setImageResource(appRightImg);
            if(app_right_tv!=null){
                app_right_tv.setVisibility(View.GONE);
            }
            app_right_iv.setVisibility(View.VISIBLE);
            app_right_iv.setOnClickListener(this);
            if(isShareImg){
                app_right_iv.setTag("share");
            }
        }
        if (appRightTitle != null) {
            app_right_tv.setText(appRightTitle);
            app_right_tv.setVisibility(View.VISIBLE);
            if(app_right_iv!=null){
                app_right_iv.setVisibility(View.GONE);
            }
            if (appRightTitleColor != 0) {
                app_right_tv.setTextColor(ContextCompat.getColor(mContext,appRightTitleColor));
            }
        }
        if (null != findViewById(R.id.pcfl_refresh)) {
            pcfl = (PtrClassicFrameLayout) findViewById(R.id.pcfl_refresh);
            pcfl.setLastUpdateTimeRelateObject(this);
//            pcfl.disableWhenHorizontalMove(true);
            pcfl.setYOffsetMultiple(3);
            pcfl.setXOffsetMultiple(3);
            pcfl.setScaledTouchMultiple(0.5f);
            pcfl.setPtrHandler(new PtrDefaultHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    getOtherData();
                    getData(1, false);
                }
            });
        }
        if (null != findViewById(R.id.pl_load)) {
            pl_load = (ProgressLayout) findViewById(R.id.pl_load);
            pl_load.setInter(this);
        }
        if (null != findViewById(R.id.nsv)) {
            nsv = (NestedScrollView) findViewById(R.id.nsv);
        }
//        setInput();
        initRxBus();
        initView();
        setClickListener();
        if (toolbar != null) {
            if (navigationIcon != -1) {
                getSupportActionBar().setHomeAsUpIndicator(navigationIcon);
            } else {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.app_back);
//                getSupportActionBar().setHomeAsUpIndicator(R.drawable.app_back_black);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(showNavigationIcon);
        }
        initData();
    }

    protected void setBackIcon(@DrawableRes int resId) {
        navigationIcon = resId;
    }

    protected void hiddenBackIcon() {
        showNavigationIcon = false;
    }

    protected void hiddenBackIcon(boolean show) {
        showNavigationIcon = show;
    }

    protected String getSStr(View view) {
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        } else if (view instanceof EditText) {
            return ((EditText) view).getText().toString();
        } else {
            return null;
        }
    }

    public void showProgress() {
        if (pl_load != null) {
            pl_load.showProgress();
        }
    }

    public void showContent() {
        if (pl_load != null) {
            pl_load.showContent();
        }
    }

    public void showErrorText() {
        if (pl_load != null) {
            pl_load.showErrorText();
        }
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtils.isFastClick(v, 800)) {
            onViewClick(v);
        }
    }
    private void setInput() {
        /*if(mContext instanceof ShangJiaActivity){
            return;
        }*/
        final View rootView = ((ViewGroup) this.findViewById(android.R.id.content))
                .getChildAt(0);
        if(null==rootView){
            Log.i("MyBaseActivity","rootView=null");
            return;
        }
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect rect= new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = decorView.getRootView().getHeight();
                int heightDifferent = screenHeight - rect.bottom- PhoneUtils.getNavigationBarHeight(mContext);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
                lp.setMargins(0, 0, 0, heightDifferent);
                rootView.requestLayout();
            }
        });
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }

    @Override
    public void again() {
        initData();
    }


    protected boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    protected boolean notEmpty(List list) {
        return !(list == null || list.size() == 0);
    }

    protected String getRnd() {
        Random random = new Random();
        int rnd = random.nextInt(9000) + 1000;
        return rnd + "";
    }

    protected BaseDividerListItem getItemDivider() {
        return new BaseDividerListItem(mContext,2, R.color.background_f2);
    }

    protected BaseDividerListItem getItemDivider(int height) {
        return new BaseDividerListItem(mContext,height, R.color.background_f2);
    }

    protected BaseDividerListItem getItemDivider(int height, int color) {
        return new BaseDividerListItem(mContext, height, color);
    }



    public void deleteCache() {
        deleteCache(null,false);
    }
    public void deleteCache(final TextView textView,final boolean isAllCache) {
        RXStart(new MyFlowableSubscriber<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) {
                CacheUtils.clearAllCache(getApplicationContext());
                try {
                    String totalCacheSize = isAllCache?CacheUtils.getTotalCacheSize(getApplicationContext()):CacheUtils.getExternalCacheSize(getApplicationContext());
                    emitter.onNext(totalCacheSize);
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNext(String totalCacheSize) {
                showMsg("清除成功");
                if(textView!=null){
                    textView.setText(totalCacheSize);
                }
            }
        });


    }


    private BottomSheetDialog selectPhotoDialog;
    public final static int result_select_photo =8888;
    public final static int result_take_photo =8889;
    public void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.app_popu_select_photo,null);
            sexView.findViewById(R.id.app_tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhoto();
                }
            });
            sexView.findViewById(R.id.app_tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    takePhoto();
                }
            });
            sexView.findViewById(R.id.app_tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(mContext);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }
    //选择相册
    protected void selectPhoto() {
        requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionCallback() {
            @Override
            public void onGranted() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, result_select_photo);
            }
            @Override
            public void onDenied(String s) {
                showMsg("获取权限失败无法正常获取图片");
            }
        });
    }
    protected String getSelectPhotoPath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null,null);
        if (cursor != null && cursor.moveToFirst()) {
            takePhotoImgSavePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        }
        return takePhotoImgSavePath;
    }
    protected String getSelectPhotoPath(Intent data){
        return getSelectPhotoPath(data.getData());
    }
    private String path = Environment.getExternalStorageDirectory() +
            File.separator + Environment.DIRECTORY_DCIM + File.separator;
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }
    public String takePhotoImgSavePath ="";
    //拍照
    protected void takePhoto() {
        requestPermission(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionCallback() {
            @Override
            public void onGranted() {
                takePhoto("没有全部授权,无法拍照");
            }
            @Override
            public void onDenied(String s) {
                showMsg("获取权限失败无法正常拍照");
            }
        });
    }
    private void takePhoto(final String showStr) {
        /*if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
        }*/
      /*  requestPermission(showStr,new PermissionCallback() {
            @Override
            public void granted() {
                startTakePhoto();
            }
            @Override
            public void noGranted() {
            }
        },Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);*/
      requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionCallback() {
          @Override
          public void onGranted() {
              startTakePhoto();
          }
          @Override
          public void onDenied(String permission) {
            showMsg("没有全部授权,无法拍照");
          }
      });
    }

    private void startTakePhoto() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String fileName = getPhotoFileName() + ".jpg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePhotoImgSavePath = path + fileName;
            /*主要是由于在Android 7.0以后，用了Content Uri 替换了原本的File Uri，
            故在targetSdkVersion=24的时候，部分 “`Uri.fromFile()“` 方法就不适用了。
            **File Uri 与 Content Uri 的区别** - File Uri 对应的是文件本身的存储路径 -
            * Content Uri 对应的是文件在Content Provider的路径 所以在android 7.0 以上，
            * 我们就需要将File Uri转换为 Content Uri。具体转换方法如下：*/
            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
                Uri photoUri = Uri.fromFile(new File(takePhotoImgSavePath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, result_take_photo);
            }else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, takePhotoImgSavePath);
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    saveTakePhoto(intent, contentValues);
                }

            }
        }
    }
    private void saveTakePhoto(Intent intent, ContentValues contentValues) {
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, result_take_photo);
    }
    /**
     * 视图是否可见
     * @param view
     * @return
     */
    public boolean keJian(View view){
        Point p=new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        int screenWidth=p.x;
        int screenHeight=p.y;

        Rect rect=new Rect(0,0,screenWidth,screenHeight );

        int[] location = new int[2];
        view.getLocationInWindow(location);
//        System.out.println(Arrays.toString(location));
//        Log("==="+ Arrays.toString(location));
        // Rect ivRect=new Rect(imageView.getLeft(),imageView.getTop(),imageView.getRight(),imageView.getBottom());
        if (view.getLocalVisibleRect(rect)) {/*rect.contains(ivRect)*/
            return true;
        } else {
            return false;
        }
    }
    public int[] getViewLocation(View view){
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }
    public interface OnScrollAutoSelectViewInter{
        void selectViewPosition(int position,View view);
    }
    protected void scrollCheckViewIsShow(NestedScrollView nsv, final List<View>list){
        scrollCheckViewIsShow(nsv,list,null);
    }
    protected void scrollCheckViewIsShow(NestedScrollView nsv, final List<View>list,final OnScrollAutoSelectViewInter inter){
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(isEmpty(list)){
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    if(keJian(list.get(i))){
                        if(inter!=null){
                            inter.selectViewPosition(i,list.get(i));
                        }
                        break;
                    }
                }
            }
        });
    }
    protected void scrollAutoSelectView(NestedScrollView nsv,View view){
        nsv.smoothScrollTo(0,view.getTop());
        //- PhoneUtils.dip2px(mContext,barHeight)
    }

    protected void scrollChangeBackground(NestedScrollView nsv,final View view){
        view.getBackground().mutate().setAlpha(0);
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int screenWidth = PhoneUtils.getScreenWidth(mContext)*2/3;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 0 && scrollY <= screenWidth) {
                    double alpha = (double) scrollY / screenWidth;
                    view.getBackground().mutate().setAlpha((int) (alpha * 255));
                } else {
                    view.getBackground().mutate().setAlpha(255);
                }
            }
        });
    }


    public void requestPermission(String permission,final PermissionCallback callback){
        super.requestPermission(permission, new PermissionCallback() {
            @Override
            public void onGranted() {
                callback.onGranted();
            }
            @Override
            public void onDenied(String s) {
                callback.onDenied(s);
                showDialog(s);
            }
        });
    }
    public void requestPermission(String[]permission,final PermissionCallback callback){
        super.requestPermission(permission, new PermissionCallback() {
            @Override
            public void onGranted() {
                callback.onGranted();
            }
            @Override
            public void onDenied(String s) {
                callback.onDenied(s);
                showDialog(s);
            }
        });
    }
    private String permissionStr=",请在手机应用权限管理中开启权限";
    private void showDialog(String permission){
        String str="无法获取相关权限,请在手机应用权限管理中开启权限";
        switch (permission){
            case Manifest.permission.CAMERA:
                str="摄像头启动失败"+permissionStr;
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                str="无法获取手机文件"+permissionStr;
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                str="应用无法保存文件"+permissionStr;
                break;
        }
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage(str);
//        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        mDialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                JumpPermission.GoToSetting(mContext);
//                openPhoneSetting();
            }
        });
        mDialog.create().show();
    }
    protected void openPhoneSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
    }
}
