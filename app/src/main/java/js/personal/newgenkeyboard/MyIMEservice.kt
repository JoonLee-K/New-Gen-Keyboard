package js.personal.newgenkeyboard

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlin.math.sqrt

class MyIMEservice : InputMethodService(), View.OnClickListener, View.OnTouchListener {

    override fun onCreateInputView(): View {
        val myKeyboardView= layoutInflater.inflate(R.layout.in_app_keyboard, null)
        val btn = myKeyboardView.findViewById<ImageView>(R.id.keyboard_skin)
        btn.setOnTouchListener(this)

        return myKeyboardView
    }

    override fun onClick(v: View?) {
        val ic = currentInputConnection

        val char = (v as Button).text.toString()
        ic.commitText(char, 1)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        val h = v?.height ?: 0

        val box = 100

        var top = false
        var bottom = false
        var right = false
        var left = false
        var mid = false

        var topNleft = false
        var topNright = false
        var bottomNleft = false
        var bottomNright = false

        var topNmid = false
        var leftNmid = false
        var rightNmid = false
        var bottomNmid = false

        val count = event?.pointerCount

        for(i in 0..count!!-1) {

            val id = event.getPointerId(i)
            var (x: Float, y: Float) = event.findPointerIndex(id).let { pointerIndex ->
                // Get the pointer's current position
                event.getX(pointerIndex) to event.getY(pointerIndex)
            }

            var buff = 0f

            // dist from 'x = y'
            buff = x - y - 158
            if(buff < 0) buff *= -1

            val d1 = buff / sqrt(2.0)

            // dist from y = -x + h
            buff = (-x) + (-y) + (h+158)
            if(buff < 0) buff *= -1

            val d2 = buff / sqrt(2.0)

            Log.d("TEST", "x : $x, y : $y, d1 : $d1, d2 : $d2")

            // top & left
            if (d1 < (box/2) && x < 373) {
                topNleft = true
            }

            // bottom & right
            else if (d1 < (box/2) && x > 684) {
                bottomNright = true
            }

            // bottom & left
            else if (d2 < (box/2) && x < 373) {
                bottomNleft = true
            }

            // top & right
            else if (d2 < (box/2) && x > 684) {
                topNright = true
            }

            // top & mid
            else if ((x < 684 && x > 373) && (y < 237 + (box/2) && y > 237 - (box/2))) {
                topNmid = true
            }

            // left & mid
            else if ((x < 380 + (box/2) && x > 380 - (box/2)) && (y < 550 && y > 237)) {
                leftNmid = true
            }

            // right & mid
            else if ((x < 715 + (box/2) && x > 715 - (box/2)) && (y < 550 && y > 237)) {
                rightNmid = true
            }

            // bottom & mid
            else if ((x < 615 && x > 373) && (y < 550 + (box/2) && y > 550 - (box/2))) {
                bottomNmid = true
            }

            // mid btn
            else if ((x < 684 && x > 373) && (y < 550 && y > 237)) {
                mid = true
            }

            // top btn
            else if (y < x-158 && y < (-x + h + 158)) {
                top = true
            }

            // right btn
            else if (y < x-158 && y > (-x + h + 158)) {
                right = true
            }

            // left btn
            else if (y > x-158 && y < (-x + h + 158)) {
                left = true
            }

            // bottom btn
            else if (y > x-158 && y > (-x + h + 158)) {
                bottom = true
            }
        }

        if (event.action == MotionEvent.ACTION_UP) {
            Log.d("TEST", "mid = $mid, left : $left, right : $right, top : $top, bottom : $bottom")
            Log.d("TEST", "topRight = $topNright, topLeft : $topNleft, bottomRight : $bottomNright, bottomLeft : $bottomNleft")

            val ic = currentInputConnection

            if (mid) { ic.commitText("5", 1) }

            else if (left) { ic.commitText("1", 1) }
            else if (top) { ic.commitText("2", 1) }
            else if (right) { ic.commitText("3", 1) }
            else if (bottom) { ic.commitText("4", 1) }

            else if (topNmid) { ic.commitText("7", 1) }
            else if (leftNmid) { ic.commitText("6", 1) }
            else if (rightNmid) { ic.commitText("8", 1) }
            else if (bottomNmid) { ic.commitText("9", 1) }

            else if (topNleft) { ic.commitText("0", 1) }
            else if (topNright) { ic.commitText("\n",0) }
            else if (bottomNleft) { ic.commitText(" ", 1) }
            else if (bottomNright) { ic.deleteSurroundingText(1, 0) }
        }

        return true
    }

}