package com.library.base;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.ClickUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerListItem;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.fragment.IBaseFragment;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.library.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/7/13.
 */

public abstract class MyBaseFragment extends IBaseFragment implements View.OnClickListener,ProgressLayout.OnAgainInter,LoadMoreAdapter.OnLoadMoreListener{
    protected int pageNum=2;
    protected int pageSize=20;
    protected int pagesize = 20;
    protected NestedScrollView nsv;
    private boolean isFirstLoadData=true;
    private boolean isPrepared;
    protected PtrClassicFrameLayout pcfl;
    /************************************************/
    protected abstract int getContentView();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void onViewClick(View v);
    protected void initRxBus(){};
    protected boolean isStop;
    protected void myReStart() {
    }
    protected void getOtherData(){};
    protected void getData(int page,boolean isLoad){};
    protected Unbinder mUnBind;
    protected String TAG=this.getClass().getSimpleName();
    protected ProgressLayout pl_load;
    protected RxPermissions rxPermissions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        mUnBind = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(null!=view.findViewById(R.id.pcfl_refresh)){
            pcfl = (PtrClassicFrameLayout) view.findViewById(R.id.pcfl_refresh);
            pcfl.setLastUpdateTimeRelateObject(this);
//            pcfl.disableWhenHorizontalMove(true);
            pcfl.setYOffsetMultiple(3);
            pcfl.setXOffsetMultiple(3);
            pcfl.setScaledTouchMultiple(0.5f);
            pcfl.setPtrHandler(new PtrDefaultHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    getOtherData();
                    getData(1,false);
                }
            });
        }
        if(null!=view.findViewById(R.id.pl_load)){
            pl_load = (ProgressLayout) view.findViewById(R.id.pl_load);
            pl_load.setInter(this);
        }
        if(null!=view.findViewById(R.id.nsv)){
            nsv = (NestedScrollView) view.findViewById(R.id.nsv);
        }
        initView();
        initRxBus();
        isPrepared=true;
        setUserVisibleHint(true);
    }

    @Override
    public void onStop() {
        super.onStop();

        isStop =true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            isStop =true;
        }else{
            isStop =false;
            myReStart();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(isStop){
            isStop =false;
            myReStart();
        }
    }
    protected void requestPermission(final PermissionCallback callback,final String... permission){
        requestPermission(null,callback,permission);
    }
    protected void requestPermission(final String showStr,final PermissionCallback callback,final String... permission){
        if(rxPermissions==null){
            rxPermissions=new RxPermissions(getActivity());
        }
        rxPermissions.request(permission).subscribe(new MySubscriber<Boolean>() {
            @Override
            public void onMyNext(Boolean granted) {
                if(granted){
                    callback.granted();
                }else {
                    if(!TextUtils.isEmpty(showStr)){
                        showMsg(showStr);
                    }
                    callback.noGranted();
                }
            }
        });
    }
    public void showProgress(){
        if (pl_load != null) {
            pl_load.showProgress();
        }
    }
    public void showContent(){
        if (pl_load != null) {
            pl_load.showContent();
        }
    }
    public void showErrorText(){
        if (pl_load != null) {
            pl_load.showErrorText();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstLoadData&&isPrepared&&getUserVisibleHint()&&isVisibleToUser){
            initData();
            isFirstLoadData=false;
        }
    }
    protected String getSStr(View view){
        if(view instanceof TextView){
            return ((TextView)view).getText().toString();
        } else if (view instanceof EditText) {
            return ((EditText)view).getText().toString();
        }else{
            return null;
        }
    }
    @Override
    public void onClick(View v) {
        if(!ClickUtils.isFastClick(v)){
            onViewClick(v);
        }
    }
    public void onDestroy() {
        super.onDestroy();
        mUnBind.unbind();
        RxBus.getInstance().removeAllStickyEvents();
    }
    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
    @Override
    public void again() {
        initData();
    }

    protected boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }
    protected boolean notEmpty(List list){
        return !(list == null || list.size() == 0);
    }
    protected String getRnd(){
        Random random = new Random();
        int rnd = random.nextInt(9000) + 1000;
        return rnd+"";
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
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, result_select_photo);
    }
    protected String getSelectPhotoPath(Uri uri){
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null,null);
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
    private void takePhoto() {
        takePhoto("没有授权,无法拍照");
    }
    private void takePhoto(final String showStr) {
        /*if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
        }*/
        requestPermission(showStr,new PermissionCallback() {
            @Override
            public void granted() {
                startTakePhoto();
            }
            @Override
            public void noGranted() {
            }
        },Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
        Uri uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, result_take_photo);
    }
}
