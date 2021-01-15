package com.example.twieasy

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.toridge.kotlintest.EncryptionUtils
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        // 「pref_data」という設定データファイルを読み込み
        val prefData = activity?.getSharedPreferences("pref_data",Context.MODE_PRIVATE)
        val account = prefData?.getString("account", "")
        val password = prefData?.getString("pass", "")

        // Log
        Log.i("account", account)
        Log.i("password", password)

        val key: String = "toridge"
        val decryptionAccount: String? =
            account?.let { it1 -> EncryptionUtils.decryptAES128(key, it1) }
        val decryptionPassword: String? =
            password?.let { it1 -> EncryptionUtils.decryptAES128(key, it1) }

        Log.i("dec",decryptionAccount)
        Log.i("dec2",decryptionPassword)

        val pass = decryptionPassword

        view.login_login.setOnClickListener{
            val inputAccount = view.findViewById<TextView>(R.id.mailAddress_login)
            val inputPassword = view.findViewById<TextView>(R.id.passWord_login)

            if(account == inputAccount.text.toString()) {
                if(pass != null && pass == inputPassword.text.toString()) {
                    findNavController().navigate(R.id.action_loginFragment_to_subjectFragment)
                }
                else {
                    inputPassword.hint =  "パスワードが違います"
                    inputPassword.setText("")
                    inputPassword.setBackgroundColor(R.color.warn)
                }
            }
            else {
                inputAccount.hint =  "アカウントが違います"
                inputAccount.setText("")
                inputAccount.setBackgroundColor(R.color.warn)
            }
        }
        // Inflate the layout for this fragment
        return view
    }
}