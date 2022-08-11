package com.skrb7f16.npat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.skrb7f16.npat.databinding.ActivityJoinGameBinding
import com.skrb7f16.npat.models.Room

class JoinGameActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinGameBinding
    lateinit var db:FirebaseDatabase
    lateinit var room: Room
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_game)
        binding=ActivityJoinGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db=FirebaseDatabase.getInstance("https://todo-32f52-default-rtdb.firebaseio.com/")
        binding.joinStart.setOnClickListener{
            if(binding.joinCode.text.isEmpty() || binding.joinName.text.isEmpty()){
                Toast.makeText(this,"SHORT NAME OR CODE",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            db.reference.child("NPAT").child("Rooms").child(binding.joinCode.text.toString()).addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    room= snapshot.getValue(Room::class.java)!!
                    if(room!=null) {
                        if(room.members.indexOf(binding.joinName.text.toString())==-1) {
                            room.members.add(binding.joinName.text.toString())
                            room.scores.put(binding.joinName.text.toString(), 0)
                            db.reference.child("NPAT").child("Rooms")
                                .child(binding.joinCode.text.toString()).setValue(room)
                                .addOnSuccessListener {
                                    val intent =
                                        Intent(applicationContext, GameRoomActivity::class.java)
                                    intent.putExtra("code", binding.joinCode.text.toString())
                                    intent.putExtra("username", binding.joinName.text.toString())
                                    startActivity(intent)

                                }
                        }
                        else{
                            if(room.creator!=binding.joinName.text.toString()) {
                                val intent =
                                    Intent(applicationContext, GameRoomActivity::class.java)
                                intent.putExtra("code", binding.joinCode.text.toString())
                                intent.putExtra("username", binding.joinName.text.toString())
                                startActivity(intent)
                            }else{
                                val intent =
                                    Intent(applicationContext,StartGameActivity::class.java)
                                intent.putExtra("code", binding.joinCode.text.toString())
                                intent.putExtra("username", binding.joinName.text.toString())
                                startActivity(intent)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}