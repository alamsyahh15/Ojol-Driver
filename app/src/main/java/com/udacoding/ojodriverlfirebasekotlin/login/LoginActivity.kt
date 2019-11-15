package com.udacoding.ojodriverlfirebasekotlin.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.udacoding.ojodriverlfirebasekotlin.auth.AutentikasiHpActivity
import com.udacoding.ojodriverlfirebasekotlin.R
import com.udacoding.ojodriverlfirebasekotlin.signup.SignUpActivity
import com.udacoding.ojodriverlfirebasekotlin.signup.Users
import com.udacoding.ojodriverlfirebasekotlin.utama.HomeActivity
import com.udacoding.ojodriverlfirebasekotlin.utils.Constan
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {
    var googleSignInClient: GoogleSignInClient? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        signUpbuttonGmail.onClick {
            signIn()

        }

        sigUplink.onClick {
            startActivity<SignUpActivity>()
        }


        loginSignIn.onClick {

            if (loginUsername.text.isNotEmpty() && loginPassword.text.isNotEmpty()) {

                authUserSignIn(loginUsername.text.toString(), loginPassword.text.toString())

            }
        }
    }


    private fun authUserSignIn(email: String, pas: String) {

//        auth = FirebaseAuth.getInstance()
        var status: Boolean? = null

        auth?.signInWithEmailAndPassword(email, pas)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                startActivity<HomeActivity>()
                finish()
            } else {


                toast("login failed")

            }
        }


    }


    private fun signIn() {
        val gson = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gson)


        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 4)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {


        val credential1 = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth?.signInWithCredential(credential1)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    checkDatabase(task.result?.user?.uid,acct)

                } else {

                }

                // ...
            }





    }

    private fun checkDatabase(uid: String?, acct: GoogleSignInAccount?) {


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(Constan.tb_Uaser)
        val query =myRef.orderByChild("uid").equalTo(auth?.uid)

        query.addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                val users = p0.getValue(Users::class.java)

                if(users?.uid != null){

                    startActivity<HomeActivity>()



                }
                else{

                    acct?.displayName?.let { acct.email?.let { it1 -> insertUser(it, it1, "08",uid) } }


                }


            }
        })
    }


    fun insertUser(name: String, email: String, hp: String, uid: String?): Boolean {


        val token = FirebaseInstanceId.getInstance().token

        val user = Users()
        user.email = email
        user.name = name
        user.hp = hp
        user.uid = uid
        user.latitude = "0.0"
        user.longitude = "0.0"
        user.token = token
        val database = FirebaseDatabase.getInstance()
        val key = database.reference.push().key


        val myRef = database.getReference(Constan.tb_Uaser)



        key?.let { myRef.child(it).setValue(user) }

        startActivity<AutentikasiHpActivity>(Constan.Key to key)
        return true

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 4) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)

                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {

            }
        }
    }


}
