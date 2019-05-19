package org.suwashizmu.livedatasample

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by KEKE on 2019/05/19.
 *
 * 以下のLiveDataが必要
 * - loading中を示す
 * - firstName
 * - lastName
 * - prefecture
 * - casSubmit
 */
class MainViewModel : ViewModel() {

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()

    //Spinnerの選択位置を保持する
    val spinnerPosition = MutableLiveData<Int>()

    private val _canSubmit = MediatorLiveData<Boolean>().apply {

        val observer = Observer<Any> {
            val firstName = firstName.value ?: ""
            val lastName = lastName.value ?: ""
            val spinnerPosition = spinnerPosition.value ?: -1
            this.value = firstName.isNotEmpty() && lastName.isNotEmpty() && spinnerPosition != -1
        }

        addSource(firstName, observer)
        addSource(lastName, observer)
        addSource(spinnerPosition, observer)
    }
    val canSubmit: LiveData<Boolean> = _canSubmit

    //nullだとTransformationsが反応されないため空のListを入れる
    private val _prefectures = MutableLiveData<List<String>>().also {
        it.value = emptyList()
    }
    val prefectures: LiveData<List<String>> = _prefectures


    private val _isLoading = MutableLiveData<Boolean>().also {
        it.value = true
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val repository = PrefectureRepository()

    fun loadData() {

        if (isLoading.value == false) return

        GlobalScope.launch(Dispatchers.Main) {
            _prefectures.value = repository.fetchPrefectures()
            _isLoading.value = false
        }
    }
}