package com.skrb7f16.npat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.skrb7f16.npat.adapters.NamesOfPlayerAdapter
import com.skrb7f16.npat.databinding.ActivityStartGameBinding
import com.skrb7f16.npat.models.Room
import com.skrb7f16.npat.models.RoundModel

class StartGameActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartGameBinding
    lateinit var db:FirebaseDatabase
    lateinit var myList: MutableList<String>
    lateinit var room: Room
    lateinit var username:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)
        binding= ActivityStartGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username= intent.getStringExtra("username").toString()
        myList= mutableListOf<String>()
        var adapter=NamesOfPlayerAdapter(myList)
        adapter.notifyDataSetChanged()
        binding.namesOfPlayers.adapter=adapter
        binding.namesOfPlayers.layoutManager= LinearLayoutManager(this)
        var code=intent.getStringExtra("code").toString()

        db= FirebaseDatabase.getInstance("https://todo-32f52-default-rtdb.firebaseio.com/")
        if (code != null) {
            db.reference.child("NPAT").child("Rooms").child(code).child("members").addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    myList.clear()
                    for(sn in snapshot.children) {

                        val temp = sn.getValue(String::class.java)
                        println(temp)
                        if(temp!=null)
                        myList.add(temp)
                    }
                    if(myList.size==2)binding.startGameStartBtn.isClickable=true
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

            db.reference.child("NPAT").child("Rooms").child(code).addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    room= snapshot.getValue(Room::class.java)!!

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.startGameStartBtn.setOnClickListener{
            db.reference.child("NPAT").child("Rooms").child(code).addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    room= snapshot.getValue(Room::class.java)!!
                    room.currentRound+=1
                    val hash= HashMap<String,ArrayList<String>>()
                    val round:RoundModel=RoundModel(room.currentRound,false,HashMap<String,ArrayList<String>>())
                    room.rounds.add(round)
                    room.startGame=true
                    db.reference.child("NPAT").child("Rooms").child(code).setValue(room).addOnSuccessListener {
                        val intent= Intent(applicationContext,GameRoomActivity::class.java);
                        intent.putExtra("username",room.creator)
                        intent.putExtra("code",code)
                        startActivity(intent)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}