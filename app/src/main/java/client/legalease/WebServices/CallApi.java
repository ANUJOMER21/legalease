package client.legalease.WebServices;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import client.legalease.BillUploadActivty;
import client.legalease.HomeActivity;
import client.legalease.LoginActivity;
import client.legalease.MakeNoteActivity;
import client.legalease.Model.AddAccountDetailsModel.AddAccountDetailsModel;
import client.legalease.Model.LoginModel.LoginModel;

import client.legalease.Model.PartnerModel.Partnermodel;
import client.legalease.Model.PayTmModel.PayTmModel;
import client.legalease.Model.QuizModel.QuizAnswerSubmissionModel;
import client.legalease.Model.UpdateUserProfile.UserUpdate;
import client.legalease.Model.UploadBillModel;
import client.legalease.Model.UploadDocModel.UploadDocModel;
import client.legalease.Model.UploadDocServerModel.Uploaddocumentserver;
import client.legalease.Model.Uploadimageprofile;
import client.legalease.Model.UserRegistration.Registration;
import client.legalease.Model.VERIFYOTP.OtpVerificationModel;
import client.legalease.MultipleCameraImageActivity;
import client.legalease.OtpActivty;
import client.legalease.PaymentActivity;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.PurchaceServiceActicty;
import client.legalease.QuizTestActivity;
import client.legalease.RedeemActivtiy;
import client.legalease.RegistrationActivity;
import client.legalease.UpdateActivity;
import client.legalease.UploadDocument;
import client.legalease.UploadDocument2;
import client.legalease.UserMoreDetailActivity;
import client.legalease.ViewAllPartner;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static client.legalease.WebServices.ApiClient.BASE_URL;


public class CallApi {

    Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .disableHtmlEscaping()
            .setLenient()
            .create();

    private Gson getGson = new Gson();
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    ApiService service = retrofit.create(ApiService.class);



    public void requestSignUp(final RegistrationActivity context, HashMap<String, String> registrationCrediantial) {
        Call<Registration> call = service.userRegistration(registrationCrediantial);

        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
//                dialogHide();
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Responce_SignUp(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }







    public void updateUserProfile(final UserMoreDetailActivity context, HashMap<String, String> updateCrediantial) {
        Call<UserUpdate> call = service.userUpdation(updateCrediantial);

        call.enqueue(new Callback<UserUpdate>() {
            @Override
            public void onResponse(Call<UserUpdate> call, Response<UserUpdate> response) {
//                dialogHide();
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Responce_Update(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<UserUpdate> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }



    public void requestLogIn(final LoginActivity context, HashMap<String, String> loginCredential) {
        Call<LoginModel> call = service.userLogin(loginCredential);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){
                    String status =response.body().getStatus();

                    context.Response_Login(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }




    public void upDateProfile(final UpdateActivity context, MultipartBody.Part photo, RequestBody name, RequestBody address, RequestBody mobile, RequestBody pincode, RequestBody city, RequestBody state, RequestBody token, RequestBody country) {


        Call<UserUpdate> call = service.updateProfile(photo, name,address,mobile,pincode,city,state, token, country);
        call.enqueue(new Callback<UserUpdate>() {
            @Override
            public void onResponse(Call<UserUpdate> call, Response<UserUpdate> response) {
                UserUpdate serverResponse = response.body();
                if (serverResponse != null) {

                    context.Response_uploadProfile(response.body());

                    Log.d("dsvcsd",response.body().toString());


                } else {
                    Log.v("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<UserUpdate> call, Throwable t) {
                Log.d("sdsdv", String.valueOf(t));

            }
        });

    }








    public void requestChangePassword(final HomeActivity context, HashMap<String, String> changePasswordCredential) {
        Call<LoginModel> call = service.changePassword(changePasswordCredential);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Response_ChangePassword(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }








    public void requestQuiz(final QuizTestActivity context, HashMap<String, String> answerCredential) {
        Call<QuizAnswerSubmissionModel> call = service.getQuizResponse(answerCredential);

        call.enqueue(new Callback<QuizAnswerSubmissionModel>() {
            @Override
            public void onResponse(Call<QuizAnswerSubmissionModel> call, Response<QuizAnswerSubmissionModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Response_quiz(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<QuizAnswerSubmissionModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }





    public void uploadBill(final BillUploadActivty context, List<MultipartBody.Part> filex, RequestBody userId,RequestBody type) {

        Call<UploadBillModel> call = service.uploadBill(filex,userId,type);
        call.enqueue(new Callback<UploadBillModel>() {
            @Override
            public void onResponse(Call<UploadBillModel> call, Response<UploadBillModel> response) {
                UploadBillModel serverResponse = response.body();
                if (serverResponse != null) {

                    context.Response_uploadBill(response.body());

                    Log.d("dsvcsd",response.body().toString());


                } else {
                    Toast.makeText(context,"No response",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadBillModel> call, Throwable t) {
                Log.d("sdsdv", String.valueOf(t));
                context.checkMessage();

            }
        });

    }





    public void uploadBillCamera(final MultipleCameraImageActivity context, List<MultipartBody.Part> filex, RequestBody userId, RequestBody type) {

        Call<UploadBillModel> call = service.uploadBill(filex,userId,type);
        call.enqueue(new Callback<UploadBillModel>() {
            @Override
            public void onResponse(Call<UploadBillModel> call, Response<UploadBillModel> response) {
                UploadBillModel serverResponse = response.body();
                if (serverResponse != null) {

                    context.Response_uploadBill(response.body());

                    Log.d("dsvcsd",response.body().toString());


                } else {
                    Toast.makeText(context,"No response",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadBillModel> call, Throwable t) {
                Log.d("sdsdv", String.valueOf(t));
                context.checkMessage();

            }
        });

    }



    public void makeNotes(final MakeNoteActivity context, HashMap<String, String> makeNoteCredential) {
        Call<LoginModel> call = service.makeNotes(makeNoteCredential);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Response_MakeNote(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }








    public void paytmRequest(final PurchaceServiceActicty context, HashMap<String, String> paytmCredential) {
        Call<PayTmModel> call = service.paymentCheckSum(paytmCredential);

        call.enqueue(new Callback<PayTmModel>() {
            @Override
            public void onResponse(Call<PayTmModel> call, Response<PayTmModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Response_MakeNote(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<PayTmModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }


    public void transsactionResponseData(final PurchaceServiceActicty context, HashMap<String, String> transactionResponse) {

        Call<PayTmModel> call = service.paytmCallback(transactionResponse);

        call.enqueue(new Callback<PayTmModel>() {
            @Override
            public void onResponse(Call<PayTmModel> call, Response<PayTmModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Response_transaction(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<PayTmModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });

    }


    public void uploadDoc(final UploadDocument context, MultipartBody.Part file,RequestBody token, RequestBody orderId, RequestBody serviceId, RequestBody uploadedType) {

        Call<UploadDocModel> call = service.uploadOrderDoc(file, token,orderId,serviceId,uploadedType);
        call.enqueue(new Callback<UploadDocModel>() {
            @Override
            public void onResponse(Call<UploadDocModel> call, Response<UploadDocModel> response) {
                UploadDocModel serverResponse = response.body();
                if (serverResponse != null) {

                    context.Response_uploadDoc(response.body());

                    Log.d("dsvcsd",response.body().toString());


                } else {
                    Log.v("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<UploadDocModel> call, Throwable t) {
                Log.d("sdsdv", String.valueOf(t));

            }
        });

    }

public void Uploadprofimage(final UpdateActivity updateActivity,MultipartBody.Part file,String token){
Call<Uploadimageprofile> uploadimageprofileCall=service.uploadimageprofile(token,file);
uploadimageprofileCall.enqueue(new Callback<Uploadimageprofile>() {
    @Override
    public void onResponse(Call<Uploadimageprofile> call, Response<Uploadimageprofile> response) {
        if(response.body()!=null){
          if( response.body().getStatus().equals("success")){
              Toast.makeText(updateActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                updateActivity.uploaddoc(response.body());
              CommonSharedPreference commonSharedPreference=new CommonSharedPreference(updateActivity);
              commonSharedPreference.setProfilephoto(updateActivity,response.body().getImg());


          }
          else {
              Toast.makeText(updateActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
              updateActivity.uploaddoc(response.body());
          }
        }
        else {
            Toast.makeText(updateActivity, "null", Toast.LENGTH_SHORT).show();
            updateActivity.uploaddoc(response.body());
        }
        Log.d("callapires", "onResponse: "+response);

    }

    @Override
    public void onFailure(Call<Uploadimageprofile> call, Throwable t) {
        Toast.makeText(updateActivity, "Check your internet connection", Toast.LENGTH_SHORT).show();

    }
});
}

public void uploaddoc2(final UploadDocument2 uploadDocument2,MultipartBody.Part file,String token,RequestBody orderid,RequestBody uploadedtype,RequestBody requiredocid){
        Call<Uploaddocumentserver> uploaddocumentserverCall=service.uploaddoc(file,token,orderid,uploadedtype,requiredocid);
        uploaddocumentserverCall.enqueue(new Callback<Uploaddocumentserver>() {
            @Override
            public void onResponse(Call<Uploaddocumentserver> call, Response<Uploaddocumentserver> response) {
                Uploaddocumentserver uploaddocumentserver=response.body();
                if(response.body()!=null){

                    if(response.body().getStatus().equals("success")){
                        Toast.makeText(uploadDocument2, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                   uploadDocument2.Response_uploadDoc(uploaddocumentserver);

                    }
                    else {
                        Toast.makeText(uploadDocument2, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        uploadDocument2.Response_uploadDoc(uploaddocumentserver);
                    }
                }
                else {
                    Toast.makeText(uploadDocument2, "null", Toast.LENGTH_SHORT).show();
                    uploadDocument2.Response_uploadDoc(uploaddocumentserver);
                }
                Log.d("callapires", "onResponse: "+response);
            }

            @Override
            public void onFailure(Call<Uploaddocumentserver> call, Throwable t) {
                Toast.makeText(uploadDocument2, "Check your internet connection", Toast.LENGTH_SHORT).show();

                Log.d("callapires", "onResponse: "+t.toString());
            }
        });
}


    public void uploadBillDoc(final BillUploadActivty context, MultipartBody.Part filex,RequestBody userId,RequestBody type) {

        Call<UploadBillModel> call = service.uploadOrderDocx(filex,userId,type);
        call.enqueue(new Callback<UploadBillModel>() {
            @Override
            public void onResponse(Call<UploadBillModel> call, Response<UploadBillModel> response) {
                UploadBillModel serverResponse = response.body();
                if (serverResponse != null) {

                    context.Response_billUplaod(response.body());

                    Log.d("dsvcsd",response.body().toString());


                } else {
                    Log.v("Response", serverResponse.toString());
                    context.closeDialog();
                }
            }

            @Override
            public void onFailure(Call<UploadBillModel> call, Throwable t) {
                Log.d("sdsdv", String.valueOf(t));
                context.closeDialog();

            }
        });

    }










    public void updatePaytmOrder(final PaymentActivity context, HashMap<String, String> updatePayment) {
        Call<LoginModel> call = service.updateMyOrder(updatePayment);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){
                    String status =response.body().getStatus();

                    context.Response_updatedOrder(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }


    public void requestOtpVerification(final OtpActivty context, final HashMap<String, String> otpCrediantial) {

        Log.d("asdbhjas", String.valueOf(otpCrediantial));


        Call<OtpVerificationModel> call = service.getOtpVerification(otpCrediantial);
        Log.d("asdbhjas", String.valueOf(otpCrediantial));

        call.enqueue(new Callback<OtpVerificationModel>() {
            @  Override
            public void onResponse(Call<OtpVerificationModel> call, Response<OtpVerificationModel> response) {
                Toast.makeText(context,"success",Toast.LENGTH_SHORT).show();


                if (response.body() != null){

                    context.Responce_otpVerification(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<OtpVerificationModel> call, Throwable t) {
                Toast.makeText(context,"not succes",Toast.LENGTH_SHORT).show();

            }
        });


    }






    public void requestSendOtp(final RedeemActivtiy context, HashMap<String, String> sendOtpCredential) {
        Call<AddAccountDetailsModel> call = service.addPaymentMethod(sendOtpCredential);

        call.enqueue(new Callback<AddAccountDetailsModel>() {
            @Override
            public void onResponse(Call<AddAccountDetailsModel> call, Response<AddAccountDetailsModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){
                    String status =response.body().getStatus();

                    context.Response_Login(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<AddAccountDetailsModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }



    public void requestVerifyOtp(final RedeemActivtiy context, HashMap<String, String> sendOtpCredential) {
        Call<AddAccountDetailsModel> call = service.addPaymentMethod(sendOtpCredential);

        call.enqueue(new Callback<AddAccountDetailsModel>() {
            @Override
            public void onResponse(Call<AddAccountDetailsModel> call, Response<AddAccountDetailsModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){
                    String status =response.body().getStatus();

                    context.Response_verifyOtp(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<AddAccountDetailsModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }




    public void requestRedeem(final RedeemActivtiy context, HashMap<String, String> redeemCredential) {
        Call<AddAccountDetailsModel> call = service.getMyRedeemPoint(redeemCredential);

        call.enqueue(new Callback<AddAccountDetailsModel>() {
            @Override
            public void onResponse(Call<AddAccountDetailsModel> call, Response<AddAccountDetailsModel> response) {
                Log.d("vgsdaghsa", String.valueOf(response));
                if (response.body() != null){

                    context.Response_redeem(response.body());
                    Log.d("DATA123", response.body().toString());

                }else {
                    context.clossDialog();
                    Log.d("OTH_RES", "ERROR14 : Server Error");

                }
            }

            @Override
            public void onFailure(Call<AddAccountDetailsModel> call, Throwable t) {
                Log.e("OTH_RES_Error", t.toString());
                context.clossDialog();
                t.printStackTrace();

            }
        });
    }


}




