package `as`.example.dobcalculator.quizeapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity() , View.OnClickListener {



    private var mCurrentPosition : Int  = 1 ;
    private var mQuestionList : ArrayList<Question> ? = null
    private var mSelectedOptionPosition : Int = 0
    private var mUserName : String? = null;
    private var mCorrectAnswer : Int = 0;

    private var progressBar : ProgressBar ? = null
    private var tvProgressBar : TextView ? = null
    private var tvQuestion : TextView ? = null
    private var tvImage : ImageView ? = null

    private var tvOptionOne : TextView ? = null
    private var tvOptionTwo : TextView ? = null
    private var tvOptionThree : TextView ? = null
    private var tvOptionFour : TextView ? = null
    private var btnSubmit : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.UAER_NAME)
        mCorrectAnswer = 0 ;

        progressBar = findViewById(R.id.progressBar)
        tvImage = findViewById(R.id.tv_image)
        tvProgressBar = findViewById(R.id.tv_prograssesBar)
        tvQuestion = findViewById(R.id.tv_question)

        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_Submit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        mQuestionList = Constants.getQuestion()

        if (mQuestionList != null && mQuestionList!!.isNotEmpty()) {
            // The list of questions is not empty and is valid
            // Proceed with your code to display questions
            extracted()
        } else {
            // Handle the case where questions couldn't be retrieved
            Log.e("QuizQuestionActivity", "No questions found or an error occurred")
            // You can display an error message to the user or take appropriate action.
        }
    }

    private fun extracted() {

        defaultOprion();

        val question: Question = mQuestionList!![mCurrentPosition!! - 1]
        progressBar?.progress = mCurrentPosition!!
        tvProgressBar?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        tvImage?.setImageResource(question.image)
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if(mCurrentPosition == mQuestionList!!.size){
            btnSubmit?.text = "FINISH"
        }else{
            btnSubmit?.text = "SUBMIT"
        }


    }

    private  fun defaultOprion () {
        var options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3, it)
       }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }


    }

    fun selectedOptionView (tv: TextView, selectedOptionNum: Int){
        defaultOprion()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selectec_option_border_bg
        )
    }

     override fun onClick(view: View?) {
        when (view?.id){
            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it,1)
                }
            }

            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it,3)
                }
            }

            R.id.tv_option_four -> {
                tvOptionFour?.let {
                    selectedOptionView(it,4)
                }
            }

            R.id.btn_Submit -> {
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            extracted()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.UAER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWER,mCorrectAnswer)
                            intent.putExtra(Constants.TOTAL_QUESTION_ANSWER,mQuestionList?.size )
                            startActivity(intent)
                            finish()
                        }

                    }

                }else {
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    if (question!!.currectAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition , R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswer++;
                    }
                    answerView(question.currectAnswer , R.drawable.currecte_option_border_bg)

                    if(mCurrentPosition == mQuestionList!!.size){
                        btnSubmit?.text = "FINISH"
                    }else {
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0 ;

                }
            }

        }
    }

    private fun answerView(answer : Int , drawableView : Int){
        when(answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }

}