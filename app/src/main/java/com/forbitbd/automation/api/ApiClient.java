package com.forbitbd.automation.api;




import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.RegisterReq;
import com.forbitbd.automation.models.ShareDeviceRequest;
import com.forbitbd.automation.models.SharedDevice;
import com.forbitbd.automation.models.SharedUser;
import com.forbitbd.automation.models.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiClient {

    // Users
    @POST("/api/users")
    Call<User> register(@Body User data);

    @GET("/api/users/{email}")
    Call<User> getUser(@Path("email") String email);

    @GET("/api/users/query/{query}")
    Call<List<User>> getQueryUsers(@Path("query") String query);

    @PUT("/api/users/{user_id}")
    @Multipart
    Call<User> updateProfile(@Path("user_id") String user_id,
                             @Part MultipartBody.Part file,
                             @PartMap() Map<String, RequestBody> partMap);


    @POST("/api/devices/register")
    Call<Device> registerDevice(@Body RegisterReq registerReq);

    @PUT("/api/devices/{device_id}")
    Call<Device> updateDevice(@Path("device_id") String deviceID,@Body Device device);


    @POST("/api/commands")
    Call<Void> sendCommand(@Body Command command);


    @POST("/api/shareddevices")
    Call<Void> sharedDevice(@Body ShareDeviceRequest request);

    @GET("/api/shareddevices/sharedusers/{device_id}")
    Call<List<SharedUser>> getSharedUser(@Path("device_id") String deviceID);

    @GET("/api/shareddevices/shareddevices/{uid}")
    Call<List<SharedDevice>> getSharedDevices(@Path("uid") String uid);

    @DELETE("/api/shareddevices/{id}")
    Call<Void> deleteSharedUser(@Path("id") String sharedUserID);

}
