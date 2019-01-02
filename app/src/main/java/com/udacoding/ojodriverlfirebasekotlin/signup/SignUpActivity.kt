package com.udacoding.ojodriverlfirebasekotlin.signup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.udacoding.ojodriverlfirebasekotlin.login.LoginActivity
import com.udacoding.ojodriverlfirebasekotlin.R
import com.udacoding.ojodriverlfirebasekotlin.utils.Constan
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class SignUpActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        auth = FirebaseAuth.getInstance()

        signUpbutton.onClick {

            if (signUpEmail.text.isNotEmpty() &&
                signUpHp.text.isNotEmpty() &&
                signUpName.text.isNotEmpty() &&
                signUpPassword.text.isNotEmpty() &&
                signUpPasswordConfirm.text.isNotEmpty()
            ) {


               autUserSignUp(signUpEmail.text.toString(), signUpPassword.text.toString())



                }


        }

    }

    fun autUserSignUp(email: String, pass: String): Boolean? {

        auth = FirebaseAuth.getInstance()
        var status: Boolean? = null

        auth?.createUserWithEmailAndPassword(email, pass)?.addOnCompleteListener { task ->

            if (task.isSuccessful) {

                if(insertUser(signUpName.text.toString(),
                        signUpEmail.text.toString(),
                        signUpHp.text.toString(),task.result.user.uid)!!
                ){

                    startActivity<LoginActivity>()
                }

                status = true

            } else {

                status = false

            }


        }

        return status


    }

    fun insertUser(name: String, email: String, hp: String, uid: String): Boolean {

        val token = FirebaseInstanceId.getInstance().token

        var user = Users()
        user.uid = uid
        user.name = name
        user.email = email
        user.hp = hp
        user.active = true
        user.latitude = "0.0"
        user.longitude = "0.0"
        user.token = token
        val database = FirebaseDatabase.getInstance()
        var key = database.reference.push().key
        val myRef = database.getReference(Constan.tb_Uaser)


        myRef.child(key).setValue(user)

        return true

    }





}
