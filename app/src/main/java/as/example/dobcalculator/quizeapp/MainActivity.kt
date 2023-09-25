package `as`.example.dobcalculator.quizeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart : Button= findViewById(R.id.btn_Start)
        val etName : TextView = findViewById(R.id.et_Text)
        val handelr = Handler()

        btnStart.setOnClickListener {

            if(etName.text.isEmpty()){
                Toast.makeText(this, "Please Enter your Name", Toast.LENGTH_SHORT).show()
            }else {
                

                val intent = Intent(this , QuizQuestionActivity :: class.java)
                intent.putExtra(Constants.UAER_NAME , etName.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }


}