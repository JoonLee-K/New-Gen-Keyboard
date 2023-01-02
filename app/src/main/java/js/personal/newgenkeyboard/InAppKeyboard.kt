package js.personal.newgenkeyboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout

// keyboard layout을 그려주는 class
// JvmOverloads - 기본인자 자동 생성기
class InAppKeyboard @JvmOverloads constructor (context: Context, attrs: AttributeSet?= null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    // class 생성 후 자동실행
    init {
        // layout 그리는 method
        LayoutInflater
            .from(context)
            .inflate(R.layout.in_app_keyboard, this, true)
    }
}