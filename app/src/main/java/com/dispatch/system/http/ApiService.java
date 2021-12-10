package com.dispatch.system.http;


import com.dispatch.system.entity.BaseBean;
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

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 存放所有 api 请求
 *
 * @author chenjunxu
 * @date 2017/8/16
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("mobileAuth/login")
    Observable<Response<LoginBean>> login(@Field("account") String account,
                                          @Field("password") String password);

    @POST("mobileAuth/logout")
    Observable<BaseBean> logout(@HeaderMap Map<String, String> headerMap);

    /**
     * 我的今天任务
     */
    @POST("task/myTodayTask")
    Observable<MineTodayTaskBean> getMineTodayTask(@HeaderMap Map<String, String> headerMap);

    /**
     * 我的今日楼栋任务
     */
    @FormUrlEncoded
    @POST("task/myBuildingTodayTaskList")
    Observable<BuildingTaskListBean> getMineBuildingTaskTaskList(@HeaderMap Map<String, String> headerMap,
                                                                 @Field("buildingCode") String buildingCode,
                                                                 @Field("deliveryTimeCode") String deliveryTimeCode);


    /**
     * 我的楼栋超时配送与未揽件任务
     */
    @FormUrlEncoded
    @POST("task/myBuildingTimeoutAndNotReceivedTask")
    Observable<BuildingTaskListBean> getMineBuildingTimeoutTaskTaskList(@HeaderMap Map<String, String> headerMap,
                                                                        @Field("buildingCode") String buildingCode);

    /**
     * 驿站今天任务
     */
    @POST("task/todayTask")
    Observable<MineTodayTaskBean> getStationTodayTask(@HeaderMap Map<String, String> headerMap);

    /**
     * 极速达订单
     */
    @POST("mobileReceivingRecord/jsdOrders")
    Observable<MineTodayTaskBean> getJsdTodayTask(@HeaderMap Map<String, String> headerMap);
    /**
     * 极速达确认揽件
     */
    @POST("mobileDeliveryRecord/sendJSDOrder")
    @FormUrlEncoded
    Observable<BuildingTaskListBean> confirmJsd(@HeaderMap Map<String, String> headerMap,
                                         @Field("userAddress") String userAddress,
                                         @Field("jsdOrderNumber") String jsdOrderNumber,
                                         @Field("userPhone") String userPhone);
    /**
     * 极速达详情
     */
    @FormUrlEncoded
    @POST("mobileReceivingRecord/jsdOrdersDetail")
    Observable<BuildingTaskListBean> getJsdBuildingaskList(@HeaderMap Map<String, String> headerMap,
                                                                        @Field("code") String buildingCode, @Field("completeName") String completeName);
    /**
     * 我的超时件与未揽件
     */
    @POST("task/myTimeoutAndNotReceivedTask")
    Observable<MineTodayTaskBean> getOverdueTask(@HeaderMap Map<String, String> headerMap);

    /**
     * 我的揽件
     */
    @POST("mobileReceivingRecord/myTaskList")
    Observable<MinePickUpBean> getMinePickUpList(@HeaderMap Map<String, String> headerMap);

    /**
     * 我的揽件（分页）
     */
    @FormUrlEncoded
    @POST("mobileReceivingRecord/myTaskPage")
    Observable<MinePickUpBean> getMinePickUpListPage(@HeaderMap Map<String, String> headerMap,
                                                     @Field("pageSize") String pageSize,
                                                     @Field("pageIndex") String pageIndex,
                                                     @Field("onPage") boolean onPage);

    /**
     * 查询异常描述模版
     */
    @POST("mobileExceptionTemplate/list")
    Observable<ExceptionTemplateBean> getException(@HeaderMap Map<String, String> headerMap);

    /**
     * 查询楼栋树
     */
    @POST("mobileBuilding/tree")
    Observable<BuildingTreeBean> getBuildingTree(@HeaderMap Map<String, String> headerMap);

    /**
     * 查询派送时间列表
     */
    @POST("mobileDeliveryTime/list")
    Observable<DeliveryTimeBean> getDeliveryTime(@HeaderMap Map<String, String> headerMap);

    /**
     * 获取员工信息
     */
    @POST("mobileEpsEmployee/epsEmployeeInfo")
    Observable<EmployeeInfoBean> getEmployeeInfo(@HeaderMap Map<String, String> headerMap);

    /**
     * 批量添加派件异常信息
     */
    @POST("task/batchAddDeliveryException")
    Observable<BaseBean> addBatchDeliveryException(@HeaderMap Map<String, String> headerMap,
                                                   @Body RequestBody body
                                              /*@Field("trackingNumbers") List<String> trackingNumbers,
                                              @Field("exceptionTemplateCode") String exceptionTemplateCode*/);

    /**
     * 添加派件异常信息
     */
    @POST("task/addDeliveryException")
    @FormUrlEncoded
    Observable<BaseBean> addDeliveryException(@HeaderMap Map<String, String> headerMap,
                                              @Field("trackingNumber") String trackingNumber,
                                              @Field("exceptionTemplateCode") String exceptionTemplateCode);

    /**
     * 配送件详情
     */
    @POST("mobileDeliveryRecord/detail")
    @FormUrlEncoded
    Observable<DeliveryDetailBean> deliveryDetail(@HeaderMap Map<String, String> headerMap,
                                                  @Field("trackingNumber") String trackingNumber,
                                                  @Field("code") String code);

    /**
     * 扫描添加自揽件
     */
    @POST("mobileReceivingRecord/addSelfReceivingRecord")
    @FormUrlEncoded
    Observable<BaseBean> addSelfReceiving(@HeaderMap Map<String, String> headerMap,
                                          @Field("trackingNumber") String trackingNumber,
                                          @Field("business") String business,
                                          @Field("budget") String budget);

    /**
     * 批量修改配送时间
     */
    @POST("task/batchUpdateDeliveryTime")
    Observable<BaseBean> batchUpdateDeliveryTime(@HeaderMap Map<String, String> headerMap,
                                                 @Body RequestBody body);

    /**
     * 户号派送记录列表
     */
    @POST("task/houseDeliveryList")
    @FormUrlEncoded
    Observable<BuildingDetailDeliveryBean> getDeliveryDetailList(@HeaderMap Map<String, String> headerMap,
                                                                 @Field("buildingCode") String buildingCode,
                                                                 @Field("houseNumber") String houseNumber);/**

     * 扫描户号派送记录列表
     */
    @POST("task/orderScan")
    @FormUrlEncoded
    Observable<BuildingDetailDeliveryBean> getScanDetailList(@HeaderMap Map<String, String> headerMap,
                                                                 @Field("trackingNumber") String trackingNumber);

    /**
     * 户号派送记录列表
     */
    @POST("task/houseTimeoutDeliveryPage")
    @FormUrlEncoded
    Observable<BuildingDetailDeliveryBean> getDeliveryDetailTimeoutList(@HeaderMap Map<String, String> headerMap,
                                                                        @Field("buildingCode") String buildingCode,
                                                                        @Field("houseNumber") String houseNumber,
                                                                        @Field("pageIndex") String pageIndex,
                                                                        @Field("pageSize") String pageSize,
                                                                        @Field("onPage") boolean onPage);

    /**
     * 确认揽件
     */
    @POST("mobileReceivingRecord/confirmReceived")
    @FormUrlEncoded
    Observable<BaseBean> confirmReceiver(@HeaderMap Map<String, String> headerMap,
                                         @Field("code") String code,
                                         @Field("trackingNumber") String trackingNumber,
                                         @Field("budget") String budget);

    /**
     * 核销揽件
     */
    @POST("mobileReceivingRecord/verify")
    @FormUrlEncoded
    Observable<BaseBean> verifyReceiveRecord(@HeaderMap Map<String, String> headerMap,
                                             @Field("trackingNumber") String trackingNumber);

    /**
     * 户号揽收记录
     */
    @POST("task/houseReceivingList")
    @FormUrlEncoded
    Observable<HouseReceivingListBean> getReceivingDetailList(@HeaderMap Map<String, String> headerMap,
                                                              @Field("buildingCode") String buildingCode,
                                                              @Field("houseNumber") String houseNumber);

    /**
     * 户号揽收记录
     */
    @POST("task/houseNotReceivingPage")
    @FormUrlEncoded
    Observable<HouseReceivingListBean> getReceivingDetailTimeoutList(@HeaderMap Map<String, String> headerMap,
                                                                     @Field("buildingCode") String buildingCode,
                                                                     @Field("houseNumber") String houseNumber,
                                                                     @Field("pageIndex") String pageIndex,
                                                                     @Field("pageSize") String pageSize,
                                                                     @Field("onPage") boolean onPage);

    /**
     * 揽件详情
     */
    @FormUrlEncoded
    @POST("mobileReceivingRecord/detail")
    Observable<PickUpDetailBean> getPickUpDetail(@HeaderMap Map<String, String> headerMap,
                                                 @Field("code") String code,
                                                 @Field("trackingNumber") String trackingNumber);

    /**
     * 最新消息轮询接口
     */
    @POST("mobileMessageNoticeRecord/newest")
    Observable<NewListBean> getMessageNotice(@HeaderMap Map<String, String> headerMap);

    /**
     * 上传签名
     */
    @POST("common/uploadDeliverySigned")
    @Multipart
    Observable<UploadSignBean> uploadAvatar(@HeaderMap Map<String, String> headerMap,
                                            @Part("file\"; filename=\"sign.png") RequestBody fileBody
            /*@Part("uploadFile\"; filename=\"sign.png") RequestBody fileBody */);

    /**
     * 楼栋任务列表
     */
    @FormUrlEncoded
    @POST("task/buildingTodayTaskList")
    Observable<BuildingTaskListBean> getBuildingTaskList(@HeaderMap Map<String, String> headerMap,
                                                         @Field("buildingCode") String buildingCode,
                                                         @Field("deliveryTimeCode") String deliveryTimeCode);

    /**
     * 批量签收
     */
    @POST("task/batchDeliverySigned")
    Observable<BaseBean> batchDeliverySign(@HeaderMap Map<String, String> headerMap,
                                           @Body RequestBody requestBody);

    /**
     * 查询快件是否为拒收件
     */
    @FormUrlEncoded
    @POST("mobileDeliveryRecord/checkRefuse")
    Observable<CheckRefuseBean> checkRefuse(@HeaderMap Map<String, String> headerMap,
                                            @Field("trackingNumber") String trackingNumber,
                                            @Field("memberCode") String memberCode);

    /**
     * 检查快递信息
     */
    @FormUrlEncoded
    @POST("mobileDeliveryRecord/checkDelivery")
    Observable<CheckRefuseBean> checkDelivery(@HeaderMap Map<String, String> headerMap,
                                              @Field("trackingNumber") String trackingNumber,
                                              @Field("memberCode") String memberCode);

    /**
     * 楼栋任务列表
     *
     * @param mobileEndingNumber 手机四位尾号
     */
    @FormUrlEncoded
    @POST("mobileMember/search")
    Observable<SearchMemberBean> searchMember(@HeaderMap Map<String, String> headerMap,
                                              @Field("mobileEndingNumber") String mobileEndingNumber);

    /**
     * 批量绑定会员
     */
    @POST("mobileDeliveryRecord/batchBindMember")
    Observable<BaseBean> bindAllMembers(@HeaderMap Map<String, String> headerMap,
                                        @Body RequestBody body);

    /**
     * 添加非会员
     */
    @FormUrlEncoded
    @POST("mobileMember/add")
    Observable<BaseBean> addUnVipMember(@HeaderMap Map<String, String> headerMap,
                                        @Field("realName") String realName,
                                        @Field("mobileNumber") String mobileNumber,
                                        @Field("buildingCode") String buildingCode,
                                        @Field("houseNumber") String houseNumber);
    /**
     * 获取快递厂商字典
     */
    @POST("common/loadBusiness")
    Observable<BusinessBean> getBusiness(@HeaderMap Map<String, String> headerMap);

}
