package com.example.twieasy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


class LoginFragment : Fragment() {

    lateinit var subjectView : SubjectViewModel

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)
        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        view.login_login.setOnClickListener{
//            val inputAccount = view.findViewById<TextView>(R.id.mailAddress_login)
//            val inputPassword = view.findViewById<TextView>(R.id.passWord_login)
//
//            if(account == inputAccount.text.toString()) {
//                if(pass != null && pass == inputPassword.text.toString()) {
//                    findNavController().navigate(R.id.action_loginFragment_to_subjectFragment)
//                }
//                else {
//                    inputPassword.hint =  "パスワードが違います"
//                    inputPassword.setText("")
//                    inputPassword.setBackgroundColor(R.color.warn)
//                }
//            }
//            else {
//                inputAccount.hint =  "アカウントが違います"
//                inputAccount.setText("")
//                inputAccount.setBackgroundColor(R.color.warn)
//            }
            val mail = mailAddress_login.text.toString()
            val pass = passWord_login.text.toString()

            // 「pref_data」という設定データファイルを読み込み
            val hexChars = subjectView.hexChars
            val encryptionAccount = hexChars?.let { it1 -> hashSHA256String(mail, it1) }
            val encryptionPassword = hexChars?.let { it1 -> hashSHA256String(pass, it1) }

            val tb : TestWeb3? = TestWeb3(requireActivity(),null)
            tb?.login(encryptionAccount,encryptionPassword)
            if(tb?.loginState == true) {
                subjectView.currentAccount = encryptionAccount.toString()
                findNavController().navigate(R.id.action_loginFragment_to_loadVotesFragment)
            }
            else{
                val inputPassword = view.findViewById<TextView>(R.id.passWord_login)
                inputPassword.hint =  "メールアドレスまたはパスワードが違います"
                inputPassword.setText("")
                inputPassword.setBackgroundColor(R.color.warn)
            }


        }
        // Inflate the layout for this fragment
        return view
    }
}