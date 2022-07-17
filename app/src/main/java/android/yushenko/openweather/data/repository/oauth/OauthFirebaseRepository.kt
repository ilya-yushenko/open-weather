package android.yushenko.openweather.data.repository.oauth

import android.yushenko.openweather.data.model.authentication.UserInitial
import com.google.firebase.auth.AuthResult

interface OauthFirebaseRepository {
    suspend fun createUser(user: UserInitial): AuthResult
    suspend fun signIn(user: UserInitial): AuthResult
    fun signOut()
    fun isSignIn(): Boolean
}