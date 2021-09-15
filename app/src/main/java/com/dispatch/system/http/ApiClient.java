package com.dispatch.system.http;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dispatch.system.BuildConfig;
import com.dispatch.system.constants.SPConstants;
import com.dispatch.system.entity.BaseBean;
import com.dispatch.system.entity.BatchDeliveryExceptionBody;
import com.dispatch.system.entity.BatchDeliverySignBody;
import com.dispatch.system.entity.BatchUpdateDeliveryTimeBody;
import com.dispatch.system.entity.BuildingDetailDeliveryBean;
import com.dispatch.system.entity.BuildingTaskListBean;
import com.dispatch.system.entity.BuildingTreeBean;
import com.dispatch.system.entity.BusinessBean;
import com.dispatch.system.entity.CheckRefuseBean;
import com.dispatch.system.entity.DeliveryDetailBean;
import com.dispatch.system.entity.DeliveryTimeBean;
import com.dispatch.system.entity.EmployeeInfoBean;
import com.dispatch.system.entity.ExceptionTemplateBean;
import com.dispatch.system.entity.HouseReceivingListBean;
import com.dispatch.system.entity.LoginBean;
import com.dispatch.system.entity.MinePickUpBean;
import com.dispatch.system.entity.MineTodayTaskBean;
import com.dispatch.system.entity.NewListBean;
import com.dispatch.system.entity.PickUpDetailBean;
import com.dispatch.system.entity.SearchMemberBean;
import com.dispatch.system.entity.UploadSignBean;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取 retrofit 对象
 *
 * @author chenjunxu
 * @date 2017/8/16
 */
public class ApiClient {
    private static Retrofit retrofit;
    private static final int CONNECT_TIME_OUT = 20;
    private static final int READ_TIME_OUT = 20;
    private ApiService apiService;
    private static Gson gson;
    private OkHttpClient client;
    /**
     * 设备信息
     */
    private static String osName;
    private static String deviceName;
    private static String versionCode;

    private ApiClient() {
        osName = "Android " + android.os.Build.VERSION.RELEASE;
        deviceName = android.os.Build.MODEL;
        versionCode = AppUtils.getAppVersionName();

        getClient();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(HttpConstants.ROOT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public OkHttpClient getClient() {
        if (client == null) {
            //打印网络请求日志
//            LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder()
//                    .loggable(BuildConfig.DEBUG)
//                    .setLevel(HttpLoggingInterceptor.Level.BASIC)
//                    .log(Platform.WARN)
//                    .request("Request")
//                    .response("Response")
//                    .build();

            OkHttpClient.Builder builder = new OkHttpClient()
                    .newBuilder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .retryOnConnectionFailure(true) // 重新尝试发送
                    .addInterceptor(new NetInterceptor())
                    .sslSocketFactory(getSSLSocketFactory())
                    .hostnameVerifier(getHostnameVerifier())
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                        message -> Log.e("ApiClient", message));
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }
            client = builder.build();
        }
        return client;
    }

    //获取这个SSLSocketFactory
    public SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }

    //获取HostnameVerifier
    public HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    /**
     * 头部添加请求的设备信息
     */
    private static class NetInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Connection", "close")
                    .addHeader("osName", osName) // 操作系统名称
                    .addHeader("versionCode", versionCode) // 版本号
                    .addHeader("deviceName", deviceName) // 机型名称
                    .addHeader("osType", "android")
                    .build();
            return chain.proceed(request);
        }
    }

    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Integer.class, new IntegerDefaultZeroAdapter())
                    .registerTypeAdapter(int.class, new IntegerDefaultZeroAdapter())
                    .create();
        }
        return gson;
    }

    public static ObservableTransformer schedulersTransform() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(@NonNull Observable upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 内部类实现单例
     */
    private static class ApiClientInner {
        public static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return ApiClientInner.INSTANCE;
    }

    /**
     * 登录:手机账号密码登录
     */
    public void login(String account,
                      String password,
                      BaseObserver<Response<LoginBean>> observer) {
        apiService
                .login(account, password)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 退出登录
     */
    public void logout(MyObserver<BaseBean> observer) {
        apiService
                .logout(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 我的今天任务
     */
    public void getMineTodayTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getMineTodayTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 驿站今天任务
     */
    public void getStationTodayTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getStationTodayTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * 极速达订单
     */
    public void getJsdTodayTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getJsdTodayTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * 极速达确认揽件
     */
    public void confirmJsd(String code, String trackingNumber, String budget,
                                MyObserver<BaseBean> observer) {
        apiService
                .confirmJsd(getSendHeader(), code, trackingNumber, budget)
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * 极速达详情
     */
    public void getJsdBuildingaskList(String buildingCode, String completeName,
                                                   MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getJsdBuildingaskList(getSendHeader(), buildingCode,completeName)
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * 驿站今天任务
     */
    public void getOverdueTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getOverdueTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 我的揽件
     */
    public void getMinePickUpList(MyObserver<MinePickUpBean> observer) {
        apiService
                .getMinePickUpList(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 我的揽件(分页)
     */
    public void getMinePickUpListPage(String pageSize, String pageIndex, boolean onPage,
                                      MyObserver<MinePickUpBean> observer) {
        apiService
                .getMinePickUpListPage(getSendHeader(), pageSize, pageIndex, onPage)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 查询异常描述模版
     */
    public void getException(MyObserver<ExceptionTemplateBean> observer) {
        apiService
                .getException(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 查询楼栋树
     */
    public void getBuildingTree(MyObserver<BuildingTreeBean> observer) {
        apiService
                .getBuildingTree(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 查询派送时间列表
     */
    public void getDeliveryTime(MyObserver<DeliveryTimeBean> observer) {
        apiService
                .getDeliveryTime(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 获取员工信息
     */
    public void getEmployeeInfo(MyObserver<EmployeeInfoBean> observer) {
        apiService
                .getEmployeeInfo(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 批量添加派件异常信息
     */
    public void addBatchDeliveryException(List<String> trackingNumber,
                                          String exceptionTemplateCode,
                                          MyObserver<BaseBean> observer) {
        BatchDeliveryExceptionBody body = new BatchDeliveryExceptionBody();
        body.setTrackingNumbers(trackingNumber);
        body.setExceptionTemplateCode(exceptionTemplateCode);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(body));
        apiService
                .addBatchDeliveryException(getSendHeader(), requestBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 添加派件异常信息
     */
    public void addDeliveryException(String trackingNumber, String exceptionTemplateCode, MyObserver<BaseBean> observer) {
        apiService
                .addDeliveryException(getSendHeader(), trackingNumber, exceptionTemplateCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 配送件详情(传入 code 或 trackingNumber 其中一个参数即可。)
     *
     * @param trackingNumber 快递编号
     * @param code           快递记录唯一标识
     */
    public void deliveryDetail(String trackingNumber, String code, MyObserver<DeliveryDetailBean> observer) {
        apiService
                .deliveryDetail(getSendHeader(), trackingNumber, code)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 扫描添加自揽件
     *
     * @param trackingNumber 快递编号
     * @param budget         快递费用
     */
    public void addSelfReceiving(String trackingNumber, String business, String budget, MyObserver<BaseBean> observer) {
        apiService
                .addSelfReceiving(getSendHeader(), trackingNumber, business, budget)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 户号派送记录列表
     */
    public void getDeliveryDetailList(String buildingCode, String houseNumber,
                                      MyObserver<BuildingDetailDeliveryBean> observer) {
        apiService
                .getDeliveryDetailList(getSendHeader(), buildingCode, houseNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 户号派送记录列表
     */
    public void getDeliveryDetailTimeoutList(String buildingCode, String houseNumber, int pageIndex,
                                      MyObserver<BuildingDetailDeliveryBean> observer) {
        apiService
                .getDeliveryDetailTimeoutList(getSendHeader(), buildingCode, houseNumber, String.valueOf(pageIndex), "10", true)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 确认揽件
     */
    public void confirmReceiver(String code, String trackingNumber, String budget,
                                MyObserver<BaseBean> observer) {
        apiService
                .confirmReceiver(getSendHeader(), code, trackingNumber, budget)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 户号派送记录列表
     */
    public void verifyReceiveRecord(String trackingNumber,
                                    MyObserver<BaseBean> observer) {
        apiService
                .verifyReceiveRecord(getSendHeader(), trackingNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 户号揽收记录
     */
    public void getReceivingDetailList(String buildingCode, String houseNumber,
                                       MyObserver<HouseReceivingListBean> observer) {
        apiService
                .getReceivingDetailList(getSendHeader(), buildingCode, houseNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 户号揽收记录
     */
    public void getReceivingDetailTimeoutList(String buildingCode, String houseNumber, String pageIndex,
                                       MyObserver<HouseReceivingListBean> observer) {
        apiService
                .getReceivingDetailTimeoutList(getSendHeader(), buildingCode, houseNumber, pageIndex, "20", true)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 揽件详情
     */
    public void getPickUpDetail(String code, String trackingNumber, MyObserver<PickUpDetailBean> observer) {
        apiService
                .getPickUpDetail(getSendHeader(), code, trackingNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 最新消息轮询接口
     */
    public void getMessageNotice(MyObserver<NewListBean> observer) {
        apiService
                .getMessageNotice(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 上传签名
     */
    public void uploadAvatar(RequestBody fileBody, MyObserver<UploadSignBean> observer) {
        apiService
                .uploadAvatar(getSendHeader(), fileBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 楼栋任务列表
     */
    public void getBuildingTaskList(String buildingCode, String deliveryTimeCode, MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getBuildingTaskList(getSendHeader(), buildingCode, deliveryTimeCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 批量签收
     */
//    public void batchDeliverySign(List<String> trackingNumbers, String signedFileCode,
//                                  MyObserver<BuildingTaskListBean> observer) {
//        apiService
//                .batchDeliverySign(getSendHeader(), trackingNumbers, signedFileCode)
//                .compose(schedulersTransform())
//                .subscribe(observer);
//    }

    /**
     * 批量签收
     */
    public void batchDeliverySign(List<String> trackingNumbers, String signedFileCode,
                                  MyObserver<BaseBean> observer) {
        BatchDeliverySignBody body = new BatchDeliverySignBody();
        body.setTrackingNumbers(trackingNumbers);
        body.setSignedFileCode(signedFileCode);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(body));
        apiService
                .batchDeliverySign(getSendHeader(), requestBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 批量修改配送时间
     */
    public void batchUpdateDeliveryTime(String dataType, List<String> trackingNumbers, String timeCode,
                                        MyObserver<BaseBean> observer) {
        BatchUpdateDeliveryTimeBody body = new BatchUpdateDeliveryTimeBody();
        body.setTrackingNumbers(trackingNumbers);
        body.setDateType(dataType);
        body.setDeliveryTimeCode(timeCode);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(body));
        apiService
                .batchUpdateDeliveryTime(getSendHeader(), requestBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }


    /**
     * 我的今天楼栋任务
     */
    public void getMineBuildingTaskTaskList(String buildingCode, String deliveryTimeCode,
                                            MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getMineBuildingTaskTaskList(getSendHeader(), buildingCode, deliveryTimeCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 我的楼栋超时配送与未揽件任务
     */
    public void getMineBuildingTimeoutTaskTaskList(String buildingCode,
                                            MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getMineBuildingTimeoutTaskTaskList(getSendHeader(), buildingCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 检测是否拒收件
     */
    public void checkRefuse(String trackingNumber, String memberCode, MyObserver<CheckRefuseBean> observer) {
        apiService
                .checkRefuse(getSendHeader(), trackingNumber, memberCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 检查快递信息
     */
    public void checkDelivery(String trackingNumber, String memberCode, MyObserver<CheckRefuseBean> observer) {
        apiService
                .checkDelivery(getSendHeader(), trackingNumber, memberCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 搜索会员
     */
    public void searchMember(String mobileEndingNumber, MyObserver<SearchMemberBean> observer) {
        apiService
                .searchMember(getSendHeader(), mobileEndingNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 添加非会员
     */
    public void addUnVipMember(String realName, String mobileNumber, String buildingCode, String house,
                               MyObserver<BaseBean> observer) {
        apiService
                .addUnVipMember(getSendHeader(), realName, mobileNumber, buildingCode, house)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 获取快递厂商字典
     */
    public void getBusiness(MyObserver<BusinessBean> observer) {
        apiService
                .getBusiness(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 批量绑定会员
     */
    public void bindAllMembers(RequestBody requestBody, MyObserver<BaseBean> observer) {
        apiService
                .bindAllMembers(getSendHeader(), requestBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * 获取发送的头文件
     */
    private Map<String, String> getSendHeader() {
        String sessionId = (String) SPUtils.getInstance().getString(SPConstants.SESSION_ID);
        return getSendHeader(sessionId);
    }

    /**
     * 获取发送的头文件
     */
    private Map<String, String> getSendHeader(String sessionId) {
        Map<String, String> headerMap = new HashMap<>();
        if (!TextUtils.isEmpty(sessionId)) {
            headerMap.put("Cookie", HttpConstants.SESSION_ID_NAME + "=" + sessionId);
        }
        return headerMap;
    }
}
