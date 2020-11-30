package com.example.twieasy


import android.util.Log
import kotlinx.coroutines.*
import javax.mail.Transport


object MailSender {

    
    @JvmStatic
    fun getInstance() = this

    
    fun sendMail(mail: Mail, onMailSendListener: OnMailSendListener? = null) {
        val send = GlobalScope.async(Dispatchers.IO) {
            Transport.send(MailUtil.createMailMessage(mail))
        }

        GlobalScope.launch(Dispatchers.Main) {
            runCatching {
                send.await()
                onMailSendListener?.onSuccess()
            }.onFailure {
                it.message?.let { it1 -> Log.e("MailSender", it1) }
                onMailSendListener?.onError(it)
            }
        }
    }

    
    interface OnMailSendListener {
        fun onSuccess()
        fun onError(e: Throwable)
    }
}
