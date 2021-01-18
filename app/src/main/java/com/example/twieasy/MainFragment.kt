package com.example.twieasy

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import android.os.Build
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import kotlin.collections.ArrayList

var times = 0
class MainFragment : Fragment(),MailSender.OnMailSendListener {

    var res :String = ""
    //lateinit var binding:FragmentMainBinding
    lateinit var subjectView : SubjectViewModel

    var counter: Int = 0
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

        val load = Thread {
            if(times++ == 0) {//戻るボタンで遷移してきた際は再度科目情報を読み取らない
                // KDBの情報を整形したもの
                subjectView.coinsSubjects = subjectView.coinsSubjectNumber.map {
                    getTextFromWeb(it, 0)
                } as ArrayList<Subject>
                subjectView.mastSubjects = subjectView.mastSubjectNumber.map {
                    getTextFromWeb(it, 1)
                } as ArrayList<Subject>
                subjectView.klisSubjects = subjectView.klisSubjectNumber.map {
                    getTextFromWeb(it, 0)
                } as ArrayList<Subject>
            }

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
            val ver = view.findViewById<EditText>(R.id.verify)
            // 1回目のパスワードと2回目のパスワードが同じ
            if(loginPass.text.toString() == loginPass2.text.toString() && ver.text.toString() == res && res != "") {
                // 「pref_data」という設定データファイルを読み込み
                val prefData = activity?.getSharedPreferences("pref_data",Context.MODE_PRIVATE)
                val editor = prefData?.edit()

                // SHA-256
                val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
                val hexChars = (1..16)
                    .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("");
                editor?.putString("key", hexChars)
                val encryptionAccount: String = hashSHA256String(loginAccount.text.toString(), hexChars)
                val encryptionPassword: String = hashSHA256String(loginPass.text.toString(), hexChars)

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
                }
                else
                {
                    Log.i("mail: ", encryptionAccount)
                }
                // 保存
                editor?.commit()

                findNavController().navigate(R.id.action_mainFragment_to_departmentFragment,bundle)
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
            findNavController().navigate(R.id.action_mainFragment_to_loadFragment,bundle)
        }

        val callBack= requireActivity().onBackPressedDispatcher.addCallback(this) {
            times = 0
            isEnabled = false
            Log.i("backButton", "Pushed")
        }
        return view
    }



    val r: Random = Random(
        123
    )
    private fun getTextFromWeb(classNum: String, command: Int): Subject? {
        var subject: Subject? = null
        val tb : TestWeb3? = TestWeb3(requireActivity(), null)
        val tr = Thread(Runnable {
            try{
                val doc = Jsoup.connect("https://kdb.tsukuba.ac.jp/syllabi/2020/$classNum/jpn/").get()
                val title = doc.select("#course-title #title").first().text() //科目名
                val assignments = doc.select("#credit-grade-assignments #assignments").first().text()
                val credit = doc.select("#credit-grade-assignments #credit").first().text() //単位数
                val timetable = doc.select("#credit-grade-assignments #timetable").first().text() //開講日
                lateinit var styleHeading: String //授業形態
                lateinit var eval: String //評価方法
                when(command){
                    0 -> {
                        styleHeading = doc.select("#style-heading-style p").first().text() //授業形態
                        eval = doc.select("#assessment-heading-assessment p").first().text() //評価方法
                    }
                    1 -> {
                        styleHeading = doc.select("#note-heading-note:contains(授業方法) p").first().text() //授業形態
                        eval = doc.select("#style-heading-style:contains(成績評価) p").first().text() //評価方法
                    }
                }
                Log.i("title:", title.toString())
                Log.i("assignments:", assignments.toString())
                Log.i("credit:", credit.toString())
                Log.i("eval:", eval)

                val easiness = tb?.getEasy(classNum) as Float
                val difficulty = tb?.getDifficult(classNum) as Float
                var ratio = 50
                if (easiness != 0.0F || difficulty != 0.0F) {
                    ratio = (easiness * 100 / (easiness + difficulty)) as Int
                    Log.i("ratio", ratio.toString())
                }

                subject = Subject(
                    title,
                    "担当教員　" + assignments + "\n開講日時　" + timetable + "\n授業形態　" + styleHeading + "\n単位数　　" + credit + "\n" + eval,
                    ratio, //(r.nextInt() % 100 + 100) % 100,
                    subjectView.reviewList[counter++],
                    classNum
                )
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