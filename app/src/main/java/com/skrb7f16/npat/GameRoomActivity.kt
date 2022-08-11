package com.skrb7f16.npat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.skrb7f16.npat.databinding.ActivityGameRoomBinding
import com.skrb7f16.npat.models.Room

class GameRoomActivity : AppCompatActivity() {
    lateinit var c:CountDownTimer
    lateinit var binding:ActivityGameRoomBinding
    lateinit var room:Room
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_room)
        binding= ActivityGameRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username=intent.getStringExtra("username")

        val code =intent.getStringExtra("code")
        val db= FirebaseDatabase.getInstance("https://todo-32f52-default-rtdb.firebaseio.com/")

        c=object:CountDownTimer(60000,1000){
            override fun onTick(p0: Long) {
                binding.timer.text = ""+p0/1000;


            }

            override fun onFinish() {
                Toast.makeText(applicationContext,"TIMER DONE",Toast.LENGTH_SHORT).show()
                if(room!=null){
                    if (code != null && username!=null) {
                        room.rounds[room.currentRound].result[username] = arrayListOf(binding.nameVal.text.toString(),
                            binding.placeVal.text.toString(),
                            binding.animalVal.text.toString(),
                            binding.thingVal.text.toString()
                        )
                        db.reference.child("NPAT").child("Rooms").child(code).setValue(room).addOnSuccessListener {
                            Toast.makeText(applicationContext,"Submitted",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

        if (code != null) {
            db.reference.child("NPAT").child("Rooms").child(code).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    room= snapshot.getValue(Room::class.java)!!
                    if(room.startGame){
                        Toast.makeText(applicationContext,"Start writing",Toast.LENGTH_SHORT).show()
                        c.start()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
}