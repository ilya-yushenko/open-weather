package android.yushenko.openweather.data.repository.firebase

import android.util.Log
import android.yushenko.openweather.data.model.authentication.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthentication {

    private val auth: FirebaseAuth = Firebase.auth
    private val database = DataBaseFirebase()

    suspend fun createUser(user: User) : Boolean {
        var created = false
        auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener {
                    created = if (it.isSuccessful) { database.userAddData(user); signOut(); true } else false
                }.await()
        return created
    }

    suspend fun signIn(user: User) : Boolean {
        var singin = false
        auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener{ singin = it.isSuccessful
                    Log.i("TAG", "Log: $singin")
                }.await()
        return singin
    }

    fun getEmailUser() = auth.currentUser?.email

    fun signOut() = Firebase.auth.signOut()

    fun isSignIn() = auth.currentUser != null
}