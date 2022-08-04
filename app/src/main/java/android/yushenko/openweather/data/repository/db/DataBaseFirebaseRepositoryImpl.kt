package android.yushenko.openweather.data.repository.db

import android.util.Log
import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.model.entity.LocationEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class DataBaseFirebaseRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth,
    private val remoteDb: DatabaseReference
) : DataBaseFirebaseRepository {

    private val searchListFlow = MutableSharedFlow<List<LocationEntity>>()

    override fun getSavedListLive(): Flow<List<LocationEntity>> = searchListFlow

    override fun fetchSavedList() {
        remoteDb.child("users")
            .child(firebaseAuth.currentUser?.uid ?: "undefined")
            .child("history")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    CoroutineScope(Dispatchers.Main).launch {
                        searchListFlow.emit(
                            dataSnapshot.children.mapNotNull {
                                it.getValue(LocationEntity::class.java)
                            }
                        )
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                }
            })
    }

    override suspend fun getUserData(): UserInitial? = withTimeout(5000L) {
        remoteDb.child("users")
            .child(firebaseAuth.currentUser?.uid ?: "undefined")
            .child("user")
            .get().await().getValue(UserInitial::class.java)
    }

    override fun userAddData(user: UserInitial) {
        remoteDb.child("users")
            .child(firebaseAuth.currentUser?.uid ?: "undefined")
            .child("user")
            .setValue(user.copy(password = ""))
    }

    override fun addItemHistory(model: LocationEntity) {
        val path = remoteDb.child("users")
            .child(firebaseAuth.currentUser?.uid ?: "undefined")
        path.child("locale").setValue(model)
        path.child("history").child(model.uuid).setValue(model)
    }

    override fun deleteItemHistory(uuid: String) {
        remoteDb.child("users")
            .child(firebaseAuth.currentUser?.uid ?: "undefined")
            .child("history")
            .child(uuid)
            .setValue(null)
    }
}