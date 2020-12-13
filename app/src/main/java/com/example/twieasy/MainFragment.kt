package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.twieasy.databinding.ActivityMainBinding
import com.example.twieasy.databinding.FragmentMainBinding

class MainFragment : Fragment(),MailSender.OnMailSendListener{
    
    var res :String = ""
    lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.makeAccount.setOnClickListener{
            view.findNavController().navigate(R.id.action_mainFragment_to_tutorialFragment)
        }

        return view
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

        val flag:Boolean = binding.mailAddress.text.toString().matches(regex)
        return Mail().apply {
            mailServerHost = "smtp.qq.com"
            mailServerPort = "587"
            fromAddress = "***"
            password = "***"
            toAddress = arrayListOf(binding.mailAddress?.text.toString())
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

    // override fun jumpToLoginPage() {
    //     setContentView(R.layout.login_page)
    //     val login: Button = findViewById(R.id.login_login)
    //     login.setOnClickListener{ jumpToSubjects(subjectsInfo.size) }
    // }

    override fun onError(e: Throwable) {
        Toast.makeText(context, ": $e.message", Toast.LENGTH_SHORT).show()
    }
}