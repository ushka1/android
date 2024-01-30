package com.example.authn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val emailLoginButton = view.findViewById<Button>(R.id.button_login)
        emailLoginButton.setOnClickListener {
            emailLoginHandler()
        }

        val googleLoginButton = view.findViewById<Button>(R.id.button_google_login)
        googleLoginButton.setOnClickListener {
            googleLoginHandler()
        }

        val switchRegisterButton = view.findViewById<Button>(R.id.button_switch_register)
        switchRegisterButton.setOnClickListener {
            switchRegisterHandler()
        }

        val facebookLoginButton = view.findViewById<Button>(R.id.button_facebook_login)
        facebookLoginButton.setOnClickListener {
            facebookLoginHandler()
        }

        return view
    }

    private fun switchRegisterHandler() {
        val fragment = RegisterFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    // =============== Email and password =============== //

    private fun emailLoginHandler() {
        val emailEditText = requireView().findViewById<EditText>(R.id.input_email)
        val passwordEditText = requireView().findViewById<EditText>(R.id.input_password)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                context,
                "Please fill in email and password.",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        val activity = requireActivity()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.i("ABC", "signInWithEmail:success", task.exception)
                } else {
                    Log.i("ABC", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    // =============== Google =============== //

    private fun googleLoginHandler() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        googleLauncher.launch(signInIntent)
    }

    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleGoogleResult(task)
            }
        }

    private fun handleGoogleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("ABC", "signInWithGoogle:success")
                    } else {
                        Log.d("ABC", "signInWithGoogle:failure")
                        Toast
                            .makeText(
                                requireActivity(),
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
            }
        } else {
            Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // =============== Facebook =============== //

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun facebookLoginHandler() {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d("ABC", "signInWithFacebook:success")
                    handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() {
                    Log.d("ABC", "signInWithFacebook:cancelled")
                }

                override fun onError(error: FacebookException) {
                    Log.d("ABC", "signInWithFacebook:failure")
                }
            })

        LoginManager.getInstance()
            .logInWithReadPermissions(this, callbackManager, listOf("email", "public_profile"))
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("ABC", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("ABC", "signInWithCredential:success")
                } else {
                    Log.w("ABC", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    // ==================================================
    // Code for Google One Tap Login. Left for reference.
    // ==================================================

    //    private lateinit var oneTapClient: SignInClient
    //    private lateinit var signInRequest: BeginSignInRequest
    //    private val REQ_ONE_TAP = 123 // any unique to activity
    //
    //    private fun googleLoginHandler() {
    //        oneTapClient = Identity.getSignInClient(requireActivity())
    //        signInRequest = BeginSignInRequest.builder()
    //            .setGoogleIdTokenRequestOptions(
    //                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
    //                    .setSupported(true)
    //                    .setServerClientId(getString(R.string.web_client_id))
    //                    .setFilterByAuthorizedAccounts(false)
    //                    .build()
    //            )
    //            .build()
    //
    //        oneTapClient.beginSignIn(signInRequest)
    //            .addOnSuccessListener(requireActivity()) { result ->
    //                try {
    //                    startIntentSenderForResult(
    //                        result.pendingIntent.intentSender, REQ_ONE_TAP,
    //                        null, 0, 0, 0, null
    //                    )
    //                } catch (e: IntentSender.SendIntentException) {
    //                    Log.e("ABC", "Couldn't start One Tap UI: ${e.localizedMessage}")
    //                }
    //            }
    //            .addOnFailureListener(requireActivity()) { e ->
    //                e.localizedMessage?.let { Log.d("ABC", it) }
    //            }
    //    }
    //
    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    //        super.onActivityResult(requestCode, resultCode, data)
    //
    //        when (requestCode) {
    //            REQ_ONE_TAP -> {
    //                try {
    //                    val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
    //                    val idToken = googleCredential.googleIdToken
    //                    when {
    //                        idToken != null -> {
    //                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
    //                            auth.signInWithCredential(firebaseCredential)
    //                                .addOnCompleteListener(requireActivity()) { task ->
    //                                    if (task.isSuccessful) {
    //                                        Log.d("ABC", "signInWithCredential:success")
    //                                    } else {
    //                                        Log.w("ABC", "signInWithCredential:failure", task.exception)
    //                                    }
    //                                }
    //                        }
    //
    //                        else -> {
    //                            Log.d("ABC", "No ID token!")
    //                        }
    //                    }
    //                } catch (e: ApiException) {
    //                    Log.d("ABC", "One tap dismissed.")
    //                }
    //            }
    //        }
    //    }

}