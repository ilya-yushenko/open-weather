package android.yushenko.openweather.data.repository.oauth

import android.yushenko.openweather.data.model.authentication.UserInitial
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class OauthFirebaseRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth
) : OauthFirebaseRepository {

    override suspend fun createUser(curentUser: UserInitial): AuthResult =
        withTimeout(5000L) {
            firebaseAuth.createUserWithEmailAndPassword(
                curentUser.email,
                curentUser.password
            ).await()
        }

    override suspend fun signIn(currentUser: UserInitial): AuthResult =
        withTimeout(5000L) {
            firebaseAuth.signInWithEmailAndPassword(
                currentUser.email,
                currentUser.password
            ).await()
        }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isSignIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}