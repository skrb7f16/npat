package com.skrb7f16.npat.models

data class RoundModel(val roundId:Int=-1,
                      val over: Boolean =false,
                      val result:HashMap<String,ArrayList<String>> = hashMapOf<String,ArrayList<String>>()
                      ) {
    constructor():this(-1,false, hashMapOf<String,ArrayList<String>>()){

    }
}
