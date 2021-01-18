package com.example.twieasy

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.twieasy.databinding.FragmentFlickBinding
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.first_boot.*
import kotlinx.android.synthetic.main.first_boot.view.*
import kotlinx.android.synthetic.main.first_boot.view.center
import kotlinx.android.synthetic.main.first_boot.view.subject_info
import kotlinx.android.synthetic.main.fragment_flick.view.*
import org.jsoup.Jsoup
import java.util.*
import kotlin.random.Random

class FlickFragment : Fragment() {

    //lateinit var binding:FragmentFlickBinding
    lateinit var vii : View
    lateinit var subjectView : SubjectViewModel
    private lateinit var department: String
    val regex = Regex(pattern = "開講日時(.*)\n")
    lateinit var t : Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            department = it.getString("department")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vii = inflater.inflate(R.layout.fragment_flick, container, false)
        t = Toast.makeText(vii.context, "", Toast.LENGTH_SHORT)

        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        val tvSubjectInfo : TextView = vii.subject_info
        when(department){
            "coins" -> {
                val matchResult = regex.find(subjectView.coinsSubjects[0]?.info)
                tvSubjectInfo.text =  subjectView.coinsSubjects[0]?.name + "\n" + matchResult?.groups?.get(1)?.value.orEmpty()
            }
            "mast" -> {
                val matchResult = regex.find(subjectView.mastSubjects[0]?.info)
                tvSubjectInfo.text =  subjectView.mastSubjects[0]?.name + "\n" + matchResult?.groups?.get(1)?.value.orEmpty()
            }
            "klis" -> {
                val matchResult = regex.find(subjectView.klisSubjects[0]?.info)
                tvSubjectInfo.text =  subjectView.klisSubjects[0]?.name + "\n" + matchResult?.groups?.get(1)?.value.orEmpty()
            }
        }

        jmpToFlick()

        requireActivity().onBackPressedDispatcher.addCallback(this) {/*戻るボタンが押されても遷移しない*/}

        return vii
    }


    val flickAttribute = mutableMapOf<Int, String>()
    var swipedCount = 1
    var res :String = ""

    private fun jmpToFlick(){
        //setContentView(R.layout.first_boot)
        vii.center.setOnTouchListener(FlickListener(flickListener))
    }

    private val flickListener = object : FlickListener.Listener {

        override fun onButtonPressed() {
            vii.center.setBackgroundButtonColor(R.color.pressedButtonColor)
            toggleVisible()
        }

        /// override fun onSwipingOnCenter() = vii.center.settingSwipe()
        override fun onSwipingOutside() {
        }

        override fun onSwipingOnLeft() = left.settingSwipe()
        override fun onSwipingOnRight() = right.settingSwipe()
        override fun onSwipingOnUp() = top.settingSwipe()
        //override fun onSwipingOnDown() = bottom.settingSwipe()

        override fun onButtonReleased() = settingFlick("中")
        override fun onFlickToLeft() = settingFlick("落単")
        override fun onFlickToRight() = settingFlick("楽単")
        override fun onFlickToUp() = settingFlick("未履修")
        // override fun onFlickToDown() = settingFlick("下")
        override fun onFlickOutside() {
            settingFlick("")
        }


        private fun settingFlick(label: String) {
            Log.i("Flicked", label)

            //Thread.sleep(100)
            toggleInvisible()
            clearAll()

            if(label != "中" && label != "下") {
                showToast(label)


                // 画面遷移
                // 1.フリック情報:labelを保持しておく
                flickAttribute.put(flickAttribute.count(), label)
                print(flickAttribute)

                // 2.科目情報を変更
                val tvSubjectInfo: TextView = vii.subject_info

                when(department){
                    "coins" -> {
                        val matchResult = regex.find(subjectView.coinsSubjects[swipedCount]?.info)
                        tvSubjectInfo.text = subjectView.coinsSubjects[swipedCount]?.name + "\n" + matchResult?.groups?.get(1)?.value.orEmpty()
                    }
                    "mast" -> {
                        val matchResult = regex.find(subjectView.mastSubjects[swipedCount]?.info)
                        tvSubjectInfo.text = subjectView.mastSubjects[swipedCount]?.name + "\n" + matchResult?.groups?.get(1)?.value.orEmpty()
                    }
                    "klis" -> {
                        val matchResult = regex.find(subjectView.klisSubjects[swipedCount]?.info)
                        tvSubjectInfo.text = subjectView.klisSubjects[swipedCount]?.name + "\n" + matchResult?.groups?.get(1)?.value.orEmpty()
                    }
                }

                swipedCount += 1

                // 3.全部終わったら履修科目一覧に遷移
                if (swipedCount >= 1) {
//                if (swipedCount >= subjectView.subjects.size) {
                    findNavController().navigate(R.id.action_flickFragment2_to_subjectFragment)
                }
            }
        }

        private fun toggleVisible() {
            top.visibility = View.VISIBLE
            left.visibility = View.VISIBLE
            right.visibility = View.VISIBLE
            bottom.visibility = View.VISIBLE
        }

        private fun toggleInvisible() {
            top.visibility = View.INVISIBLE
            left.visibility = View.INVISIBLE
            right.visibility = View.INVISIBLE
            bottom.visibility = View.INVISIBLE
        }

        private fun clearAll() {
            center.setBackgroundButtonColor(R.color.baseButtonColor)
            left.setBackgroundButtonColor(R.color.lightgray)
            right.setBackgroundButtonColor(R.color.lightgray)
            top.setBackgroundButtonColor(R.color.lightgray)
            bottom.setBackgroundButtonColor(R.color.baseButtonColor)
        }

        private fun View.settingSwipe() {
            clearAll()
            setBackgroundButtonColor(R.color.pressedButtonColor)
            Log.i("Swiped", "")
        }

        private fun View.setBackgroundButtonColor(@ColorRes resId: Int) =
            setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, resId))

        private fun showToast(msg: String) {
            if(t != null) {
                t.cancel()
            }
            t = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            t.show()
        }
    }
}