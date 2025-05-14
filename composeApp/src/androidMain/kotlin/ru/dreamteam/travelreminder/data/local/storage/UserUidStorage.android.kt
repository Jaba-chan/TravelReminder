package ru.dreamteam.travelreminder.data.local.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

actual class UserUidStorage(context: Context) {

    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual fun getUserUid(): String? = sharedPreferences.getString(USER_UID_KEY, null)
    actual fun setUserUid(uid: String?) {
        val editor = sharedPreferences.edit()
        if (uid == null) {
            editor.remove(USER_UID_KEY)
        } else {
            editor.putString(USER_UID_KEY, uid)
        }
        editor.apply()
    }

    actual fun getIdToken(): String? = sharedPreferences.getString(ID_TOKEN_KEY, null)
    actual fun setIdToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null) {
            editor.remove(ID_TOKEN_KEY)
        } else {
            editor.putString(ID_TOKEN_KEY, token)
        }
        editor.apply()
    }

    actual fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    actual fun setRefreshToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null) {
            editor.remove(REFRESH_TOKEN_KEY)
        } else {
            editor.putString(REFRESH_TOKEN_KEY, token)
        }
        editor.apply()
    }

    actual fun clear() {
        setUserUid(null)
        setIdToken(null)
        setRefreshToken(null)
    }

    companion object {
        private const val USER_UID_KEY = "user_uid"
        private const val ID_TOKEN_KEY = "id_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }


}

