package android.yushenko.openweather.data.model.firebase

import android.util.Log
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.model.search.Search
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class DataBaseFirebase {
    private lateinit var database: DatabaseReference
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getLocalDataBase(onLocalFirebaseCallback: OnLocalFirebaseCallback) {
        Log.i("TAG", "Reading")
        database = Firebase.database.reference

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val search: Search? = snapshot.child("users").child(auth.currentUser.uid).child("locale").getValue<Search>()
                search?.let { onLocalFirebaseCallback.onReadingLocalFirebase(it) }
            }
        }
        database.addValueEventListener(postListener)
    }

    fun getListLocalsDataBase(onListLocalsFirebaseCallback: OnListLocalsFirebaseCallback) {
        database = Firebase.database.reference
        val listHistory = database.child("users").child(auth.currentUser.uid).child("history")

        listHistory.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val list : MutableList<Search> = mutableListOf()
                snapshot.children.forEach {
                    val search: Search? = it.getValue<Search>()
                    search?.keyDB = it.key
                    if (search != null) { list.add(search) }
//                    Log.i("TAG", "City " + search?.name)
                }
                onListLocalsFirebaseCallback.onGetListLocalsFirebase(list)
            }

        })
    }

    fun getEmailUser(onEmailUserFirebaseCallback: OnEmailUserFirebaseCallback) {
        onEmailUserFirebaseCallback.onGetEmailUserFirebase(auth.currentUser.email)
    }

    fun userAddData(user: User) {
        database = Firebase.database.reference
        user.password = null
        database.child("users").child(auth.currentUser.uid).child("user").setValue(user)
    }

    fun writeDataBase(search: Search) {
        database = Firebase.database.reference
        database.child("users").child(auth.currentUser.uid).child("locale").setValue(search)

        database.child("users").child(auth.currentUser.uid).child("history").child(getDate()).setValue(search)
    }

    fun deleteItemsDataBase(search: Search) {
        database = Firebase.database.reference
        database.child("users").child(auth.currentUser.uid).child("history").child(search.keyDB.toString()).setValue(null)
    }

    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
        return dateFormat.format(Date())
    }
}

interface OnLocalFirebaseCallback {
    fun onReadingLocalFirebase(search: Search)
}

interface OnListLocalsFirebaseCallback {
    fun onGetListLocalsFirebase(searches: List<Search>)
}

interface OnEmailUserFirebaseCallback {
    fun onGetEmailUserFirebase(email: String)
}