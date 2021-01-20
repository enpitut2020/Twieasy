package com.example.twieasy

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.twieasy.R

class MainActivity : AppCompatActivity() {
    private var testWeb3: TestWeb3? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(R.layout.fragment_select_department)

        Web3Helper.SetupBouncyCastle()
        testWeb3 = TestWeb3(this,null);
    }

    /*override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // テストの呼び出し
            testWeb3!!.test()
        }
        return super.onTouchEvent(event)
    }*/
}