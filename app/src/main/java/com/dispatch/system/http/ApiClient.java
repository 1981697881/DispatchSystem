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
import com.dispatch.system.entity.WorkOrderBean;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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
 * ?????? retrofit ??????
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
     * ????????????
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
            //????????????????????????
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
                    .retryOnConnectionFailure(true) // ??????????????????
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

    //????????????SSLSocketFactory
    public SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //??????TrustManager
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

    //??????HostnameVerifier
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
     * ?????????????????????????????????
     */
    private static class NetInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Connection", "close")
                    .addHeader("osName", osName) // ??????????????????
                    .addHeader("versionCode", versionCode) // ?????????
                    .addHeader("deviceName", deviceName) // ????????????
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
     * ?????????????????????
     */
    private static class ApiClientInner {
        public static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return ApiClientInner.INSTANCE;
    }

    /**
     * ??????:????????????????????????
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
     * ????????????
     */
    public void logout(MyObserver<BaseBean> observer) {
        apiService
                .logout(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void getMineTodayTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getMineTodayTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void getStationTodayTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getStationTodayTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * ???????????????
     */
    public void getJsdTodayTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getJsdTodayTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * ?????????????????????
     */

    public void confirmJsd(String userAddress, String jsdOrderNumber, String userPhone,
                                MyObserver<BuildingTaskListBean> observer) {
        apiService
                .confirmJsd(getSendHeader(), userAddress, jsdOrderNumber, userPhone)
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * ???????????????
     */
    public void getJsdBuildingaskList(String buildingCode, String completeName,
                                                   MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getJsdBuildingaskList(getSendHeader(), buildingCode,completeName)
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * ??????????????????
     */
    public void getOverdueTask(MyObserver<MineTodayTaskBean> observer) {
        apiService
                .getOverdueTask(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????
     */
    public void getMinePickUpList(MyObserver<MinePickUpBean> observer) {
        apiService
                .getMinePickUpList(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????(??????)
     */
    public void getMinePickUpListPage(String pageSize, String pageIndex, boolean onPage,
                                      MyObserver<MinePickUpBean> observer) {
        apiService
                .getMinePickUpListPage(getSendHeader(), pageSize, pageIndex, onPage)
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * ????????????
     */
    public void getWorkOrderListPage(String status,
                                      MyObserver<WorkOrderBean> observer) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", status);
        System.out.println(jsonObject);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(jsonObject));

        apiService
                .getWorkOrderListPage(getSendHeader(), requestBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    public void getException(MyObserver<ExceptionTemplateBean> observer) {
        apiService
                .getException(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ???????????????
     */
    public void getBuildingTree(MyObserver<BuildingTreeBean> observer) {
        apiService
                .getBuildingTree(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    public void getDeliveryTime(MyObserver<DeliveryTimeBean> observer) {
        apiService
                .getDeliveryTime(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void getEmployeeInfo(MyObserver<EmployeeInfoBean> observer) {
        apiService
                .getEmployeeInfo(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????????????????
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
     * ????????????????????????
     */
    public void addDeliveryException(String trackingNumber, String exceptionTemplateCode, MyObserver<BaseBean> observer) {
        apiService
                .addDeliveryException(getSendHeader(), trackingNumber, exceptionTemplateCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * ????????????
     */
    public void updateWorkOrder(JsonObject jsonObject, MyObserver<BaseBean> observer) {
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), new Gson().toJson(jsonObject));

        apiService
                .updateWorkOrder(getSendHeader(), requestBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ???????????????(?????? code ??? trackingNumber ???????????????????????????)
     *
     * @param trackingNumber ????????????
     * @param code           ????????????????????????
     */
    public void deliveryDetail(String trackingNumber, String code, MyObserver<DeliveryDetailBean> observer) {
        apiService
                .deliveryDetail(getSendHeader(), trackingNumber, code)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ?????????????????????
     *
     * @param trackingNumber ????????????
     * @param budget         ????????????
     */
    public void addSelfReceiving(String trackingNumber, String business, String budget, MyObserver<BaseBean> observer) {
        apiService
                .addSelfReceiving(getSendHeader(), trackingNumber, business, budget)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    public void getDeliveryDetailList(String buildingCode, String houseNumber,
                                      MyObserver<BuildingDetailDeliveryBean> observer) {
        apiService
                .getDeliveryDetailList(getSendHeader(), buildingCode, houseNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }
    /**
     * ??????????????????????????????
     */
    public void getScanDetailList(String trackingNumber,
                                      MyObserver<BuildingDetailDeliveryBean> observer) {
        apiService
                .getScanDetailList(getSendHeader(),trackingNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    public void getDeliveryDetailTimeoutList(String buildingCode, String houseNumber, int pageIndex,
                                      MyObserver<BuildingDetailDeliveryBean> observer) {
        apiService
                .getDeliveryDetailTimeoutList(getSendHeader(), buildingCode, houseNumber, String.valueOf(pageIndex), "10", true)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????
     */
    public void confirmReceiver(String code, String trackingNumber, String budget,
                                MyObserver<BaseBean> observer) {
        apiService
                .confirmReceiver(getSendHeader(), code, trackingNumber, budget)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    public void verifyReceiveRecord(String trackingNumber,
                                    MyObserver<BaseBean> observer) {
        apiService
                .verifyReceiveRecord(getSendHeader(), trackingNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void getReceivingDetailList(String buildingCode, String houseNumber,
                                       MyObserver<HouseReceivingListBean> observer) {
        apiService
                .getReceivingDetailList(getSendHeader(), buildingCode, houseNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void getReceivingDetailTimeoutList(String buildingCode, String houseNumber, String pageIndex,
                                       MyObserver<HouseReceivingListBean> observer) {
        apiService
                .getReceivingDetailTimeoutList(getSendHeader(), buildingCode, houseNumber, pageIndex, "20", true)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????
     */
    public void getPickUpDetail(String code, String trackingNumber, MyObserver<PickUpDetailBean> observer) {
        apiService
                .getPickUpDetail(getSendHeader(), code, trackingNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    public void getMessageNotice(MyObserver<NewListBean> observer) {
        apiService
                .getMessageNotice(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????
     */
    public void uploadAvatar(RequestBody fileBody, MyObserver<UploadSignBean> observer) {
        apiService
                .uploadAvatar(getSendHeader(), fileBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void getBuildingTaskList(String buildingCode, String deliveryTimeCode, MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getBuildingTaskList(getSendHeader(), buildingCode, deliveryTimeCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????
     */
//    public void batchDeliverySign(List<String> trackingNumbers, String signedFileCode,
//                                  MyObserver<BuildingTaskListBean> observer) {
//        apiService
//                .batchDeliverySign(getSendHeader(), trackingNumbers, signedFileCode)
//                .compose(schedulersTransform())
//                .subscribe(observer);
//    }

    /**
     * ????????????
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
     * ????????????????????????
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
     * ????????????????????????
     */
    public void getMineBuildingTaskTaskList(String buildingCode, String deliveryTimeCode,
                                            MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getMineBuildingTaskTaskList(getSendHeader(), buildingCode, deliveryTimeCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????????????????????????????
     */
    public void getMineBuildingTimeoutTaskTaskList(String buildingCode,
                                            MyObserver<BuildingTaskListBean> observer) {
        apiService
                .getMineBuildingTimeoutTaskTaskList(getSendHeader(), buildingCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ?????????????????????
     */
    public void checkRefuse(String trackingNumber, String memberCode, MyObserver<CheckRefuseBean> observer) {
        apiService
                .checkRefuse(getSendHeader(), trackingNumber, memberCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void checkDelivery(String trackingNumber, String memberCode, MyObserver<CheckRefuseBean> observer) {
        apiService
                .checkDelivery(getSendHeader(), trackingNumber, memberCode)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????
     */
    public void searchMember(String mobileEndingNumber, MyObserver<SearchMemberBean> observer) {
        apiService
                .searchMember(getSendHeader(), mobileEndingNumber)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ???????????????
     */
    public void addUnVipMember(String realName, String mobileNumber, String buildingCode, String house,
                               MyObserver<BaseBean> observer) {
        apiService
                .addUnVipMember(getSendHeader(), realName, mobileNumber, buildingCode, house)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    public void getBusiness(MyObserver<BusinessBean> observer) {
        apiService
                .getBusiness(getSendHeader())
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ??????????????????
     */
    public void bindAllMembers(RequestBody requestBody, MyObserver<BaseBean> observer) {
        apiService
                .bindAllMembers(getSendHeader(), requestBody)
                .compose(schedulersTransform())
                .subscribe(observer);
    }

    /**
     * ????????????????????????
     */
    private Map<String, String> getSendHeader() {
        String sessionId = (String) SPUtils.getInstance().getString(SPConstants.SESSION_ID);
        return getSendHeader(sessionId);
    }

    /**
     * ????????????????????????
     */
    private Map<String, String> getSendHeader(String sessionId) {
        Map<String, String> headerMap = new HashMap<>();
        if (!TextUtils.isEmpty(sessionId)) {
            headerMap.put("Cookie", HttpConstants.SESSION_ID_NAME + "=" + sessionId);
        }
        return headerMap;
    }
}
