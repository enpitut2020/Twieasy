package com.example.twieasy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.twieasy.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.jsoup.Jsoup
import java.io.*
import java.util.*
import android.content.SharedPreferences
import android.content.Context
import android.graphics.Color
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class MainFragment : Fragment(),MailSender.OnMailSendListener {
    var res :String = ""
    //lateinit var binding:FragmentMainBinding
    lateinit var subjectView : SubjectViewModel

    var counter: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)

        val view =  inflater.inflate(R.layout.fragment_main, container, false)
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        val load = Thread {
            subjectView.kdbRawData = subjectView.subjectNumber.map {
                "https://kdb.tsukuba.ac.jp/syllabi/2020/$it/jpn/"
            } as MutableList<String>

            // KDBの情報を整形したもの
            subjectView.subjects = subjectView.kdbRawData.map {
                getTextFromWeb(it)
            } as MutableList<Subject>

            subjectView.loaded = true
        }

        load.start()



        view.makeAccount.setOnClickListener{
            val bundle:Bundle = Bundle()
            bundle.putInt("buttonNum",0)

            // ログインIDとログインパスワードのビューオブジェクトを取得
            val loginAccount = view.findViewById<EditText>(R.id.mailAddress)
            val loginPass = view.findViewById<EditText>(R.id.passWord)
            val loginPass2 = view.findViewById<EditText>(R.id.passWord2)

            // 1回目のパスワードと2回目のパスワードが同じ
            if(loginPass.text.toString() == loginPass2.text.toString()) {
                // 「pref_data」という設定データファイルを読み込み
                val prefData = activity?.getSharedPreferences("pref_data",Context.MODE_PRIVATE)
                val editor = prefData?.edit()

                // パスワードを暗号化
                val encryption: String = Base64.getEncoder().encodeToString(loginPass.text.toString().toByteArray())

                // 入力されたログインIDとログインパスワード
                editor?.putString("account", loginAccount.text.toString())
                editor?.putString("pass", encryption)

                // 保存
                editor?.commit()

                Log.i("TextView Input", loginAccount.text.toString())
                Log.i("pass-enc",encryption)

                findNavController().navigate(R.id.action_mainFragment_to_departmentFragment,bundle)
            }
            // パスワードが違う
            else {
                Log.i("pass1", loginPass.text.toString())
                Log.i("pass2", loginPass2.text.toString())
                Log.i("error", "diffPassword")

                loginPass2.hint = "パスワードが違います"
                loginPass2.setText("")
                loginPass2.setBackgroundColor(R.color.warn)
            }

        }

        view.button.setOnClickListener{
            val mail = getMail()
            MailSender.getInstance().sendMail(mail, this)
        }

        view.loginButton.setOnClickListener{
            val bundle:Bundle = Bundle()
            bundle.putInt("buttonNum",1)
            findNavController().navigate(R.id.action_mainFragment_to_loadFragment,bundle)
        }

        return view
    }



    val r: Random = Random(
        123
    )
    private fun getTextFromWeb(urlString: String): Subject? {
        var subject: Subject? = null

        val tr = Thread(Runnable {
            try {
                val doc = Jsoup.connect(urlString).get();
                val title = doc.select("#course-title #title").first().text() //科目名
                val credit = doc.select("#credit-grade-assignments #credit").first().text() //単位数
                val timetable =
                    doc.select("#credit-grade-assignments #timetable").first().text() //開講日時
                val styleHeading = doc.select("#style-heading-style p").first().text() //授業形態
                val eval = doc.select("#assessment-heading-assessment p").first().text() //評価方法
                Log.i("title:", title.toString())
                Log.i("credit:", title.toString())
                Log.i("eval:", eval.toString())

                subject = Subject(
                    title,
                    "開講日時　" + timetable + "\n授業形態 " + styleHeading + "\n" + eval + "\n単位数 " + credit,
                    (r.nextInt() % 100 + 100) % 100,
                    subjectView.reviewList[counter++]
                );
            } catch (ex: Exception) {
                Log.d("Exception", ex.toString())
            }
        })

        tr.start()

        tr.join()




        return subject
    }


    private fun Ver(min: Int, max: Int):Int{
        val rand = Random()
        val randomNum = rand.nextInt((max - min) + 1) + min
        return randomNum
    }



    override fun getMail(): Mail{
        val regex = "[s]{1}[0-9]{7}@[s,u].tsukuba.ac.jp".toRegex();
        val message :String = Ver(1000, 9999).toString()
        res = message
        val flag:Boolean = mailAddress.text.toString().matches(regex)
        return Mail().apply {
            mailServerHost = "smtp.qq.com"
            mailServerPort = "587"
            fromAddress = "****"
            password = "****"
            toAddress = arrayListOf(mailAddress.text.toString())
            subject = "Twieasy messageSender Test"
            if(flag) {
                content = SpanUtils(context)
                    .appendLine(message).setFontSize(28, true)
                    .create()
            }else{
                Toast.makeText(context, "筑波大学のメールアドレスを使いなさい", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(context, ": $e.message", Toast.LENGTH_SHORT).show()
    }



}