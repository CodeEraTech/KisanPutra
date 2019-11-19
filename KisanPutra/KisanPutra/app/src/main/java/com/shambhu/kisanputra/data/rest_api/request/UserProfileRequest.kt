package com.shambhu.kisanputra.data.rest_api.request

 class UserProfileRequest(){
    var first_name: String? = ""
    var last_name: String? = ""
    var age: Int? = 0
    var charges: Int? = 0
    var categories: List<String>? = null
    var user_profile: String? = ""
    var online_status: Int? = 0
    var notification_status: Int? = 0
    var educations: List<String>? = null
    var total_experience: String? = "0"
    var gender: String? = "Male"
    var language: String? = null
}