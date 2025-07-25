package com.govahan.data


import com.govahan.activities.ReferNEarnResponse
import com.govahan.activities.TransactionReportResponse
import com.govahan.activities.loadercompletedbookingdetails.UploadDocumentsResponse
import com.govahan.model.*
import com.govahan.model.addfavouritelocation.AddFavouriteLocationModel
import com.govahan.model.authorisedFranchisesDisttListModel.AuthorisedFranchisesDisttListResponseModel
import com.govahan.model.authorisedFranchisesPincodeListModel.AuthorisedFranchisesPinCodeListResponseModel
import com.govahan.model.authorisedFranchisesStateListModel.AuthorisedFranchisesStateListResponseModel
import com.govahan.model.authorizedfranchisesmodel.AuthorizedFranchisesResponseModel
import com.govahan.model.bookingloadermodel.BookingLoaderResponseModel
import com.govahan.model.bookingpassengelmodel.BookingPassengerResponseModel
import com.govahan.model.bookingreviewget.BookingReviewModel
import com.govahan.model.bookingreviewpassengerget.BookingReviewPassengerModel
import com.govahan.model.cancelledloadertriphistorymodel.CancelledLoaderTripHistoryResponseModel
import com.govahan.model.cancelledpassengertriphistorymodel.CancelledPassengerTripHistoryResponseModel
import com.govahan.model.completedloadertriphistorymodel.CompletedLoaderTripHistoryResponseModel
import com.govahan.model.completedpassengertriphistorymodel.CompletedPassengerTripHistoryResponseModel
import com.govahan.model.contactusmodel.ContactUsModel
import com.govahan.model.deletefavlocation.DeleteFavLocationModel
import com.govahan.model.getPrivacyPolicy.PrivacyPolicyModel
import com.govahan.model.getfavouritelocation.GetFavouriteLocationModel
import com.govahan.model.getprofile.GetUserProfileModel
import com.govahan.model.homebannermodel.HomeBannerResponseModel
import com.govahan.model.loaderAddRaiseComplaintModel.LoaderAddRaiseComplaintResponseModel
import com.govahan.model.loaderComplaintListDetailModel.LoaderComplaintListDetailResponseModel
import com.govahan.model.loaderComplaintlistmodel.LoaderComplaintListResponseModel
import com.govahan.model.loaderInvoiceDetailModel.LoaderInvoiceDetailResponseModel
import com.govahan.model.loaderInvoiceDownloadModel.LoaderDownloadInvoiceUrlResponseModel
import com.govahan.model.loaderRideCompletedModel.LoaderRideCompletedResponseModel
import com.govahan.model.loaderaddwalletmodel.LoaderAddWalletResponseModel
import com.govahan.model.loadercancelreasonmodel.LoaderCancelReasonListResponseModel
import com.govahan.model.loadercanceltripmodel.LoaderTripCancelResponseModel
import com.govahan.model.loaderinvoicelistmodel.LoaderInvoiceListResponseModel
import com.govahan.model.loaderlivetrackingmodel.LoaderLiveTrackingResponseModel
import com.govahan.model.loaderongoinghistorydetailmodel.LoaderOngoingHistoryDetailResponseModel
import com.govahan.model.loaderpaymentsuccessmodel.LoaderPaymentSuccessResponseModel
import com.govahan.model.loaderrescheduletripmodel.LoaderRescheduleTripResponseModel
import com.govahan.model.loadertripmanagementmodel.LoaderTripManagementDetailResponse
import com.govahan.model.loaderwalletfiltermodel.LoaderWalletFilterResponseModel
import com.govahan.model.loaderwalletlistmodel.LoaderWalletListResponseModel
import com.govahan.model.loginOtpModel.LoginOtpResponseModel
import com.govahan.model.loginResponse.LoginResponseModel
import com.govahan.model.myoffersmodel.MyOffersResponseModel
import com.govahan.model.noOfTyrePModel.GetNoOfTyrePModel
import com.govahan.model.notificationmodel.NotificationResponseModel
import com.govahan.model.ongoingPassengerTripHistoryModel.OngoingPassengerTripHistoryResponseModel
import com.govahan.model.ongoingloadertriphistorymodel.OngoingLoaderTripHistoryResponseModel
import com.govahan.model.passengerAddRaiseComplaintModel.PassengerAddRaiseComplaintResponseModel
import com.govahan.model.passengerComplaintlistdetailmodel.PassengerComplaintListDetailResponseModel
import com.govahan.model.passengerComplaintlistmodel.PassengerComplaintListResponseModel
import com.govahan.model.passengerInvoiceDetailModel.PassengerInvoiceDetailResponseModel
import com.govahan.model.passengerOngoinghistorydetailmodel.PassengerOngoingHistoryDetailResponseModel
import com.govahan.model.passengerRideCompletedModel.PassengerRideCompletedResponseModel
import com.govahan.model.passengeraddratingmodel.PassengerAddRatingResponseModel
import com.govahan.model.passengercancelreasonmodel.PassengerCancelReasonListResponseModel
import com.govahan.model.passengercanceltripmodel.PassengerTripCancelResponseModel
import com.govahan.model.passengerdownloadinvoicemodel.PassengerDownloadInvoiceUrlResponseModel
import com.govahan.model.passengerinvoicelistmodel.PassengerInvoiceListResponseModel
import com.govahan.model.passengerlivetrackingmodel.PassengerLiveTrackingResponseModel
import com.govahan.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessResponseModel
import com.govahan.model.passengertripmanagementmodel.PassengerTripManagementDetailResponse
import com.govahan.model.searchPassengerVehicle.SearchPassengerVehicleResponseModel
import com.govahan.model.searchauthorisedfranchisesmodel.SearchAuthorisedFranchisesResponseModel
import com.govahan.model.searchvehiclemodel.SearchVehicleResponseModel
import com.govahan.model.seatingcapacitymodel.GetSeatingCapacityModel
import com.govahan.model.sendmailmodel.LoaderSendMailResponseModel
import com.govahan.model.settingsmsemailmodel.SettingSmsEmailResponseModel
import com.govahan.model.settingwhatsappmodel.SettingWhatsappResponseModel
import com.govahan.model.transportownerget.TransportOwnerModel
import com.govahan.model.tripmanagementloadermodel.LoaderTripManagementResponseModel
import com.govahan.model.tripmanagementpassengermodel.PassengerTripManagementResponseModel
import com.govahan.model.truckbodytypeget.TruckBodyTypeModel
import com.govahan.model.truckcapacityget.TruckCapacityGetModel
import com.govahan.model.truckpricefor_get.TruckPriceForModel
import com.govahan.model.trucktypegetmodel.TruckTypeModel
import com.govahan.model.vehicletypemodel.GetVehicleTypeModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface MainRepository {
  /*  suspend fun userRegistration(name : String, email : String, mobile : String, device_id : String,
                                 device_type : String , device_name : String, device_token : String) : Response<OtpModel>
*/
    suspend fun verifyOTPForLogIn(otp : String, mobile : String,referal:String) : Response<LoginOtpResponseModel>

    suspend fun checksumApi(
        token : String,
        amount: String,
        mobile: String,
        user_id: String,
    ): Response<ChecksumResponse>
    suspend fun paymentcheckapi(
        token : String,
        transaction_id: String,
    ): Response<PaymentSuccessMsgResponse>

    suspend fun payment_status_check(
        token: String,
        transaction_id: String
    ): Response<RazorpaystatusResponse>
    suspend fun add_my_wallet(token: String,amount : String,transactionId:String): Response<WalletResponse>
    suspend fun razorpayPayment(
        token: String,
        tripId : String,
        bookingFor : String,// 1: Loader, 2: Passenger
        paymentMode : String,// !: Online, 2: Wallet
        transactionId : String,// If payment from wallet so accept string "walletTrasactionId"
        invoice : String,// If payment from wallet so accept string "walletInvoice"
        currency : String,
        amount : String,
    ): Response<RazorpaystatusResponse>

//    suspend fun razorpayPayment(
//        token: String,
//        pick_up_location: String,
//        pick_up_lat: String,
//        pick_up_long: String,
//        drop_location: String,
//        drop_lat: String,
//        drop_long: String,
//        vechicle_id: String,
//        fare: String,total_fare : String,
//        payment_mode: String,
//        booking_date: String,
//        booking_time: String,
//        driver_id: String,
//        dis: String,
//        body_type: String,
//        capacity: String,
//        distance: String,
//        vehicle_numbers: String,
//        transaction_id: String,
//        payment_status: String,
//        currency: String,
//        booking_relation_id: String
//    ): Response<RazorpaystatusResponse>

    suspend fun userLogin(mobile : String,
                          device_id: String,
                          device_type: String,
                          device_name: String,
                          device_token: String,referal_code: String) : Response<LoginResponseModel>

    suspend fun getUserProfile(token : String) : Response<GetUserProfileModel>
    suspend fun purchase_plan_from_walletApi(header: String,amount:String,transaction_type:String): Response<LoaderAddWalletResponseModel>
    suspend fun updateUserProfile(token : String, name : RequestBody, email : RequestBody,
                              mobile_number : RequestBody,
                              address : RequestBody, device_token : RequestBody,
                                  device_type : RequestBody, device_id : RequestBody,
                              profile_image : MultipartBody.Part?) : Response<EditProfileResponse>

    suspend fun contactUsApi(token : String) : Response<ContactUsModel>

    suspend fun privacyPolicyApi(token : String) : Response<PrivacyPolicyModel>

    suspend fun termsAndConditions(token : String) : Response<PrivacyPolicyModel>
    suspend fun truckTypeApi(token : String) : Response<TruckTypeModel>
    suspend fun truckCapacityApi(token : String) : Response<TruckCapacityGetModel>
    suspend fun truckBodyTypeApi(token : String) : Response<TruckBodyTypeModel>
    suspend fun truckPriceForApi(token : String) : Response<TruckPriceForModel>

    suspend fun searchLoaderVehicleApi(token : String,
                                       pickup_lat: String,
                                       pickup_long: String,
                                       dropup_lat: String,
                                       dropup_long: String,
                                       loader_type: String,
                                       vehicle_category: String,
//                                       body_type: String,
//                                       seat: String,
//                                       wheels: String,
                                       booking_date: String,
                                       booking_time: String,
                              ) : Response<SearchVehicleResponseModel>

    suspend fun searchLoaderDetailApi(token : String ,
                                      id : String,
                                      task : String,
                                      pickup_location : String,
                                      pickup_lat : String,
                                      pickup_long : String,
                                      dropup_location : String,
                                      dropup_lat : String,
                                      dropup_long : String,
                                      booking_date : String,
                                      booking_time : String,
                                      available : String) : Response<BookingReviewModel>

    suspend fun owner_driverDetailApi(token : String ,
                                      driver_id : String) : Response<TransportOwnerModel>

    suspend fun addRatingApi(token : String ,
                                      driver_id : String,rating : String ,desc : String ) : Response<AddRatingModel>

    suspend fun getRatingApi(token : String,
                             driver_id : String
                        ) : Response<ReviewsModelClass>
    suspend fun my_wallet_payment(header: String,type:String,transaction_id:String,amount: String): Response<LoaderAddWalletResponseModel>

    suspend fun addFavouriteLocationApi(token : String,
                                       pickup_lat : String,
                                       pickup_long : String,
                                       dropup_lat : String,
                                       dropup_long : String
                                       ) : Response<AddFavouriteLocationModel>

    suspend fun getFavouriteLocationApi(token : String) : Response<GetFavouriteLocationModel>

    suspend fun vehicleTypeApi(token : String,type:String) : Response<GetVehicleTypeModel>

    suspend fun seatingCapacityApi(token : String) : Response<GetSeatingCapacityModel>

    suspend fun noOfTyrePApi(token : String,type: String) : Response<GetNoOfTyrePModel>
     suspend fun refer_userApi(
        token: String,
    ): Response<ReferNEarnResponse>
    suspend fun deleteFavLocationApi(token : String, id : String) : Response<DeleteFavLocationModel>
   suspend fun search_loader_driver_review(token : String, driver_id : String) : Response<ReviewsModelClass>
     suspend fun aboutUs(token: String): Response<PrivacyPolicyModel>
     suspend fun calcelation_refund_policy(token: String): Response<PrivacyPolicyModel>

    suspend fun searchPassengerVehicleApi(token : String,
                                          pickup_lat : String,
                                          pickup_long : String,
                                          dropup_lat : String,
                                          dropup_long : String,
                                          vehicle_type : String,
                                          tyers : String,
                                          booking_date : String,
                                          booking_time : String,
                                          pickup_location : String,
                                          dropup_location : String,) : Response<SearchPassengerVehicleResponseModel>

    suspend fun searchPassengerDetailApi(token : String ,
                                         pickup_lat : String,
                                         pickup_long : String,
                                         dropup_lat : String,
                                         dropup_long : String,
                                         vehicle_type : String,
                                         seat : String,
                                         tyers : String,
                                         booking_date : String,
                                         booking_time : String,
                                         vehicle_id : String,
                                         id : String) : Response<BookingReviewPassengerModel>

    suspend fun bookingPassengerApi(token : String ,
                                    pick_up_location : String,
                                    pick_up_lat : String,
                                    pick_up_long : String,
                                    drop_location : String,
                                    drop_lat : String,
                                    drop_long : String,
                                    vechicle_id : String,
                                    fare : String,
                                    payment_mode : String,
                                    booking_date : String,
                                    booking_time : String,
                                    driver_id : String,booking_relation_id:String) : Response<BookingPassengerResponseModel>

    suspend fun bookingLoaderApi(
        token: String,
        pick_up_location: String,
        pick_up_lat: String,
        pick_up_long: String,
        drop_location: String,
        drop_lat: String,
        drop_long: String,
        vechicle_id: String,
        fare: String,
        payment_mode: String,
        booking_date: String,
        booking_time: String,
        driver_id: String,
        body_type: String,
        capacity: String,
        distance: String,
        vehicle_numbers: String,
        booking_relation_id: String,
    ) : Response<BookingLoaderResponseModel>

    suspend fun getNotificationListApi(token : String) : Response<NotificationResponseModel>

    suspend fun getLoaderTripManagementApi(
        token : String,
        forPassenger : String,
        bookingString : String
    ) : Response<LoaderTripManagementResponseModel>
    suspend fun getPassengerTripManagementApi(token : String) : Response<PassengerTripManagementResponseModel>

    suspend fun loaderTripManagementDetailApi(token : String ,
                                              booking_id : String) : Response<LoaderTripManagementDetailResponse>

    suspend fun getLoaderCancelReasonListApi(token : String) : Response<LoaderCancelReasonListResponseModel>

    suspend fun loaderTripCancelApi(token : String ,
                                              booking_id : String, reasons_id : String,message : String  ) : Response<LoaderTripCancelResponseModel>

  suspend fun loaderRescheduleTripApi(token : String ,
                                              booking_id : String, booking_date : String,booking_time : String  ) : Response<LoaderRescheduleTripResponseModel>

  suspend fun loaderRideCompletedApi(token : String ,
                                              booking_id : String ) : Response<LoaderRideCompletedResponseModel>

  suspend fun loaderOngoingBookingTripHistoryApi(token : String ) : Response<LoaderTripManagementResponseModel>
    suspend fun loaderPendingBookingTripHistoryApi(token: String): Response<OngoingLoaderTripHistoryResponseModel>
  suspend fun loaderCompletedBookingTripHistoryApi(token : String ) : Response<CompletedLoaderTripHistoryResponseModel>

  suspend fun loaderOngoingHistoryDetailApi(token : String, booking_id : String ) : Response<LoaderOngoingHistoryDetailResponseModel>
  suspend fun UploadDocumentsResponseApi(token : String, booking_id : String ) : Response<UploadDocumentsResponse>
     suspend fun complaint_resolved(token: String,
                                            id: String,
                                            type: String
    ): Response<LoaderAddRaiseComplaintResponseModel>

  suspend fun loaderInvoiceListApi(token : String ) : Response<LoaderInvoiceListResponseModel>

    suspend fun loaderInvoiceDetailApi(token : String, invoice_numbers : String ) : Response<LoaderInvoiceDetailResponseModel>

    suspend fun loaderPaymentSuccessApi(token : String ,
                                        pick_up_location : String,
                                        pick_up_lat : String,
                                        pick_up_long : String,
                                        drop_location : String,
                                        drop_lat : String,
                                        drop_long : String,
                                        vechicle_id : String,
                                        fare : String,total_fare : String,
                                        payment_mode : String,
                                        booking_date : String,
                                        booking_time : String,
                                        driver_id : String,
                                        dis : String,
                                        body_type : String,
                                        capacity : String,
                                        distance : String,
                                        vehicle_numbers : String,
                                        transaction_id : String,
                                        payment_status : String,
                                        currency : String,booking_relation_id:String) : Response<LoaderPaymentSuccessResponseModel>
    suspend fun loader_payment_wallet   (token : String ,
                                        pick_up_location : String,
                                        pick_up_lat : String,
                                        pick_up_long : String,
                                        drop_location : String,
                                        drop_lat : String,
                                        drop_long : String,
                                        vechicle_id : String,
                                        fare : String,total_fare : String,
                                        payment_mode : String,
                                        booking_date : String,
                                         booking_time : String,
                                        driver_id : String,
                                        dis : String,
                                        body_type : String,
                                        capacity : String,
                                        distance : String,
                                        vehicle_numbers : String,
                                        payment_status : String,
                                        currency : String,booking_relation_id:String)
    : Response<LoaderPaymentSuccessResponseModel>
  suspend fun passenenger_payment_wallet(token : String ,
                                        pick_up_location : String,
                                        pick_up_lat : String,
                                        pick_up_long : String,
                                        drop_location : String,
                                        drop_lat : String,
                                        drop_long : String,
                                        vechicle_id : String,
                                        fare : String,total_fare : String,
                                        payment_mode : String,
                                        booking_date : String,
                                         booking_time : String,
                                        driver_id : String,
                                        dis : String,
                                        body_type : String,
                                        capacity : String,
                                        distance : String,
                                        vehicle_numbers : String,
                                        payment_status : String,
                                        currency : String,booking_relation_id:String) : Response<LoaderPaymentSuccessResponseModel>
    suspend fun my_wallet_list_download(token: String): Response<LoaderWalletListResponseModel>
    suspend fun loaderWalletAddMoneyApi(token : String ,
                                        amount : String) : Response<LoaderAddWalletResponseModel>
     suspend fun loaderWalletListApi(token: String,date:String,transaction_type: String): Response<LoaderWalletListResponseModel>

    suspend fun UpcomingsTripHistory(header: String, forPassenger :String, bookingStatus :String): Response<LoaderTripManagementResponseModel>
     suspend fun user_online_transaction_historyApi(token: String): Response<TransactionReportResponse>

    suspend fun loaderWalletFilterApi(token : String , date : String ,transaction_type:String) : Response<LoaderWalletFilterResponseModel>

    suspend fun raiseComplaintApi(token : String , booking_id : String,subject : String , com_message : String ) : Response<LoaderAddRaiseComplaintResponseModel>
    suspend fun raiseComplaintList(token : String ) : Response<LoaderComplaintListResponseModel>
    suspend fun loaderComplaintListDetailApi(token : String , booking_id : String ) : Response<LoaderComplaintListDetailResponseModel>

    suspend fun bookingTracking(token : String , booking_id : String ) : Response<LoaderLiveTrackingResponseModel>

    suspend fun passengerPaymentSuccessApi(token : String ,
                                        pick_up_location : String,
                                        pick_up_lat : String,
                                        pick_up_long : String,
                                        drop_location : String,
                                        drop_lat : String,
                                        drop_long : String,
                                        vechicle_id : String,
                                        fare : String,total_fare : String,
                                        payment_mode : String,
                                        booking_date : String,booking_time:String,
                                        driver_id : String,
                                        dis : String,
                                        body_type : String,
                                        capacity : String,
                                        distance : String,
                                        vehicle_numbers : String,
                                        transaction_id : String,
                                        payment_status : String,
                                        currency : String,booking_relation_id:String) : Response<PassengerPaymentSuccessResponseModel>





    suspend fun passengerTripManagementDetailApi(token : String ,
                                              booking_id : String) : Response<PassengerTripManagementDetailResponse>

    suspend fun getPassengerCancelReasonListApi(token : String) : Response<PassengerCancelReasonListResponseModel>

    suspend fun passengerTripCancelApi(token : String ,
                                    booking_id : String, reasons_id : String,message : String  ) : Response<PassengerTripCancelResponseModel>

    suspend fun getDashboardBannertApi(token : String  ) : Response<HomeBannerResponseModel>

    suspend fun passengerRideCompletedApi(token : String ,
                                       booking_id : String ) : Response<PassengerRideCompletedResponseModel>

    suspend fun passengerAddRatingApi(token : String ,
                             driver_id : String,rating : String ,desc : String, user_id : String ) : Response<PassengerAddRatingResponseModel>

    suspend fun getAuthorizedFranchisesApi(token : String) : Response<AuthorizedFranchisesResponseModel>
    suspend fun vendor_number_vehicle_list(token : String,id:String) : Response<AuthorizedFranchiseDetailsApi>

    suspend fun passengerInvoiceListApi(token : String ) : Response<PassengerInvoiceListResponseModel>

    suspend fun passengerOngoingBookingTripHistoryApi(token : String ) : Response<OngoingPassengerTripHistoryResponseModel>

    suspend fun passengerPendingBookingTripHistoryApi(token: String): Response<OngoingPassengerTripHistoryResponseModel>
    suspend fun passengerCompletedBookingTripHistoryApi(token : String ) : Response<CompletedPassengerTripHistoryResponseModel>

    suspend fun passengerOngoingHistoryDetailApi(token : String, booking_id : String ) : Response<PassengerOngoingHistoryDetailResponseModel>

    suspend fun passengerInvoiceDetailApi(token : String, invoice_numbers : String ) : Response<PassengerInvoiceDetailResponseModel>

    suspend fun passengerLiveTrackingApi(token : String , booking_id : String ) : Response<PassengerLiveTrackingResponseModel>
    suspend fun passengerAddRaiseComplaintApi(token : String , booking_id : String , com_message : String ) : Response<PassengerAddRaiseComplaintResponseModel>

    suspend fun passengerComplaintListApi(token : String ) : Response<PassengerComplaintListResponseModel>
    suspend fun passengerComplaintListDetailApi(token : String , booking_id : String ) : Response<PassengerComplaintListDetailResponseModel>
    suspend fun getMyOffersApi(token : String ) : Response<MyOffersResponseModel>
    suspend fun searchAuthorizedFranchisesApi(token : String, state_id : String, district_id : String, pin_code : String ) : Response<SearchAuthorisedFranchisesResponseModel>
    suspend fun sendMailLoaderInvoiceApi(token : String, booking_id : String ) : Response<LoaderSendMailResponseModel>
    suspend fun sendMailPassengerInvoiceApi(token : String, booking_id : String) : Response<LoaderSendMailResponseModel>
    suspend fun loaderCancelledBookingTripHistoryApi(token : String ) : Response<CancelledLoaderTripHistoryResponseModel>
    suspend fun passengerCancelledBookingTripHistoryApi(token : String ) : Response<CancelledPassengerTripHistoryResponseModel>
    suspend fun passengerDownloadInvoiceUrlApi(token : String , booking_id : String  ) : Response<PassengerDownloadInvoiceUrlResponseModel>
    suspend fun loaderDownloadInvoiceUrlApi(token : String , booking_id : String  ) : Response<LoaderDownloadInvoiceUrlResponseModel>
    suspend fun loader_invoice_url_second(token : String , booking_id : String  ) : Response<LoaderDownloadInvoiceUrlResponseModel>
    suspend fun passenenger_invoice_url_second(token : String , booking_id : String  ) : Response<LoaderDownloadInvoiceUrlResponseModel>
    suspend fun getAuthorisedFranchisesStateListApi(token : String) : Response<AuthorisedFranchisesStateListResponseModel>
    suspend fun getAuthorisedFranchisesDisttListApi(token : String , state_id : String  ) : Response<AuthorisedFranchisesDisttListResponseModel>
    suspend fun getAuthorisedFranchisesPincodeListApi(token : String , city_id : String  ) : Response<AuthorisedFranchisesPinCodeListResponseModel>



    suspend fun settingsWhatsappUpdatesApi(token : String , state_id : String  ) : Response<SettingWhatsappResponseModel>
    suspend fun settingsSmsEmailUpdatesApi(token : String , state_id : String  ) : Response<SettingSmsEmailResponseModel>


     suspend fun get_user_check_statusApi(
        token: String
    ): Response<AuthorisedFranchisesStateListResponseModel>


     suspend fun reschedule_trip_passenger(
        token: String,
        booking_id: String,
        booking_date: String,
        booking_time: String
    ): Response<LoaderRescheduleTripResponseModel>





}


