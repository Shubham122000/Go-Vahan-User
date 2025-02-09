package com.govahanuser.com.data


import com.govahanuser.com.activities.ReferNEarnResponse
import com.govahanuser.com.activities.TransactionReportResponse
import com.govahanuser.com.activities.loadercompletedbookingdetails.UploadDocumentsResponse
import com.govahanuser.com.model.*
import com.govahanuser.com.model.addfavouritelocation.AddFavouriteLocationModel
import com.govahanuser.com.model.authorisedFranchisesDisttListModel.AuthorisedFranchisesDisttListResponseModel
import com.govahanuser.com.model.authorisedFranchisesPincodeListModel.AuthorisedFranchisesPinCodeListResponseModel
import com.govahanuser.com.model.authorisedFranchisesStateListModel.AuthorisedFranchisesStateListResponseModel
import com.govahanuser.com.model.authorizedfranchisesmodel.AuthorizedFranchisesResponseModel
import com.govahanuser.com.model.bookingloadermodel.BookingLoaderResponseModel
import com.govahanuser.com.model.bookingpassengelmodel.BookingPassengerResponseModel
import com.govahanuser.com.model.bookingreviewget.BookingReviewModel
import com.govahanuser.com.model.bookingreviewpassengerget.BookingReviewPassengerModel
import com.govahanuser.com.model.cancelledloadertriphistorymodel.CancelledLoaderTripHistoryResponseModel
import com.govahanuser.com.model.cancelledpassengertriphistorymodel.CancelledPassengerTripHistoryResponseModel
import com.govahanuser.com.model.completedloadertriphistorymodel.CompletedLoaderTripHistoryResponseModel
import com.govahanuser.com.model.completedpassengertriphistorymodel.CompletedPassengerTripHistoryResponseModel
import com.govahanuser.com.model.contactusmodel.ContactUsModel
import com.govahanuser.com.model.deletefavlocation.DeleteFavLocationModel
import com.govahanuser.com.model.getPrivacyPolicy.PrivacyPolicyModel
import com.govahanuser.com.model.getfavouritelocation.GetFavouriteLocationModel
import com.govahanuser.com.model.getprofile.GetUserProfileModel
import com.govahanuser.com.model.homebannermodel.HomeBannerResponseModel
import com.govahanuser.com.model.loaderAddRaiseComplaintModel.LoaderAddRaiseComplaintResponseModel
import com.govahanuser.com.model.loaderComplaintListDetailModel.LoaderComplaintListDetailResponseModel
import com.govahanuser.com.model.loaderComplaintlistmodel.LoaderComplaintListResponseModel
import com.govahanuser.com.model.loaderInvoiceDetailModel.LoaderInvoiceDetailResponseModel
import com.govahanuser.com.model.loaderInvoiceDownloadModel.LoaderDownloadInvoiceUrlResponseModel
import com.govahanuser.com.model.loaderRideCompletedModel.LoaderRideCompletedResponseModel
import com.govahanuser.com.model.loaderaddwalletmodel.LoaderAddWalletResponseModel
import com.govahanuser.com.model.loadercancelreasonmodel.LoaderCancelReasonListResponseModel
import com.govahanuser.com.model.loadercanceltripmodel.LoaderTripCancelResponseModel
import com.govahanuser.com.model.loaderinvoicelistmodel.LoaderInvoiceListResponseModel
import com.govahanuser.com.model.loaderlivetrackingmodel.LoaderLiveTrackingResponseModel
import com.govahanuser.com.model.loaderongoinghistorydetailmodel.LoaderOngoingHistoryDetailResponseModel
import com.govahanuser.com.model.loaderpaymentsuccessmodel.LoaderPaymentSuccessResponseModel
import com.govahanuser.com.model.loaderrescheduletripmodel.LoaderRescheduleTripResponseModel
import com.govahanuser.com.model.loadertripmanagementmodel.LoaderTripManagementDetailResponse
import com.govahanuser.com.model.loaderwalletfiltermodel.LoaderWalletFilterResponseModel
import com.govahanuser.com.model.loaderwalletlistmodel.LoaderWalletListResponseModel
import com.govahanuser.com.model.loginOtpModel.LoginOtpResponseModel
import com.govahanuser.com.model.loginResponse.LoginResponseModel
import com.govahanuser.com.model.myoffersmodel.MyOffersResponseModel
import com.govahanuser.com.model.noOfTyrePModel.GetNoOfTyrePModel
import com.govahanuser.com.model.notificationmodel.NotificationResponseModel
import com.govahanuser.com.model.ongoingPassengerTripHistoryModel.OngoingPassengerTripHistoryResponseModel
import com.govahanuser.com.model.ongoingloadertriphistorymodel.OngoingLoaderTripHistoryResponseModel
import com.govahanuser.com.model.passengerAddRaiseComplaintModel.PassengerAddRaiseComplaintResponseModel
import com.govahanuser.com.model.passengerComplaintlistdetailmodel.PassengerComplaintListDetailResponseModel
import com.govahanuser.com.model.passengerComplaintlistmodel.PassengerComplaintListResponseModel
import com.govahanuser.com.model.passengerInvoiceDetailModel.PassengerInvoiceDetailResponseModel
import com.govahanuser.com.model.passengerOngoinghistorydetailmodel.PassengerOngoingHistoryDetailResponseModel
import com.govahanuser.com.model.passengerRideCompletedModel.PassengerRideCompletedResponseModel
import com.govahanuser.com.model.passengeraddratingmodel.PassengerAddRatingResponseModel
import com.govahanuser.com.model.passengercancelreasonmodel.PassengerCancelReasonListResponseModel
import com.govahanuser.com.model.passengercanceltripmodel.PassengerTripCancelResponseModel
import com.govahanuser.com.model.passengerdownloadinvoicemodel.PassengerDownloadInvoiceUrlResponseModel
import com.govahanuser.com.model.passengerinvoicelistmodel.PassengerInvoiceListResponseModel
import com.govahanuser.com.model.passengerlivetrackingmodel.PassengerLiveTrackingResponseModel
import com.govahanuser.com.model.passengerpaymentsuccessmodel.PassengerPaymentSuccessResponseModel
import com.govahanuser.com.model.passengertripmanagementmodel.PassengerTripManagementDetailResponse
import com.govahanuser.com.model.searchPassengerVehicle.SearchPassengerVehicleResponseModel
import com.govahanuser.com.model.searchauthorisedfranchisesmodel.SearchAuthorisedFranchisesResponseModel
import com.govahanuser.com.model.searchvehiclemodel.SearchVehicleResponseModel
import com.govahanuser.com.model.seatingcapacitymodel.GetSeatingCapacityModel
import com.govahanuser.com.model.sendmailmodel.LoaderSendMailResponseModel
import com.govahanuser.com.model.settingsmsemailmodel.SettingSmsEmailResponseModel
import com.govahanuser.com.model.settingwhatsappmodel.SettingWhatsappResponseModel
import com.govahanuser.com.model.transportownerget.TransportOwnerModel
import com.govahanuser.com.model.tripmanagementloadermodel.LoaderTripManagementResponseModel
import com.govahanuser.com.model.tripmanagementpassengermodel.PassengerTripManagementResponseModel
import com.govahanuser.com.model.truckbodytypeget.TruckBodyTypeModel
import com.govahanuser.com.model.truckcapacityget.TruckCapacityGetModel
import com.govahanuser.com.model.truckpricefor_get.TruckPriceForModel
import com.govahanuser.com.model.trucktypegetmodel.TruckTypeModel
import com.govahanuser.com.model.vehicletypemodel.GetVehicleTypeModel
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

  suspend fun loaderOngoingBookingTripHistoryApi(token : String ) : Response<OngoingLoaderTripHistoryResponseModel>
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

    suspend fun UpcomingsTripHistory(header: String, forPassenger :String, bookingStatus :String): Response<OngoingLoaderTripHistoryResponseModel>
     suspend fun user_online_transaction_historyApi(token: String): Response<TransactionReportResponse>

    suspend fun loaderWalletFilterApi(token : String , date : String ,transaction_type:String) : Response<LoaderWalletFilterResponseModel>

    suspend fun loaderAddRaiseComplaintApi(token : String , booking_id : String , com_message : String ) : Response<LoaderAddRaiseComplaintResponseModel>
    suspend fun loaderComplaintListApi(token : String ) : Response<LoaderComplaintListResponseModel>
    suspend fun loaderComplaintListDetailApi(token : String , booking_id : String ) : Response<LoaderComplaintListDetailResponseModel>

    suspend fun loaderLiveTrackingApi(token : String , booking_id : String ) : Response<LoaderLiveTrackingResponseModel>

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


