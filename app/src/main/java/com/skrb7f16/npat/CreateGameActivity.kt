package com.skrb7f16.npat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.skrb7f16.npat.databinding.ActivityCreateGameBinding
import com.skrb7f16.npat.models.Room
import com.skrb7f16.npat.models.RoundModel

class CreateGameActivity : AppCompatActivity() {
    lateinit var db:FirebaseDatabase;
    lateinit var binding: ActivityCreateGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)
        binding= ActivityCreateGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= FirebaseDatabase.getInstance("https://todo-32f52-default-rtdb.firebaseio.com/");
        val code=java.util.UUID.randomUUID().toString()
        binding.enterCode.text = code
        binding.createStart.setOnClickListener {
            val username=binding.enterUsername.text.toString()
            val members= ArrayList<String>()
            members.add(username)
            val scores= HashMap<String,Int>()
            scores[username]=0
            val rounds= ArrayList<RoundModel>()
            val room=Room(code,1,username,members,scores,rounds)
            db.reference.child("NPAT").child("Rooms").child(code).setValue(room).addOnSuccessListener(
                OnSuccessListener {
                    val intent = Intent(applicationContext,StartGameActivity::class.java)
                    intent.putExtra("code",code)
                    startActivity(intent)
                })
        }

        binding.copy.setOnClickListener {

        }
    }
}