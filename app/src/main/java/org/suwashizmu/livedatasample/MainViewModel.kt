package org.suwashizmu.livedatasample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
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

    private val _canSubmit = MutableLiveData<Boolean>()
    val canSubmit: LiveData<Boolean> = _canSubmit

    //nullだとTransformationsが反応されないため空のListを入れる
    private val _prefectures = MutableLiveData<List<String>>().also {
        it.value = emptyList()
    }
    val prefectures: LiveData<List<String>> = _prefectures
    val isLoading: LiveData<Boolean> = Transformations.map(prefectures) {
        it.isEmpty()
    }

    private val repository = PrefectureRepository()

    fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            _prefectures.value = repository.fetchPrefectures()
        }
    }
}