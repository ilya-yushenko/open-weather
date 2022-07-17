package android.yushenko.openweather.data.repository.oauth

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class OauthFirebaseRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth
) : OauthFirebaseRepository {

    override suspend fun createUser(userInitial: UserInitial): AuthResult =
        withTimeout(5000L) {
            firebaseAuth.createUserWithEmailAndPassword(
                userInitial.email,
                userInitial.password
            ).await()
        }

    override suspend fun signIn(userInitial: UserInitial): AuthResult =
        withTimeout(5000L) {
            firebaseAuth.signInWithEmailAndPassword(
                userInitial.email,
                userInitial.password
            ).await()
        }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isSignIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}