package com.wht.compareview;

import java.util.List;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @Description: 对比view
 * @author wanghaitao
 * @date 2014-11-25 上午11:48:25
 * @version V1.0
 */
public class CompareView extends View {

	private Context context;
	/**
	 * 左边矩形 的颜色
	 */
	private int leftRectColor;
	/**
	 * 右边矩形 的颜色
	 */
	private int rightRectColor;
	/**
	 * 线的颜色
	 */
	private int lineColor;
	/**
	 * 线的宽度
	 */
	private int lineWidth = 1;
	/**
	 * 线的高度
	 */
	private float lineHeight;

	/**
	 * view 的高度
	 */
	private float viewWidth;
	/**
	 * 文字大小
	 */
	private float textSize = 15;
	/**
	 * 矩形高度
	 */
	private float rectHight;
	/**
	 * padding
	 */
	private int padding;

	/**
	 * textWith
	 */
	private int textWidth = 80;
	
	Paint paintRect = new Paint();
	
	Paint paintLine = new Paint();
	
	private List<Compare> list;

	public CompareView(Context context) {
		this(context, null);
	}

	public CompareView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.CompareView);

		// 获取自定义属性和默认值
		leftRectColor = mTypedArray.getColor(
				R.styleable.CompareView_leftRectColor, 0xff58b2ff);
		rightRectColor = mTypedArray.getColor(
				R.styleable.CompareView_rightRectColor, 0xfffd6e6e);
		lineColor = mTypedArray.getColor(R.styleable.CompareView_lineColor,
				0xff1bfff2);
		rectHight = mTypedArray.getDimension(R.styleable.CompareView_rectHight,
				PixelUtil.dp2px(context,35));
		this.padding = PixelUtil.dp2px(context,10);
		lineWidth = PixelUtil.dp2px(context,1);
		lineHeight = mTypedArray.getDimension(
				R.styleable.CompareView_lineHight, PixelUtil.dp2px(context,10));
		textSize = mTypedArray.getDimension(
				R.styleable.CompareView_textSize, PixelUtil.sp2px(context,16));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		paintRect.setTextSize(textSize);
		paintRect.setStyle(Paint.Style.FILL);
		float centerTextWidth = 0; // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间

		float startX = 0;
		float startY = 0;
		float endX = 0;
		float endY = 0;
		float canUseLenth;
		String leftText;
		String rightText;
		String centerText;
		float max;
		Compare compare;
	
		paintLine.setAntiAlias(true);
		paintLine.setStrokeWidth(lineWidth);
		paintLine.setColor(0xff1bfff2);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {

				compare = list.get(i);
				leftText = compare.getLeftText();
				rightText = compare.getRightText();
				centerText = compare.getCenterText();
				max = compare.getMax();

				centerTextWidth = paintRect.measureText(leftText);

				startX = padding + textWidth;
				endX = viewWidth / 2 ;

				startY = endY;
				
				endY =startY+lineHeight;
				
				canvas.drawLine(viewWidth / 2-lineWidth/2, startY, viewWidth / 2, endY,
						paintLine);
				
				centerTextWidth = paintRect.measureText(centerText);
				// 中间文字
				canvas.drawText(centerText,
						viewWidth / 2 - centerTextWidth / 2, startY
								+ lineHeight/2 + textSize / 2,
						paintRect);
				
				float rate1=toRate(max, leftText);
				float rate2=toRate(max, rightText);
				
/*				if(rate1<0.6 || rate2<0.6){
					
					if(rate1>rate2){
						
					}else{
						
					}
				}*/
				canUseLenth = (endX - startX);
				startX = endX - canUseLenth * rate1;
				
				paintRect.setColor(leftRectColor);
				

				
				startY = endY;
				endY = startY + rectHight;
				
				// 左边矩形
				canvas.drawRect(startX, startY, endX, endY, paintRect);

				paintRect.setColor(Color.BLACK);
				// 左边文字
				centerTextWidth = paintRect.measureText(leftText);
				canvas.drawText(leftText, startX - centerTextWidth - 5, startY
						+ (endY - startY) / 2 + textSize / 2, paintRect);

				paintRect.setColor(rightRectColor);

				// 右边文字

				startX = viewWidth / 2 ;

				endX = startX + canUseLenth * rate2;

				// 右边矩形
				canvas.drawRect(startX, startY, endX, endY, paintRect);
				paintRect.setColor(Color.BLACK);
				centerTextWidth = paintRect.measureText(centerText);


				// 右边文字
				canvas.drawText(rightText, endX, startY + (endY - startY) / 2
						+ textSize / 2, paintRect);

				
				// 划线
				canvas.drawLine(viewWidth / 2-lineWidth/2, startY, viewWidth / 2, endY,
						paintLine);		
				
				startY = endY;

			}
			
			endY = startY + lineHeight;
			canvas.drawLine(viewWidth / 2-lineWidth/2, startY, viewWidth / 2, endY,
					paintLine);
			
		}
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) viewWidth, (int) endY);
		params.height =(int) endY+5;
		setLayoutParams(params);

	}

	public void addData(List<Compare> list) {
		this.list = list;
		postInvalidate();
	}

	private float toRate(float max, String str) {
		float rate = 0;
		if (str != null && max != 0) {
			str =str.replaceAll("%", "").replace("码", "").replace("杆", "");
			float temp = Float.parseFloat(str);
			rate = temp / max;
		}
		return rate >1? 1:rate;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		getMeasuredLength(widthMeasureSpec, true);
	
	}

	private int getMeasuredLength(int length, boolean isWidth) {
		int specMode = MeasureSpec.getMode(length);
		int specSize = MeasureSpec.getSize(length);
		int size = 0;
		int padding = isWidth ? getPaddingLeft() + getPaddingRight()
				: getPaddingTop() + getPaddingBottom();
		if (specMode == MeasureSpec.EXACTLY) {
			size = specSize;
		} else {
			if (specMode == MeasureSpec.AT_MOST) {
				size = Math.min(size, specSize);
			}
		}
		viewWidth = size;
		return size;
	}
}
