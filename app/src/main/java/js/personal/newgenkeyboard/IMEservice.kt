package js.personal.newgenkeyboard

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.ImageView

class IMEservice : InputMethodService(), View.OnTouchListener {

    // 모든 파일이 공유하는 전역 변수 : 휘발성
    companion object {
        var topM2 = false
        var bottomM2 = false
        var rightM2 = false
        var leftM2 = false
        var midM2 = false
        var flag = false // false : touch up || true : touch down
        lateinit var ic: InputConnection
    }

    // 앱 내부에서 공유되는 cache 데이터 : 비휘발성
    private val userInfo by lazy { getSharedPreferences("userInfo", Context.MODE_PRIVATE) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateInputView(): View {

        // 레이아웃 설정 : R.layout.in_app_keyboard 리소스 가져옴
        val myKeyboardView= layoutInflater.inflate(R.layout.in_app_keyboard, null)

        // 키보드 button 선언 및 정의
        val btn = myKeyboardView.findViewById<ImageView>(R.id.keyboard_skin)

        // touch 입력 리스너 시작
        btn.setOnTouchListener(this)

        return myKeyboardView
    }

    // 터치가 되었을 때 터치를 계속하고 있을 때 터치가 떨어졌을때 불리는 함수
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        ic = currentInputConnection // 입력될 field 연결

        // mode에 따라 다른 함수 호출
        when(userInfo.getBoolean("mode", false)) {
            false -> mode1(v, event)
            true -> mode2(v, event)
        }

        return true
    }
}