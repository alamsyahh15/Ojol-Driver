package com.udacoding.ojodriverlfirebasekotlin.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.udacoding.ojodriverlfirebasekotlin.R
import com.udacoding.ojodriverlfirebasekotlin.login.LoginActivity
import com.udacoding.ojodriverlfirebasekotlin.signup.Users
import com.udacoding.ojodriverlfirebasekotlin.utils.Constan
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity


class ProfileFragment : Fragment() {

    var auth: FirebaseAuth? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(Constan.tb_Uaser)

        val query = myRef.orderByChild("uid").equalTo(auth?.uid)
        query.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                if(p0 != null) {
                    for (issue in p0.children) {

                        val user = Users()

                        val data = issue?.getValue(Users::class.java)
                        user.longitude = data?.longitude
                        user.latitude = data?.latitude
                        user.name = data?.name
                        user.hp = data?.hp
                        user.email = data?.email


                        showProfile(user)

                    }

                }
            }

        })


    }

    private fun showProfile(data: Users?) {

        profilEmail.text = data?.email
        profileName.text = data?.name
        profilhp.text = data?.hp

        profileSignout.onClick {

            auth?.signOut()

            startActivity<LoginActivity>()

        }

    }


}
