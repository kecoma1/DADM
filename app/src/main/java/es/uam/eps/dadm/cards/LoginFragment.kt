package es.uam.eps.dadm.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cards.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    var email: String = ""
    var password: String = ""

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        auth = Firebase.auth

        val emailTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email = s.toString()
            }
        }

        val passwordTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) { }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password = s.toString()
            }
        }

        binding.emailEditText.addTextChangedListener(emailTextWatcher)
        binding.passwordEditText.addTextChangedListener(passwordTextWatcher)

        val goToDeckListFragment = { view: View ->
            view.findNavController()
                .navigate(LoginFragmentDirections
                    .actionLoginFragmentToDeckListFragment())
        }

        /* signinOrLogin: true signin, false login  */
        val checkTask = { task: Task<AuthResult>, view: View, signinOrLogin: Boolean ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Snackbar.make(
                    binding.root,
                    if (signinOrLogin) R.string.signin_success
                    else R.string.signin_success
                    , Snackbar.LENGTH_SHORT).show()
                goToDeckListFragment(view)
            } else {
                Snackbar.make(
                    binding.root,
                    if (signinOrLogin) R.string.signin_failure
                    else R.string.signin_failure,
                    Snackbar.LENGTH_SHORT).show()
            }
        }

        val checkInput = {
            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(binding.root, R.string.ask_for_input, Snackbar.LENGTH_SHORT).show()
                false
            }
            else if (!email.contains("@") || !email.contains(".")) {
                Snackbar.make(binding.root, R.string.wrong_email_format, Snackbar.LENGTH_SHORT).show()
                false
            }
            else if (password.length < 8) {
                Snackbar.make(binding.root, R.string.short_password, Snackbar.LENGTH_SHORT).show()
                false
            } else
                true
        }

        binding.loginButton.setOnClickListener {
            if (checkInput())
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> -> checkTask(task, it, true) }
        }

        binding.signupButton.setOnClickListener {
            if (checkInput())
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> -> checkTask(task, it, false) }
        }

        binding.cancelButton.setOnClickListener {
            it.findNavController()
                .navigate(LoginFragmentDirections
                    .actionLoginFragmentToTitleFragment())
        }
    }
}