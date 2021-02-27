package ru.nextleap.fox_in_box.provider;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.nextleap.fox_in_box.data.BaseResponse;
import ru.nextleap.fox_in_box.data.Token;

public interface NetApi {
    @FormUrlEncoded
    @POST("Token")
    Single<Token> getToken(@Field("grant_type") String grant_type, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/user/register")
    Single<BaseResponse> register(@Field("Email") String email, @Field("Password") String password, @Field("FirstName") String firstName, @Field("MiddleName") String middleName, @Field("LastName") String lastName, @Field("PhoneNumber") String phoneNumber, @Field("TradePointName") String tradePointName, @Field("NotificationsEnabled") boolean notificationsEnabled);

    @FormUrlEncoded
    @POST("/api/user/changePassword")
    Single<BaseResponse> changePassword(@Field("OldPassword") String oldPassword, @Field("NewPassword") String newPassword);

    @FormUrlEncoded
    @POST("/api/user/code")
    Single<BaseResponse> code(@Field("NewEmail") String newEmail);

    @GET("api/profile")
    Single<BaseResponse> getProfile();

    @GET("api/news/list")
    Single<BaseResponse> getNewsList(@Query("skip") Integer skip, @Query("take") Integer take);

    @GET("api/news")
    Single<BaseResponse> getNews(@Query("id") Integer id);

    @GET("api/materials/list")
    Single<BaseResponse> getMaterialsList(@Query("skip") Integer skip, @Query("take") Integer take);

    @GET("api/sku/list")
    Single<BaseResponse> getSkuList(@Query("skip") Integer skip, @Query("take") Integer take);

    @GET("api/sku")
    Single<BaseResponse> getSku(@Query("id") Integer id);

    @GET("/api/rewards/aggregatedList")
    Single<BaseResponse> getAggregatedList(@Query("skip") Integer skip, @Query("take") Integer take);

    @GET("/api/orders/list")
    Single<BaseResponse> getOrdersList(@Query("skip") Integer skip, @Query("take") Integer take);

}
