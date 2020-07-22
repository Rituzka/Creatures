package com.raywenderlich.android.creaturemon.model

class CreatureGenerator {
    fun generateCreature(attributes: CreatureAttributes, name: String = "",
    drawable: Int = 0) : Creature {
        val hitpoints = 5 * attributes.intelligence +
                3 * attributes.strength +
                4 * attributes.endurance
        return Creature(attributes, hitpoints, name, drawable)
    }

}