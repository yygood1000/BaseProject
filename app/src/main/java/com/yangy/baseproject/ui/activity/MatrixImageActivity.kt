package com.yangy.baseproject.ui.activity

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ImageView
import com.yangy.baseproject.R
import utils.ActivityUtils

/**
 * 通过矩阵确认图片的缩放比（该demo为将图片横向方法到全屏宽度）
 */
class MatrixImageActivity : AppCompatActivity() {
    var size = Point()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix_image)

        //获取屏幕的尺寸
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getSize(size)

        //屏幕宽度
        val screenWidth: Int = size.x

        //获取图片的原始宽度
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.bbb, options)
        val imageWidth = options.outWidth

        val scaleRate = screenWidth * 1.0f / imageWidth

        //设置matrix
        val matrix = Matrix()

        //设置放缩比例
        matrix.setScale(scaleRate, scaleRate)

        val ivBackground: ImageView = findViewById(R.id.iv)
        ivBackground.imageMatrix = matrix
        ivBackground.setImageResource(R.mipmap.bbb)
    }

    fun startActivity(activity: Activity?) {
        ActivityUtils.turnToActivity(activity, MatrixImageActivity::class.java)
    }
}
