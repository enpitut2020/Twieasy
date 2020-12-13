package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.twieasy.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.io.*
import java.util.*

class MainFragment : Fragment(),MailSender.OnMailSendListener {
    var res :String = ""
    //lateinit var binding:FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)

        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        view.makeAccount.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_flickFragment2)
        }

        view.button.setOnClickListener{
            val mail = getMail()
            MailSender.getInstance().sendMail(mail, this)
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
        val flag:Boolean = mailAddress.text.toString().matches(regex)
        return Mail().apply {
            mailServerHost = "smtp.qq.com"
            mailServerPort = "587"
            fromAddress = "***"
            password = "***"
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