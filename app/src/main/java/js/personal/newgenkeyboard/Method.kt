package js.personal.newgenkeyboard

import android.util.Log
import android.view.MotionEvent
import android.view.View
import js.personal.newgenkeyboard.IMEservice.Companion.bottomM2
import js.personal.newgenkeyboard.IMEservice.Companion.flag
import js.personal.newgenkeyboard.IMEservice.Companion.ic
import js.personal.newgenkeyboard.IMEservice.Companion.leftM2
import js.personal.newgenkeyboard.IMEservice.Companion.midM2
import js.personal.newgenkeyboard.IMEservice.Companion.rightM2
import js.personal.newgenkeyboard.IMEservice.Companion.topM2
import kotlin.math.sqrt

fun mode2(v: View?, event: MotionEvent?) {
    val h = v?.height ?: 0

    val count = event?.pointerCount
    var upCounter = 0
    var downCounter = 0

    for(i in 0..count!!-1) {

        val id = event.getPointerId(i)
        val (x: Float, y: Float) = event.findPointerIndex(id).let { pointerIndex ->
            // Get the pointer's current position
            event.getX(pointerIndex) to event.getY(pointerIndex)
        }

        if (event.action == MotionEvent.ACTION_DOWN) downCounter++
        if (event.action == MotionEvent.ACTION_UP) upCounter++

        // mid btn
        if ((x < 684 && x > 373) && (y < 550 && y > 237)) {
            midM2 = true
        }

        // top btn
        else if (y < x-158 && y < (-x + h + 158)) {
            topM2 = true
        }

        // right btn
        else if (y < x-158 && y > (-x + h + 158)) {
            rightM2 = true
        }

        // left btn
        else if (y > x-158 && y < (-x + h + 158)) {
            leftM2 = true
        }

        // bottom btn
        else if (y > x-158 && y > (-x + h + 158)) {
            bottomM2 = true
        }
    }

    // 터치가 되었을 때
    if (!flag && downCounter > 0) {
        flag = true
    }

    // 두 손가락 모두 떨어졌을 때
    else if (flag && upCounter > 0 && downCounter == 0) {
        flag = false
        Log.d("MODE1", "mid = $midM2, left : $leftM2, right : $rightM2, top : $topM2, bottom : $bottomM2")

        // 상황에 따른 keyboard input
        if (topM2 && midM2) { ic.commitText("6", 1) }
        else if (leftM2 && midM2) { ic.commitText("7", 1) }
        else if (rightM2 && midM2) { ic.commitText("8", 1) }
        else if (bottomM2 && midM2) { ic.commitText("9", 1) }

        else if (leftM2 && topM2) { ic.commitText("0", 1) }
        else if (topM2 && rightM2) { ic.commitText("\n",0) }
        else if (leftM2 && bottomM2) { ic.commitText(" ", 1) }
        else if (rightM2 && bottomM2) { ic.deleteSurroundingText(1, 0) }

        else if (midM2) { ic.commitText("5", 1) }

        else if (leftM2) { ic.commitText("2", 1) }
        else if (topM2) { ic.commitText("1", 1) }
        else if (rightM2) { ic.commitText("3", 1) }
        else if (bottomM2) { ic.commitText("4", 1) }

        // 변수 초기화
        midM2 = false
        leftM2 = false
        rightM2 = false
        topM2 = false
        bottomM2 = false
    }
}

fun mode1(v: View?, event: MotionEvent?) {
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

    // 손가락 떨어졌을 때
    if (event.action == MotionEvent.ACTION_UP) {
        Log.d("MODE1", "mid = $mid, left : $left, right : $right, top : $top, bottom : $bottom")
        Log.d("MODE1", "topRight = $topNright, topLeft : $topNleft, bottomRight : $bottomNright, bottomLeft : $bottomNleft")


        // 상황에 따른 keyboard input
        if (mid) { ic.commitText("5", 1) }

        else if (left) { ic.commitText("2", 1) }
        else if (top) { ic.commitText("1", 1) }
        else if (right) { ic.commitText("3", 1) }
        else if (bottom) { ic.commitText("4", 1) }

        else if (topNmid) { ic.commitText("6", 1) }
        else if (leftNmid) { ic.commitText("7", 1) }
        else if (rightNmid) { ic.commitText("8", 1) }
        else if (bottomNmid) { ic.commitText("9", 1) }

        else if (topNleft) { ic.commitText("0", 1) }
        else if (topNright) { ic.commitText("\n",0) }
        else if (bottomNleft) { ic.commitText(" ", 1) }
        else if (bottomNright) { ic.deleteSurroundingText(1, 0) }
    }
}