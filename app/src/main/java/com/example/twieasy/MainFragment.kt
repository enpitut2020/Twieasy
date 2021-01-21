package com.example.twieasy

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.jsoup.Jsoup
import java.util.*
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import kotlin.collections.ArrayList

var times = 0
class MainFragment : Fragment(),MailSender.OnMailSendListener {

    var res :String = ""
    lateinit var subjectView : SubjectViewModel
    //lateinit var binding:FragmentMainBinding

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)

        val view =  inflater.inflate(R.layout.fragment_main, container, false)
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)
        view.makeAccount.setOnClickListener{
            val bundle:Bundle = Bundle()
            bundle.putInt("buttonNum",0)

            // ログインIDとログインパスワードのビューオブジェクトを取得
            val loginAccount = view.findViewById<EditText>(R.id.mailAddress)
            val loginPass = view.findViewById<EditText>(R.id.passWord)
            val loginPass2 = view.findViewById<EditText>(R.id.passWord2)
            val ver = view.findViewById<EditText>(R.id.verify)
            // 1回目のパスワードと2回目のパスワードが同じ
            if(loginPass.text.toString() == loginPass2.text.toString() && ver.text.toString() == res && res != "") {
                // 「pref_data」という設定データファイルを読み込み
                val prefData = activity?.getSharedPreferences("pref_data",Context.MODE_PRIVATE)
                val editor = prefData?.edit()


                val encryptionAccount: String = hashSHA256String(loginAccount.text.toString(), subjectView.hexChars)
                val encryptionPassword: String = hashSHA256String(loginPass.text.toString(), subjectView.hexChars)

                // デバッグ用Log
                Log.i("enc", encryptionAccount)
                Log.i("enc2", encryptionPassword)

                // 入力されたログインIDとログインパスワード
                editor?.putString("account", encryptionAccount)
                editor?.putString("pass", encryptionPassword)

                // 暗号化したアカウント名とパスワードを送信

                val tb : TestWeb3? = TestWeb3(requireActivity(), null)
                tb?.register(encryptionAccount, encryptionPassword)
                if(tb?.registerState == true){
                    Log.i("register: ", tb?.registerState.toString())
                    Log.i("mail: ", encryptionAccount)
                    // 保存
                    editor?.commit()

                    findNavController().navigate(R.id.action_mainFragment_to_departmentFragment,bundle)
                }
                else
                {
                    Log.i("mail: ", encryptionAccount)
                }

            } else if (loginPass.text.toString() != loginPass2.text.toString()){
                Log.i("pass1", loginPass.text.toString())
                Log.i("pass2", loginPass2.text.toString())
                Log.i("error", "Difference password")

                loginPass2.hint = "パスワードが違います"
                loginPass2.setText("")
                loginPass2.setBackgroundColor(R.color.warn)
            }else if(ver.text.toString() != res || ver.text.toString().length == 0){
                ver.hint = "認証コードが違います"
                ver.setText("")
                ver.setBackgroundColor(R.color.warn)
            }

        }

        /*view.skip.setOnClickListener{
            val bundle:Bundle = Bundle()
            bundle.putInt("buttonNum",0)
            findNavController().navigate(R.id.action_mainFragment_to_loadFragment,bundle)
        }*/


        view.button.setOnClickListener{
            val mail = getMail()
            MailSender.getInstance().sendMail(mail, this)
        }

        view.loginButton.setOnClickListener{
            val bundle:Bundle = Bundle()
            bundle.putInt("buttonNum",1)
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment,bundle)
        }

        val callBack= requireActivity().onBackPressedDispatcher.addCallback(this) {
            times = 0
            isEnabled = false
            Log.i("backButton", "Pushed")
        }

        return view
    }


    private fun Ver(min: Int, max: Int):Int{
        val rand = Random()
        val randomNum = rand.nextInt((max - min) + 1) + min
        return randomNum
    }



    override fun getMail(): Mail{
        val regex = "[s]{1}[0-9]{7}@[s,u].tsukuba.ac.jp".toRegex()
        val message :String = Ver(1000, 9999).toString()
        res = message
        val flag:Boolean = mailAddress.text.toString().matches(regex)
        return Mail().apply {
            mailServerHost = "smtp.qq.com"
            mailServerPort = "587"
            fromAddress = "549908110@qq.com"
            password = "kyhrhsjoxhxcbfij"
            toAddress = arrayListOf(mailAddress.text.toString())
            subject = "Twieasy messageSender Test"
            if(flag) {
                content = SpanUtils(context)
                    .appendLine(message).setFontSize(28, true)
                    .create()
            }else{
                Toast.makeText(context, "筑波大学のメールアドレスを使用してください", Toast.LENGTH_SHORT).show()
                content = SpanUtils(context)
                    .appendLine("筑波大学のメールアドレスを使いなさい 例:s*******@u/s.tsukuba.ac.jp").setFontSize(
                        28,
                        true
                    )
                    .create()
            }
        }
    }

    override fun onSuccess() {
        //Toast.makeText(this@MainActivity, "成功", Toast.LENGTH_SHORT).show()
    }


    override fun onError(e: Throwable) {
        //Toast.makeText(context, ": $e.message", Toast.LENGTH_SHORT).show()
    }



}