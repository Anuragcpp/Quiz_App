package `as`.example.dobcalculator.quizeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvName : TextView = findViewById(R.id.tv_userName)
        val tvScore : TextView = findViewById(R.id.tv_score)
        val btnfinish : Button = findViewById(R.id.btn_finish)

        tvName.text = intent.getCharSequenceExtra(Constants.UAER_NAME)
        val totalQuestion = intent.getIntExtra(Constants.TOTAL_QUESTION_ANSWER, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWER, 0)

        tvScore.text = "Your Score is $correctAnswers out of $totalQuestion"


        btnfinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}