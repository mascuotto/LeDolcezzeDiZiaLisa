package com.mascuttoapp.ledolcezzedizialisa.bean

import java.io.Serializable

class Formula: Serializable {
        var name:String? = ""
        var duration : Long? = 0
        var level : String? = ""
        var steps = mutableListOf<String>()
        var icon : String? = ""
        var elements = mutableListOf<String>()
        var video : String? = ""

        constructor(name:String, duration: Long, level: String, video: String, icon: String, steps: List<String>, elements: List<String>) {
            this.name = name
            this.duration = duration
            this.level = level
            this.video = video
            this.icon = icon
            this.steps = steps as MutableList<String>
            this.elements = elements as MutableList<String>
        }
        constructor() { }
    }