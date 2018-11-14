package com.example.andre.verifypresency.source.remote.user

import com.example.andre.verifypresency.source.models.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.HashMap

class UserRemoteDataSource {

    fun createUser(user: User, callBack: UserDataSource.SaveUserCallback) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email!!, user.password!!).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                this.saveUser(user, callBack)

            } else {

                task.exception?.message?.let { callBack.onSaveFailed(it) }

            }
        }
    }

    private fun sendVerificationEmail(callBack: UserDataSource.SaveUserCallback) {
        val user = FirebaseAuth.getInstance().currentUser

        user?.sendEmailVerification()?.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                FirebaseAuth.getInstance().signOut()
                callBack.onUserSaved("An email has been sent for validation")
            } else
                callBack.onSaveFailed("Failed to send validation email")

        }
    }

    private fun saveUser(user: User, callBack: UserDataSource.SaveUserCallback) {

        val userDetail = HashMap<String, Any>()

        FirebaseAuth.getInstance().currentUser?.let { userDetail.put("UserId", it.uid) }
        user.firstName?.let { userDetail.put("FirstName", it) }
        user.lastName?.let { userDetail.put("LastName", it) }
        user.orgName?.let { userDetail.put("OrganizationName", it) }
        user.email?.let { userDetail.put("Email", it) }

        val db = FirebaseFirestore.getInstance()

        db.collection("UserDetail").document().set(userDetail)
                .addOnCompleteListener { task ->
                    if (task.isComplete && task.isSuccessful) {
                        this.sendVerificationEmail(callBack)
                    } else {
                        task.exception?.message?.let { callBack.onSaveFailed(it) }

                    }
                }

    }

}
