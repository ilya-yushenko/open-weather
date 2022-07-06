package android.yushenko.openweather.data.repository.firebase

import android.yushenko.openweather.data.model.authentication.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.BroadcastChannel
import javax.inject.Inject

class FirebaseAuthentication @Inject constructor(
        private val auth: FirebaseAuth,
        private val database: DataBaseFirebase,
) {

    val chanelRegister = BroadcastChannel<Boolean>(1)
    val chanelLogin = BroadcastChannel<Boolean>(1)

    fun createUser(user: User) =
            auth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            database.userAddData(user)
                            signOut()
                            chanelRegister.offer(true)
                        } else
                            chanelRegister.offer(false)
                    }

    fun signIn(user: User) =
            auth.signInWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener {
                        chanelLogin.offer(it.isSuccessful)
                    }


    fun signOut() = Firebase.auth.signOut()

    fun isSignIn() = auth.currentUser != null
}