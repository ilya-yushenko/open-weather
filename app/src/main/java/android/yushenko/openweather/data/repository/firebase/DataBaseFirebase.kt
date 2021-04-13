package android.yushenko.openweather.data.repository.firebase

import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.model.search.Search
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class DataBaseFirebase {
    private lateinit var database: DatabaseReference
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun getListLocals() : List<Search> {
        database = Firebase.database.reference.child("users").child(auth.currentUser.uid).child("history")
        val list : MutableList<Search> = mutableListOf()

        database.get().await().children.forEach {
            val search = it.getValue<Search>()
            search?.keyDB = it.key
            if (search != null) { list.add(search) }
        }
        return list
    }

    fun userAddData(user: User) {
        database = Firebase.database.reference
        user.password = null
        database.child("users").child(auth.currentUser.uid).child("user").setValue(user)
    }

    fun addItemHistory(search: Search) {
        database = Firebase.database.reference
        database.child("users").child(auth.currentUser.uid).child("locale").setValue(search)
        database.child("users").child(auth.currentUser.uid).child("history").child(getDate()).setValue(search)
    }

    fun deleteItemHistory(search: Search) {
        database = Firebase.database.reference
        database.child("users").child(auth.currentUser.uid).child("history").child(search.keyDB.toString()).setValue(null)
    }

    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
        return dateFormat.format(Date())
    }
}