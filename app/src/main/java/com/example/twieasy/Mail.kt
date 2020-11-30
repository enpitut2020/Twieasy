package com.example.twieasy

import java.io.File


data class Mail(
    var mailServerHost: String = "", 
    var mailServerPort: String = "",
    var fromAddress: String = "",
    var password: String = "", 

    var toAddress: ArrayList<String> = ArrayList(), 
    var ccAddress: ArrayList<String> = ArrayList(), 
    var bccAddress: ArrayList<String> = ArrayList(), 

    var subject: String = "",  
    var content: CharSequence = "",
    var attachFiles: ArrayList<File> = ArrayList(), 

    var openSSL:Boolean  = false, 
    var sslFactory:String = "javax.net.ssl.SSLSocketFactory"
)