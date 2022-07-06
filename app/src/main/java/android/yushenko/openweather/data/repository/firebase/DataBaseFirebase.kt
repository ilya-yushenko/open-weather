package android.yushenko.openweather.data.repository.firebase

import android.util.Log
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.model.search.Search
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DataBaseFirebase @Inject constructor(
        private val auth: FirebaseAuth,
) {
    val listLiveData = MutableLiveData<List<Search>>()

    fun loadHistory() {
        val list: MutableList<Search> = mutableListOf()
        val database = Firebase.database.reference.child("users").child(auth.currentUser.uid).child("history")
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                dataSnapshot.children.forEach {
                    val search = it.getValue<Search>()
                    search?.keyDB = it.key
                    search?.let { value -> list.add(value) }
                }
                listLiveData.postValue(list.asReversed())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }

        database.addValueEventListener(listener)
    }

    fun userAddData(user: User) {
        user.password = null
        Firebase.database.reference
                .child("users").child(auth.currentUser.uid).child("user").setValue(user)
    }

    fun getUserData() = flow {
        val snapshot = Firebase.database.reference
                .child("users").child(auth.currentUser.uid).child("user").get().await()
        emit(snapshot.getValue<User>())
    }

    fun addItemHistory(search: Search) {
        val database = Firebase.database.reference
        database.child("users").child(auth.currentUser.uid).child("locale").setValue(search)
        database.child("users").child(auth.currentUser.uid).child("history").child(getDate()).setValue(search)
    }

    fun deleteItemHistory(search: Search) {
        val database = Firebase.database.reference
        database.child("users").child(auth.currentUser.uid).child("history").child(search.keyDB.toString()).setValue(null)
    }

    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
        return dateFormat.format(Date())
    }
}