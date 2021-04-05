package android.yushenko.openweather.data.model.firebase


import android.util.Log
import android.yushenko.openweather.model.authentication.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthentication {
    private val auth: FirebaseAuth = Firebase.auth

    fun createUser(user: User, onFirebaseCreatedUser: OnFirebaseCreatedUser) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        signOut()
                        onFirebaseCreatedUser.onCreatedUserOk(true)
                    } else {
                        onFirebaseCreatedUser.onCreatedUserOk(false)
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    }
                }
    }

    fun signIn(user: User, onFirebaseSignIn: OnFirebaseSignInUser) {
        auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onFirebaseSignIn.onSignInUserOk(true)
                    } else {
                        onFirebaseSignIn.onSignInUserOk(false)
                    }
                }
    }

    fun signOut() {
        Firebase.auth.signOut()
    }

    fun isSignIn(): Boolean {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            return true
        }
        return false
    }
}

interface OnFirebaseCreatedUser {
    fun onCreatedUserOk(boolean: Boolean)
}

interface OnFirebaseSignInUser {
    fun onSignInUserOk(boolean: Boolean)
}