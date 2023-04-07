package client.legalease.WebServices;

import java.util.HashMap;
import java.util.List;

import client.legalease.Model.Acceptedordermodel.AcceptedOrderModel;
import client.legalease.Model.AccountModel.Account;
import client.legalease.Model.AccountantDetails.AccountantDetail;
import client.legalease.Model.AccountantModel.AccountantModel;
import client.legalease.Model.AddAccountDetailsModel.AddAccountDetailsModel;
import client.legalease.Model.Appslider.AppSliderModel;
import client.legalease.Model.Bookmeetmodel;
import client.legalease.Model.CheckCoupanModel.CheckCoupanModel;
import client.legalease.Model.ClientOrderModel;
import client.legalease.Model.DeleteDocModel.DeleteDocModel;
import client.legalease.Model.FinalPaymentModel.FinalPaymentModel;
import client.legalease.Model.InvoiceDetails.InvoiceData;
import client.legalease.Model.ListOfMethodModel.ListOfMethodModel;
import client.legalease.Model.Meetingbookmodel.Meetingschedulemodel;
import client.legalease.Model.MyEasyMoneyModel.MyEasyMoneyModel;
import client.legalease.Model.MyNetworkModel.MyNetworkModel;
import client.legalease.Model.NetrorkDetailsModel.NetworkDetailsModel;
import client.legalease.Model.OrderModel.OrderModel;
import client.legalease.Model.LoginModel.LoginModel;

import client.legalease.Model.MyDocuments.MyDocumentModel;
import client.legalease.Model.NoteModel.NoteModel;
import client.legalease.Model.OrderPAYReqModel;
import client.legalease.Model.PartnerModel.Partnermodel;
import client.legalease.Model.PayTmModel.PayTmModel;
import client.legalease.Model.PaymentCredentialModel.Paymentcredentialmodel;
import client.legalease.Model.PaymentMethodModel.PaymentMethodModel;
import client.legalease.Model.PaymentModel;
import client.legalease.Model.ReachUsModel.ReachUsModel;
import client.legalease.Model.ServiceOfferModel.ServiceOffermodel;
import client.legalease.Model.Servicerequestmodel.ServiceRequestModel;
import client.legalease.Model.SubServicesListModel.SubServiceListModel;
import client.legalease.Model.UploadDocServerModel.Uploaddocumentserver;
import client.legalease.Model.Uploadimageprofile;
import client.legalease.Model.UserPurchaseModel.PurchaseUserDetails;
import client.legalease.Model.QualificationModel.QualificationModel;
import client.legalease.Model.QuizModel.QuestionModel.GetQuestions;
import client.legalease.Model.QuizModel.QuestionSet.SetModel;
import client.legalease.Model.QuizModel.QuizAnswerSubmissionModel;
import client.legalease.Model.QuizModel.QuizFinalResult.QuizResult;
import client.legalease.Model.ReportModel.ReportModel;
import client.legalease.Model.Services.ServiceDetails.ServiceDetails;
import client.legalease.Model.Services.ServicesParentModule.ServiceModel;
import client.legalease.Model.Services.SubServices.SubServiceModel;
import client.legalease.Model.SheetModel.SheetModel;
import client.legalease.Model.StateListModel.StateModel;
import client.legalease.Model.SummeryModel.SummeryModel;
import client.legalease.Model.UpdateUserProfile.UserUpdate;
import client.legalease.Model.UploadBillModel;
import client.legalease.Model.UploadDocModel.UploadDocModel;
import client.legalease.Model.UploadDocumentSpinnerModel.UploadDocumentSpinnerModel;
import client.legalease.Model.UploadedDocumentModel.UploadedDocumentModel;
import client.legalease.Model.UserRegistration.Registration;
import client.legalease.Model.Userupdate;
import client.legalease.Model.VERIFYOTP.OtpVerificationModel;
import client.legalease.Model.VideoCategoryModel.VideoCategory;
import client.legalease.Model.VideoModel.VideoModel;
import client.legalease.Model.WalletModel;
import client.legalease.Model.customerRequestListModel.CustomerRequestListmodel;
import client.legalease.Model.myorder;
import client.legalease.Model.updateprofilemodel.Updateprofilemodel;
import client.legalease.Model.uploaddocumentlistModel.Uploaddocumentlist;
import client.legalease.Model.uploadedDocuments.Uploaddocuments;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import static client.legalease.APIConstant.ApiConstant.ADDPAYMENTMETHOD;
import static client.legalease.APIConstant.ApiConstant.BOOKMEETING;
import static client.legalease.APIConstant.ApiConstant.CHANGEPASSWORDWITHTOKEN;
import static client.legalease.APIConstant.ApiConstant.CHECKCOUPAN;
import static client.legalease.APIConstant.ApiConstant.CLIENTORDERREQUEST;
import static client.legalease.APIConstant.ApiConstant.CUSTOMERASSOCIATELIST;
import static client.legalease.APIConstant.ApiConstant.CUSTOMERPROFILEINFOUPDATE;
import static client.legalease.APIConstant.ApiConstant.CUSTOMERREQUESTLIST;
import static client.legalease.APIConstant.ApiConstant.CUSTOMERREQUESTOFFERLIST;
import static client.legalease.APIConstant.ApiConstant.DELETEPAYMENTMETHOD;
import static client.legalease.APIConstant.ApiConstant.DELETEUPLOADDOCUMENT;
import static client.legalease.APIConstant.ApiConstant.GETACCOUNTMENU;
import static client.legalease.APIConstant.ApiConstant.GETAPPSLIDER;
import static client.legalease.APIConstant.ApiConstant.GETASSOCIATE;
import static client.legalease.APIConstant.ApiConstant.GETBALANCESHEET;
import static client.legalease.APIConstant.ApiConstant.GETINVOICE;
import static client.legalease.APIConstant.ApiConstant.GETLISTOFACCOUNTANT;
import static client.legalease.APIConstant.ApiConstant.GETMAINCHECKSUM;
import static client.legalease.APIConstant.ApiConstant.GETMETHODTYPE;
import static client.legalease.APIConstant.ApiConstant.GETMOBILELOGINOTP;
import static client.legalease.APIConstant.ApiConstant.GETMYACCOUNTANTDETAILS;
import static client.legalease.APIConstant.ApiConstant.GETMYNETWORK;
import static client.legalease.APIConstant.ApiConstant.GETMYORDER;
import static client.legalease.APIConstant.ApiConstant.GETPARENTPACKAGE;
import static client.legalease.APIConstant.ApiConstant.GETPAYMENTMETHODLIST;
import static client.legalease.APIConstant.ApiConstant.GETPROFITANDLOSS;
import static client.legalease.APIConstant.ApiConstant.GETPURCHASEUSERDETAILS;
import static client.legalease.APIConstant.ApiConstant.GETQUALIFICATION;
import static client.legalease.APIConstant.ApiConstant.GETQUESTION;
import static client.legalease.APIConstant.ApiConstant.GETQUESTIONSET;
import static client.legalease.APIConstant.ApiConstant.GETQUIZRESULT;
import static client.legalease.APIConstant.ApiConstant.GETREQUIREDDOCLIST;
import static client.legalease.APIConstant.ApiConstant.GETSCHEDULESREPORT;
import static client.legalease.APIConstant.ApiConstant.GETSERVICEREQ;
import static client.legalease.APIConstant.ApiConstant.GETSTATE;
import static client.legalease.APIConstant.ApiConstant.GETSUBPACKAGE;
import static client.legalease.APIConstant.ApiConstant.GETSUMMARYDATA;
import static client.legalease.APIConstant.ApiConstant.GETTODO;
import static client.legalease.APIConstant.ApiConstant.GETTRANSACTIONLOG;
import static client.legalease.APIConstant.ApiConstant.GETUPLOADEDDOCUMENT;
import static client.legalease.APIConstant.ApiConstant.GETUPLODEDDOCUMENT;
import static client.legalease.APIConstant.ApiConstant.GETUSEREASYMONEY;
import static client.legalease.APIConstant.ApiConstant.GETVIDEOCATEGORY;
import static client.legalease.APIConstant.ApiConstant.GETWALLETDATA;
import static client.legalease.APIConstant.ApiConstant.MAKENOTE;
import static client.legalease.APIConstant.ApiConstant.MEETINGS;
import static client.legalease.APIConstant.ApiConstant.ORDERACCEPTREJECT;
import static client.legalease.APIConstant.ApiConstant.ORDERPAYMENTUPDATE;
import static client.legalease.APIConstant.ApiConstant.ORDERWORKAPPROVAL;
import static client.legalease.APIConstant.ApiConstant.PACKAGEDETAILS;
import static client.legalease.APIConstant.ApiConstant.PAYMENTCALLBACK;
import static client.legalease.APIConstant.ApiConstant.PAYMENTCHECKSUM;
import static client.legalease.APIConstant.ApiConstant.PAYMENTCREDENTIALS;
import static client.legalease.APIConstant.ApiConstant.PAYTMCALLBACK;
import static client.legalease.APIConstant.ApiConstant.REDEEMPOINT;
import static client.legalease.APIConstant.ApiConstant.REMOVECOUPON;
import static client.legalease.APIConstant.ApiConstant.SAVEQUIZANSWER;
import static client.legalease.APIConstant.ApiConstant.SEARCHSERVICE;
import static client.legalease.APIConstant.ApiConstant.UPDATEPAYMENTSTATUS;
import static client.legalease.APIConstant.ApiConstant.UPDATEPROFILE;
import static client.legalease.APIConstant.ApiConstant.UPLOADBILL;
import static client.legalease.APIConstant.ApiConstant.UPLOADBILLX;
import static client.legalease.APIConstant.ApiConstant.UPLOADDOC;
import static client.legalease.APIConstant.ApiConstant.UPLOADDOCUMENTLIST;
import static client.legalease.APIConstant.ApiConstant.UPLOADORDERDOC;
import static client.legalease.APIConstant.ApiConstant.UPLOADPROFILEIMAGE;
import static client.legalease.APIConstant.ApiConstant.USERREGISTRATION;
import static client.legalease.APIConstant.ApiConstant.USERUPDATION;
import static client.legalease.APIConstant.ApiConstant.VERIFYOTP;
import static client.legalease.APIConstant.ApiConstant.VIDEOTUTORIALS;
import static client.legalease.APIConstant.ApiConstant.orderDocumentList;

public interface ApiService {


    @FormUrlEncoded
    @POST(USERREGISTRATION)
    Call<Registration> userRegistration(@FieldMap HashMap<String, String> registrationCrediantial);



    @GET(GETSTATE)
    Call<StateModel> getStateList();

    @FormUrlEncoded
    @POST(USERUPDATION)
    Call<UserUpdate> userUpdation(@FieldMap HashMap<String, String> updateCrediantial);


    @GET(GETQUALIFICATION)
    Call<QualificationModel> getQualificationList();



    @FormUrlEncoded
    @POST(GETMOBILELOGINOTP)
    Call<LoginModel> userLogin(@FieldMap HashMap<String, String> loginCredential);


    @Multipart
    @POST(UPDATEPROFILE)
    Call<UserUpdate> updateProfile(@Part MultipartBody.Part photo,
                                   @Part("name") RequestBody name,
                                   @Part("address") RequestBody address,
                                   @Part("mobile") RequestBody mobile,
                                   @Part("pincode") RequestBody pincode,
                                   @Part("city") RequestBody city,
                                   @Part("state") RequestBody state,
                                   @Part("token") RequestBody token,
                                   @Part("country") RequestBody country);







    @GET(VIDEOTUTORIALS)
    Call<VideoModel> getVideoData(@Query("categoryId") String categoryId, @Query("page") int page);



    @GET(GETPARENTPACKAGE)
    Call<ServiceModel> getSevicesData();

    @GET(GETSUBPACKAGE)
    Call<SubServiceModel> getSubSevicesData(@Query("parentId") String parentId);


    @GET(PACKAGEDETAILS)
    Call<ServiceDetails> getSeviceDetailsData(@Query("id") String id);





    @FormUrlEncoded
    @POST(CHANGEPASSWORDWITHTOKEN)
    Call<LoginModel> changePassword(@FieldMap HashMap<String, String> changePasswordCredential);


    @GET(GETQUESTIONSET)
    Call<SetModel> getQuestionSet(@Query("token") String token);


    @GET(GETQUESTION)
    Call<GetQuestions> getQuestion(@Query("setId") String setId);



    @FormUrlEncoded
    @POST(SAVEQUIZANSWER)
    Call<QuizAnswerSubmissionModel> getQuizResponse(@FieldMap HashMap<String, String> answerCredential);

    @GET(GETQUIZRESULT)
    Call<QuizResult> getMyResult(@Query("token") String token,@Query("setId") String setId);

    @GET(GETVIDEOCATEGORY)
    Call<VideoCategory> getVideoCategory();


    @GET(GETAPPSLIDER)
    Call<AppSliderModel> getAppSlider();



    @GET(GETACCOUNTMENU)
    Call<Account> getAccountData();




    @Multipart
    @POST(UPLOADBILL)
    Call<UploadBillModel> uploadBill(@Part List<MultipartBody.Part> filex,
                                     @Part("userId") RequestBody userId,
                                     @Part("type") RequestBody type);

    @GET(GETLISTOFACCOUNTANT)
    Call<AccountantModel> getAccountantList(@Query("token") String token);

    @FormUrlEncoded
    @POST(MAKENOTE)
    Call<LoginModel> makeNotes(@FieldMap HashMap<String, String> makeNoteCredential);


    @GET(GETTODO)
    Call<NoteModel> getNote(@Query("token") String token, @Query("clientId") String clientId);


    @GET(GETUPLODEDDOCUMENT)
    Call<MyDocumentModel> getDocument(@Query("token") String token,@Query("type") String type,@Query("month") String month,@Query("year") String year);



    @GET(GETSUMMARYDATA)
    Call<SummeryModel> getSummaryData(@Query("token") String token, @Query("month") String month, @Query("year") String year);

    @GET(GETBALANCESHEET)
    Call<SheetModel> getSheetData(@Query("token") String token, @Query("month") String month, @Query("year") String year);


    @GET(GETSCHEDULESREPORT)
    Call<ReportModel> getSheduleData(@Query("token") String token, @Query("month") String month, @Query("year") String year);



    @GET(GETPROFITANDLOSS)
    Call<SheetModel> getProfitLossData(@Query("token") String token, @Query("month") String month, @Query("year") String year);


    @GET(GETWALLETDATA)
    Call<WalletModel> getWalletData(@Query("token") String token);



    @FormUrlEncoded
    @POST(PAYMENTCHECKSUM)
    Call<PayTmModel> paymentCheckSum(@FieldMap HashMap<String, String> makeNoteCredential);

    @FormUrlEncoded
    @POST(PAYTMCALLBACK)
    Call<PayTmModel> paytmCallback(@FieldMap HashMap<String, String> transactionResponse);


    @GET(GETMYORDER)
    Call<AcceptedOrderModel> getMyBills(@Header("Authorization") String token,@Query("page")String page);

    @GET(GETMAINCHECKSUM)
    Call<FinalPaymentModel> getCheckSumData(@Query("token") String token, @Query("orderId") String orderId,@Query("id") String id,@Query("price") String price);


    @GET(CHECKCOUPAN)
    Call<CheckCoupanModel> getCoupanVerification(@Query("token") String token, @Query("coupon") String coupon, @Query("orderId") String orderId);


    @GET(REMOVECOUPON)
    Call<CheckCoupanModel> removeCoupon(@Query("token") String token, @Query("coupon") String coupon, @Query("orderId") String orderId);


    @GET(GETREQUIREDDOCLIST)
    Call<UploadDocumentSpinnerModel> getSpinnerValue(@Query("token") String token,@Query("serviceId") String serviceid,@Query("orderId") String orderId);



    @Multipart
    @POST(UPLOADORDERDOC)
    Call<UploadDocModel> uploadOrderDoc(@Part MultipartBody.Part file,
                                        @Part("token") RequestBody token,
                                        @Part("orderId") RequestBody orderId,
                                        @Part("serviceId") RequestBody serviceId,
                                        @Part("uploadedType") RequestBody uploadedType);





@Multipart
@POST(UPLOADDOC)
Call<Uploaddocumentserver> uploaddoc(@Part MultipartBody.Part file,
                                     @Header("Authorization") String auth,
                                     @Part("orderId") RequestBody orderId,
                                     @Part("uploadedType") RequestBody uploadedtype,
                                     @Part("require_doc_id") RequestBody required_doc_id);



@Multipart
@POST(UPLOADPROFILEIMAGE)
Call<Uploadimageprofile> uploadimageprofile(@Header("Authorization") String token,@Part MultipartBody.Part file);
    @GET(DELETEUPLOADDOCUMENT)
    Call<DeleteDocModel> deleteDoc(@Query("token") String token, @Query("id") String id);


    @GET(GETUPLOADEDDOCUMENT)
    Call<UploadedDocumentModel> getMyUploadedDocument(@Query("token") String token, @Query("serviceId") String serviceId,@Query("orderId") String orderId);


    @GET(GETMYACCOUNTANTDETAILS)
    Call<AccountantDetail> getAccountantDetails(@Query("token") String token, @Query("accountantid") String accountantid);



@GET(UPLOADDOCUMENTLIST)
Call<Uploaddocumentlist> uploaddocumentlist(@Header("Authorization") String token);
    @GET(orderDocumentList)
    Call<Uploaddocuments> geuptdoc(@Header("Authorization") String token,@Query("orderId")String orderid);





    @Multipart
    @POST(UPLOADBILLX)
    Call<UploadBillModel> uploadOrderDocx(@Part MultipartBody.Part filex,
                                          @Part("userId") RequestBody userId,
                                          @Part("type") RequestBody type);





    @FormUrlEncoded
    @POST(PAYMENTCALLBACK)
    Call<LoginModel> updateMyOrder(@FieldMap HashMap<String, String> updatePayment);
@FormUrlEncoded
@POST(ORDERWORKAPPROVAL)
Call<Bookmeetmodel> orderapproval(@Header("Authorization")String Auth,@FieldMap HashMap<String ,String> data);




    @GET(UPDATEPAYMENTSTATUS)
    Call<PaymentModel> uploadMyOrder(@Query("status") String status, @Query("token")String token, @Query("orderId")String orderId,@Query("coupon") String coupon,@Query("iswallet") String iswallet);


    @GET(GETINVOICE)
    Call<InvoiceData> myInvoice(@Query("token")String token, @Query("orderId")String orderId);


    @FormUrlEncoded
    @POST(VERIFYOTP)
    Call<OtpVerificationModel> getOtpVerification(@FieldMap HashMap<String, String> otpCrediantial);
@GET(MEETINGS)
Call<Meetingschedulemodel> meetings(@Query("page")String page,@Header("Authorization") String token);
    @GET(CUSTOMERASSOCIATELIST)
    Call<Partnermodel> getAssociateList (@Query("Page") String page,@Header("Authorization") String token,@Query("lat")String lat,@Query("lon")String lon,@Query("service_id")String sid);
@FormUrlEncoded
    @POST(ORDERACCEPTREJECT)
    Call<ClientOrderModel> orderacceptreject(@Header("Authorization") String token,@FieldMap HashMap<String,String> order);

    @GET(GETSERVICEREQ)
    Call<ServiceRequestModel>  getServiceRequest(@Query("page") String page,@Header("Authorization") String token,@Query("customer_id")String customerid);
@GET(CUSTOMERREQUESTLIST)
Call<CustomerRequestListmodel> customerreqli(@Query("page")String page ,@Header("Authorization")String token);

    @FormUrlEncoded
  @POST(BOOKMEETING)
  Call<Bookmeetmodel> bookmeet(@Header("Authorization") String token, @FieldMap HashMap<String,String> book);
    @GET(CUSTOMERREQUESTOFFERLIST)
    Call<ServiceOffermodel> getServiceOffermodel(@Query("page") String page,@Header("Authorization") String token,@Query("status")String status);
    @FormUrlEncoded
    @POST(CLIENTORDERREQUEST)
    Call<ClientOrderModel> getClientorderRequest (@Header("Authorization") String token, @FieldMap HashMap<String,String> clientrequest);
@FormUrlEncoded
    @POST(CUSTOMERPROFILEINFOUPDATE)
    Call<Updateprofilemodel> customerupdate(@Header("Authorization") String token, @FieldMap HashMap<String,String> userupdate);


    @GET(GETMYNETWORK)
    Call<MyNetworkModel> myNetworkList(@Query("token") String token);

    @GET(GETMETHODTYPE)
    Call<PaymentMethodModel> myPaymentMethodType(@Query("token") String token);

@FormUrlEncoded
@POST(ORDERPAYMENTUPDATE)
    Call<OrderPAYReqModel > orderpaymentrequest(@Header("Authorization") String token, @FieldMap HashMap<String,String> clientrequest);
    @GET(GETPAYMENTMETHODLIST)
    Call<ListOfMethodModel> getmyMethodList(@Query("token") String token,@Query("methodType") String methodType);




    @FormUrlEncoded
    @POST(ADDPAYMENTMETHOD)
    Call<AddAccountDetailsModel> addPaymentMethod(@FieldMap HashMap<String, String> sendOtpCredential);



    @GET(DELETEPAYMENTMETHOD)
    Call<ListOfMethodModel> getMyDeletedData(@Query("token") String token,@Query("methodType") String methodType,@Query("id") String id);




    @FormUrlEncoded
    @POST(REDEEMPOINT)
    Call<AddAccountDetailsModel> getMyRedeemPoint(@FieldMap HashMap<String, String> redeemCredential);


    @GET(GETUSEREASYMONEY)
    Call<MyEasyMoneyModel> getUserEasyMoney(@Query("token") String token);

    @GET(GETTRANSACTIONLOG)
    Call<NetworkDetailsModel> getnetworkDetails(@Query("token") String token, @Query("userId") String userId);


    @GET(GETPURCHASEUSERDETAILS)
    Call<PurchaseUserDetails> getPurchaseUserDetails(@Query("token") String token);

    @GET(SEARCHSERVICE)
    Call<SubServiceListModel> getSubServiceList();

    @GET(GETASSOCIATE)
    Call<ReachUsModel> getAssociateData(@Query("token") String token, @Query("latitude") String latitude, @Query("longitude") String longitude);
    @GET(PAYMENTCREDENTIALS)
    Call<Paymentcredentialmodel> getpaymentcredential(@Header("Authorization")String token);



}
