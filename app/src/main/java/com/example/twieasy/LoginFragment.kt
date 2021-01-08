package com.example.twieasy

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        // 「pref_data」という設定データファイルを読み込み
        val prefData = activity?.getSharedPreferences("pref_data",Context.MODE_PRIVATE)
        val account = prefData?.getString("account", "")
        val encryption = prefData?.getString("pass", "")

        // Log
        Log.i("account", account)
        Log.i("pass-en", encryption)

        // パスワード複合化処理
        try {
            val decryption = Base64.getDecoder().decode(encryption?.toByteArray()).toString(Charsets.UTF_8)
            Log.i("pass-dec", decryption)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        }


        view.login_login.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_subjectFragment)

        }
        // Inflate the layout for this fragment
        return view
    }
}