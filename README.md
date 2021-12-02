# Android  Icon Text
## 功能介紹
特殊圖片數字顯示元件<br />
建議使用方法<br />
寬度wrap_content，高度固定dp，因為數字圖片通常都是1:1的比例<br />
倒數功能是做在Activity用Hanlder 50ms去刷新的，要做在裡面要改用Surface<br />
由於jcenter不能傳了所以也沒打算作成Library，而且實際使用圖片一定都不一樣<br />
有預設圖片太佔空間了。<br />

## xml
```java
    <com.shang.icontext.IconText
        android:id="@+id/iconText"
        android:layout_width="wrap_content"
        android:layout_height="@dimen/IconTextTextSize"
        android:paddingStart="0dp"
        android:paddingTop="0dp"
        android:paddingEnd="0dp"
        android:paddingBottom="0dp"
        app:it_defaultText="0"
        app:it_space="@dimen/IconTextSpace"
        app:it_textSize="@dimen/IconTextTextSize"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/button" />
```

## 屬性介紹
|  attr |  description | type | default |
| ------------ | ------------ |------------ |------------ |
|  it_defaultText | 預設文字  | String | "0"
|  it_textSize| 文字寬高  | dimension | 30dp
|  it_space|  文字內間距 |dimension | 0dp


## Demo圖片
##### TextSize=30dp
[![TextSize=30dp](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/origin.png?raw=true "TextSize=30dp")](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/origin.png?raw=true "TextSize=30dp")
##### TextSize=16dp
[![TextSize=16dp](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/textSize16dp.png?raw=true "TextSize=16dp")](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/textSize16dp.png?raw=true "TextSize=16dp")
##### 添加背景
[![添加背景](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/background.png?raw=true "添加背景")](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/background.png?raw=true "添加背景")
##### 間距(space)=20dp
[![間距20dp](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/sapce20dp.png?raw=true "間距20dp")](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/sapce20dp.png?raw=true "間距20dp")
##### 倒數Gif
[![倒數Gif](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/countdown.gif?raw=true "倒數Gif")](https://github.com/CiaShangLin/AndroidIconText/blob/master/demo/countdown.gif?raw=true "倒數Gif")