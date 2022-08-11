package com.skrb7f16.npat.models

class Room (
    var code:String="",
                var totalMember:Int=0,
                var creator:String="-1",
                var members:ArrayList<String> = ArrayList<String>(),
                var scores:HashMap<String,Int> = hashMapOf<String,Int>(),
                var rounds:ArrayList<RoundModel> = ArrayList<RoundModel>(),

                var startGame:Boolean=false,
                var currentRound:Int=-1,)
{
    constructor():this("",0,"-1",ArrayList<String>(), hashMapOf<String,Int>(),ArrayList<RoundModel>(),false,-1){

    }
}
