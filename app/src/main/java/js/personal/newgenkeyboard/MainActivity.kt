package js.personal.newgenkeyboard

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var modeText: TextView
    lateinit var modeBtn: Button

    private val userInfo by lazy { getSharedPreferences("userInfo", Context.MODE_PRIVATE) }

    // 모드가 바뀌는지 확인하는 method : 바뀌는 모드에 따라 text 변경
    private val ModeChangedListener by lazy { SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when (sharedPreferences.getBoolean(key, false)) {
            false -> modeText.text = "M"
            true -> modeText.text = "T"
        }
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 모드 표시 text 등록
        modeText = findViewById(R.id.modeView)

        // 모드 바꾸는 버튼 선언 및 등록
        modeBtn = findViewById(R.id.swt)
        modeBtn.setOnClickListener(this)

        // 모드가 바뀌는지 확인하는 method 등록
        userInfo.registerOnSharedPreferenceChangeListener(ModeChangedListener)
    }

    // 화면에 있어나는 모든 버튼 클릭있을 때 블려지는 함수
    override fun onClick(v: View?) {
        if (v != null) {

            // 눌린 버튼이 우리가 원하는 버튼일 때
            if(v.id == R.id.swt) {

                // 폰에 지정된 모드가 무엇인지 확인
                val mode = userInfo.getBoolean("mode", false)
                val edit = userInfo.edit()

                // 지정된 모드에 따라 반대 모드로 스위치
                when(mode) {
                    false -> edit.putBoolean("mode", true)
                    true -> edit.putBoolean("mode", false)
                }

                // 모드 저장
                edit.apply()
            }
        }
    }
}