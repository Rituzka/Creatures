package com.raywenderlich.android.creaturemon.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.raywenderlich.android.creaturemon.model.*
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class CreatureViewModel(private val creatureGenerator: CreatureGenerator = CreatureGenerator(),
                        private val repository: CreatureRepository =  RoomRepository()): ViewModel() {

    private val creatureLiveData = MutableLiveData<Creature>()

    private val saveLiveData = MutableLiveData<Boolean>()

    fun getCreatureLiveData(): LiveData<Creature> = creatureLiveData

    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData

    var name = ObservableField<String>("")
    var intelligence = 0
    var strenght = 0
    var endurance = 0
    var drawable = 0

    lateinit var creature: Creature

    fun updateCreature() {
        val attributes = CreatureAttributes(intelligence, strenght, endurance)
        creature = creatureGenerator.generateCreature(attributes, name.get() ?: "", drawable)
        creatureLiveData.postValue(creature)

    }

    fun attributeSelected(attributeType: AttributeType, position: Int) {
        when (attributeType) {
            AttributeType.INTELLIGENCE ->
                intelligence = AttributeStore.INTELLIGENCE[position].value
            AttributeType.STRENGTH ->
                strenght = AttributeStore.STRENGTH[position].value
            AttributeType.ENDURANCE ->
                endurance = AttributeStore.ENDURANCE[position].value
        }

        updateCreature()

    }

    fun drawableSelected(drawable: Int) {
        this.drawable = drawable
        updateCreature()
    }

    fun saveCreature() {
        return if(canSaveCreature()) {
            repository.saveCreature(creature)
            saveLiveData.postValue(true)
        }else {
            saveLiveData.postValue(false)
        }
    }

    fun canSaveCreature(): Boolean {
        val name = this.name.get()
        name?.let {
            return intelligence != 0 && strenght != 0 && endurance != 0 &&
                    name.isNotEmpty() && drawable != 0
        }
        return false
    }
}