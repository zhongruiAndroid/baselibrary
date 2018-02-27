package com.library;

import com.library.base.tools.LunarUtils;
import com.library.base.tools.has.Lunar;
import com.library.base.tools.has.LunarSolar;
import com.library.base.tools.has.Solar;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void rili() throws Exception {
        Solar solar = new Solar();
        solar.solarYear = 2001;
        solar.solarMonth = 5;
        solar.solarDay = 23;
        System.out.println("solar:"+solar.toString());


        LunarSolar lunarSolar = new LunarSolar();
        Lunar lunar = lunarSolar.SolarToLunar(solar);
        System.out.println(lunar.isleap+"lunar:"+lunar.toString());


        lunar.lunarYear = lunar.lunarYear+1;
//        lunar.isleap = false;
        Solar new_solar = lunarSolar.LunarToSolar(lunar);
        System.out.println("new_lunar:"+lunar.toString());
        System.out.println("new_solar:"+new_solar.toString());
    }
    public void affdsfsdfsd() throws Exception {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        LunarUtils a=new LunarUtils(calendar);
        System.out.println(a.animalsYear());
        System.out.println(a.cyclical());
        System.out.println(a.get_Big_Or_Small());
        System.out.println(a.get_date());
        System.out.println(a.get_month());
        System.out.println(a.numeric_md());
        System.out.println(a.toString());
    }
    @Test
    public void afsdfsd() throws Exception {
        String a="asdfsf.gd.54.";
        String[] bb = a.split("\\.");
        System.out.println(bb.length);
        System.out.println(bb[bb.length-1]);
    }

    public void a(String... a){
        if(a==null){
            System.out.println("空");
        }else{
            System.out.println(a.length);
        }
    }





































//    public interface service {
//        @Multipart
//        @POST("api/SHLGPub/PostUploadFile")
//        Call<ResponseBody> upload(@QueryMap Map<String, String> map, @Part() MultipartBody.Part file);
//
//        @Multipart
//        @POST("api/SHLGPub/PostUploadFile")
//        Call<ResponseObj<BaseObj>> newUpload(@QueryMap Map<String, String> map, @Part() MultipartBody.Part file);
//    }
//
//    public Retrofit getClient() {
//        return new Retrofit.Builder()
//                .baseUrl("http://121.40.186.118:1554/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    private void uploadFile(String path) {
////        String path="/storage/emulated/0/非手机自带/image/346853.jpg";
//        File file = new File(path);
//
//        // 创建 RequestBody，用于封装构建RequestBody
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        UploadRequestBody uploadRequestBody=new UploadRequestBody(file, new UploadRequestBody.ProgressListener() {
//            @Override
//            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
//                Log.i(TAG+"===","==="+hasWrittenLen+"====="+totalLen+"=="+hasFinish);
//            }
//        });
//
//
//// MultipartBody.Part  和后端约定好Key，这里的partName是用image
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("file", file.getName(), uploadRequestBody);
//
//// 添加描述
//        String descriptionString = "hello, 这是文件描述";
//        RequestBody description =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), descriptionString);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("rnd", getRnd());
//        map.put("sign", getSign(map));
//
//// 执行请求
////        Call<ResponseBody> call = getClient().create(service.class).upload(map, body);
//        Call<ResponseObj<BaseObj>> call = NetWorkManager.getGeneralClient("http://121.40.186.118:1554/").create(service.class).newUpload(map, body);
//        call.enqueue(new MyCallBack<BaseObj>(mContext) {
//            @Override
//            public void onSuccess(BaseObj obj) {
//                Log.i(TAG + "===", "===" + obj.toString());
//            }
//        });
//       /* call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call,
//                                   Response<ResponseBody> response) {
//                Log.v("Upload", "success");
//                try {
//                    Log.i(TAG + "===", "===" + response.body().string());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("Upload error:", t.getMessage());
//            }
//        });
//*/
//    }
}