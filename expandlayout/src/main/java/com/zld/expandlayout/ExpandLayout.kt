package com.zld.expandlayout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_expand_view.view.*

/**
 *  Created by lingdong on 2018/5/6.
 *
 *
 **/
class ExpandLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mListener: OnExpandListener? = null

    private var maxCollapsedLines = 3
    private var contentTextSize = 18f
    private var contentTextColor = 0
    private var expandText = ""
    private var collapseText = ""
    private var expandCollapseTextSize = 18f
    private var expandCollapseTextColor = 0
    private var expandCollapseTextGravity = 0
    private var ellipsizeText = "..."
    private var middlePadding = 0f

    init {
        initAttrs(context, attrs, defStyleAttr)
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.layout_expand_view, this)
        etv_content.setMaxLineCount(maxCollapsedLines)
        etv_content.textSize = DensityUtil.px2sp(context, contentTextSize)
        etv_content.setTextColor(contentTextColor)
        etv_content.setEllipsizeText(ellipsizeText)

        var lp = tv_tip.layoutParams as LinearLayout.LayoutParams
        lp.topMargin = middlePadding.toInt()
        tv_tip.layoutParams = lp

        tv_tip.textSize = DensityUtil.px2sp(context, expandCollapseTextSize)
        tv_tip.setTextColor(expandCollapseTextColor)
        tv_tip.gravity = when (expandCollapseTextGravity) {
            0 -> Gravity.LEFT
            1 -> Gravity.CENTER
            2 -> Gravity.RIGHT
            else -> Gravity.LEFT
        }

        etv_content.requestLayout()
        tv_tip.requestLayout()

    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        var ta = context.obtainStyledAttributes(attrs, R.styleable.ExpandLayout)
        maxCollapsedLines = ta.getInt(R.styleable.ExpandLayout_maxCollapsedLines, 3)
        contentTextSize = ta.getDimension(R.styleable.ExpandLayout_contentTextSize, DensityUtil.sp2px(context, 18f).toFloat())
        contentTextColor = ta.getColor(R.styleable.ExpandLayout_contentTextColor, resources.getColor(R.color.text_black))
        expandText = if (ta.getString(R.styleable.ExpandLayout_expandText).isNullOrEmpty()) "全文" else ta.getString(R.styleable.ExpandLayout_expandText)
        collapseText = if (ta.getString(R.styleable.ExpandLayout_collapseText).isNullOrEmpty()) "收起" else ta.getString(R.styleable.ExpandLayout_collapseText)
        expandCollapseTextSize = ta.getDimension(R.styleable.ExpandLayout_expandCollapseTextSize, DensityUtil.sp2px(context, 18f).toFloat())
        expandCollapseTextColor = ta.getColor(R.styleable.ExpandLayout_expandCollapseTextColor, resources.getColor(R.color.text_blue))
        expandCollapseTextGravity = ta.getColor(R.styleable.ExpandLayout_expandCollapseTextGravity, 0)
        ellipsizeText = if (ta.getString(R.styleable.ExpandLayout_ellipsizeText).isNullOrEmpty()) "..." else ta.getString(R.styleable.ExpandLayout_ellipsizeText)
        middlePadding = ta.getDimension(R.styleable.ExpandLayout_middlePadding, 0f)
        ta.recycle()
    }


    fun setText(text: String, expand: Boolean, listener: OnExpandListener) {
        mListener = listener
        ll_expand_view.setOnClickListener({
            mListener?.expandChange()
        })
        etv_content.setChanged(expand)
        etv_content.setText(text, expand, object : ExpandTextView.Callback {
            override fun onExpand() {
                tv_tip.visibility = View.VISIBLE
                tv_tip.text = collapseText
            }

            override fun onCollapse() {
                tv_tip.visibility = View.VISIBLE
                tv_tip.text = expandText
            }

            override fun onLoss() {
                tv_tip.visibility = View.GONE
            }

        })
    }

    interface OnExpandListener {
        fun expandChange()
    }


}