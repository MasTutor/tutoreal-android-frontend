package com.mastutor.tutoreal.data.remote

import com.google.gson.annotations.SerializedName
data class TutorsResponse(

	@field:SerializedName("tutors")
	val tutors: Tutors,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class TutorItem(

	@field:SerializedName("Specialization")
	val specialization: String,

	@field:SerializedName("Nama")
	val nama: String,

	@field:SerializedName("Categories")
	val categories: String,

	@field:SerializedName("UserId")
	val userId: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("picture")
	val picture: String
)

data class Tutors(

	@field:SerializedName("items")
	val items: List<TutorItem>
)
data class ProfileResponse(
	@field:SerializedName("user_data")
	val profile: Profile,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)
data class Profile(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("photoURL")
	val photoURL: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("noTelp")
	val noTelp: String
)
data class RegisterResponse(

	@field:SerializedName("signupToken")
	val signupToken: SignupToken? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class SignupToken(

	@field:SerializedName("access token")
	val accessToken: String? = null
)
data class LoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Token(

	@field:SerializedName("access token")
	val accessToken: String? = null
)

data class LoginResult(

	@field:SerializedName("userId")
	val userId: UserId? = null,

	@field:SerializedName("token")
	val token: Token? = null
)

data class UserId(

	@field:SerializedName("Username")
	val username: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
data class TutorResponse(
	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("detail_tutor")
	val detailTutor: TutorDetail
)

data class TutorDetail(
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("UserId")
	val userId: String,

	@field:SerializedName("Nama")
	val nama: String,

	@field:SerializedName("hasPenis")
	val gender: Int,

	@field:SerializedName("AgesRanges")
	val ages: String,

	@field:SerializedName("Specialization")
	val specialization: String,

	@field:SerializedName("Categories")
	val categories: String,

	@field:SerializedName("AboutMe")
	val about: String,

	@field:SerializedName("SkillsAndExperience")
	val skills: String,

	@field:SerializedName("picture")
	val picture: String,

	@field:SerializedName("price")
	val price: String
)
data class ScheduleResponse(

	@field:SerializedName("history_data")
	val historyData: List<HistoryDataItem?>? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class HistoryDataItem(

	@field:SerializedName("TutorName")
	val tutorName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("SessionName")
	val sessionName: String? = null,

	@field:SerializedName("Date")
	val date: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)