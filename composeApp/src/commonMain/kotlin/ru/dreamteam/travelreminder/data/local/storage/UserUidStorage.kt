package ru.dreamteam.travelreminder.data.local.storage

expect class UserUidStorage{
    fun getUserUid(): String?
    fun setUserUid(uid: String?)

    fun getIdToken(): String?
    fun setIdToken(token: String?)

    fun getRefreshToken(): String?
    fun setRefreshToken(token: String?)
}