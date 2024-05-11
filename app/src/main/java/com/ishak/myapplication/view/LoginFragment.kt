package com.ishak.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ishak.myapplication.R
import com.ishak.myapplication.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val currentUser = auth.currentUser
        if(currentUser != null){
            val goWhereToFragment=LoginFragmentDirections.actionLoginFragmentToWhereToFragment2()
            findNavController().navigate(goWhereToFragment)
        }

        binding.signUpbutton.setOnClickListener{

            auth.createUserWithEmailAndPassword(binding.emailText.text.toString(),binding.passwordText.text.toString()).addOnSuccessListener {

                val goWhereToFragment=LoginFragmentDirections.actionLoginFragmentToWhereToFragment2()
                findNavController().navigate(goWhereToFragment)
            }.addOnFailureListener{

                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

        binding.signInbutton.setOnClickListener{
            auth.signInWithEmailAndPassword(binding.emailText.text.toString(),binding.passwordText.text.toString()).addOnSuccessListener {
                val goWhereToFragment=LoginFragmentDirections.actionLoginFragmentToWhereToFragment2()
                findNavController().navigate(goWhereToFragment)
            }.addOnFailureListener{

                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }


}