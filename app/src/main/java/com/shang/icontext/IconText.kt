package com.shang.icontext

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.content.res.use

/*
 * 使用方法
 * 寬度 = 不限,warp,match,xxDp
 * 高度 = xxDp
 * 建議寬度使用wrap,高度使用固定dp,因為match寫得比較不好,而且沒有置中功能
 * 倒數功能要研究一下surface
 * 目前只有支援0123456789不支援其他特殊符號，因為圖片比例不同
 */
class IconText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        data class Source(val resID: Int, val key: String, var bitmap: Bitmap?)

        private val mSourceMap = mutableMapOf<String, Source>().apply {
            put("0", Source(R.drawable.zero, "0", null))
            put("1", Source(R.drawable.one, "1", null))
            put("2", Source(R.drawable.two, "2", null))
            put("3", Source(R.drawable.three, "3", null))
            put("4", Source(R.drawable.four, "4", null))
            put("5", Source(R.drawable.five, "5", null))
            put("6", Source(R.drawable.six, "6", null))
            put("7", Source(R.drawable.seven, "7", null))
            put("8", Source(R.drawable.eight, "8", null))
            put("9", Source(R.drawable.nine, "9", null))
        }.toMap()

        private val mSourceSize: Int = mSourceMap.size

    }

    //顯示文字陣列
    private var mShowTextArray = mutableListOf<String>("0")

    //字體大寫(DP),sp應該不好算
    private var mTextSize: Float = resources.getDimension(R.dimen.IconTextTextSize)

    //字體內間距,不包含最左最右,最左最右用padding
    private var mTextSpace = resources.getDimension(R.dimen.IconTextSpace)

    //wrap的時候要左到右,其他是右到左
    private var mLeftToRight = true

    init {
        context.obtainStyledAttributes(attrs, R.styleable.IconText).use {
            it.getString(R.styleable.IconText_it_defaultText)?.let {
                setText(it)
            }
            mTextSize = it.getDimension(
                R.styleable.IconText_it_textSize,
                resources.getDimension(R.dimen.IconTextTextSize)
            )
            mTextSpace = it.getDimension(
                R.styleable.IconText_it_space,
                resources.getDimension(R.dimen.IconTextSpace)
            )
        }
    }

    private fun initSourceMap() {
        mSourceMap.values.forEach {
            if (it.bitmap == null) {
                it.bitmap = createBitmap(it.resID)
            }
        }
    }

    private fun createBitmap(resID: Int): Bitmap {
        val bitmapFactory = BitmapFactory.decodeResource(resources, resID)
        //一般的數字大小應該是1:1的，有要加入特殊字元應該要用when去設定他的寬高
        val bitmap =
            Bitmap.createScaledBitmap(bitmapFactory, mTextSize.toInt(), mTextSize.toInt(), false)
        bitmapFactory.recycle()
        return bitmap
    }

    private fun getAllTextWidth(): Float {
        //mShowTextArray應該要forEach去判斷有沒有特殊文字
        val allTextWidth = mTextSize * mShowTextArray.size
        val allSpaceWidth = mTextSpace * (mShowTextArray.size - 1)
        return allTextWidth + allSpaceWidth
    }

    //在init的時候拿不到寬高
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initSourceMap()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Match的話她會先跑AT_MOST->EXACTLY
        //Wrap的話只會跑AT_MOST,等價 ViewGroup.LayoutParams.WRAP_CONTENT
        //指定大小EXACTLY
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(
                getAllTextWidth().toInt() + paddingStart + paddingEnd,
                heightSize + paddingTop + paddingBottom
            )
            mLeftToRight = true
        } else if (widthMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize)
            mLeftToRight = false
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        mShowTextArray.forEachIndexed { index, key ->
            mSourceMap[key]?.bitmap?.let {
                if (mLeftToRight) {
                    //Wrap
                    canvas?.drawBitmap(
                        it,
                        (mTextSize * index) + paddingStart + (mTextSpace * index),
                        0f + paddingTop,
                        null
                    )
                } else {
                    //指定大小和match,這裡應該用Rect來算出區塊
                    //paddingStart目前不起作用
                    val w =
                        width - (mTextSize * (mShowTextArray.size - index)) + paddingEnd - (mShowTextArray.size - index) * mTextSpace
                    canvas?.drawBitmap(
                        it,
                        w,
                        0f,
                        null
                    )
                }
            }
        }
    }

    fun setText(text: String) {
        val beforeSize = mShowTextArray.size
        mShowTextArray.clear()
        text.split("").filter { it.isNotEmpty() }.forEach {
            mShowTextArray.add(it)
        }
        if (beforeSize != mShowTextArray.size) {
            //刷新Layout重新繪製元件寬高
            requestLayout()
        }
        //刷新canvas
        invalidate()
    }

    fun setText(text: List<String>) {
        val beforeSize = mShowTextArray.size
        mShowTextArray.clear()
        mShowTextArray.addAll(text)
        if (beforeSize != mShowTextArray.size) {
            requestLayout()
        }
        invalidate()
    }
}