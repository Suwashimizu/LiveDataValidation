package org.suwashizmu.livedatasample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _isLoading = MutableLiveData<Boolean>().also {
        it.value = true
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _canSubmit = MutableLiveData<Boolean>()
    val canSubmit: LiveData<Boolean> = _canSubmit

    private val _prefectures = MutableLiveData<List<String>>()
    val prefectures: LiveData<List<String>> = _prefectures

    private val repository = PrefectureRepository()

    fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            _prefectures.value = repository.fetchPrefectures()
        }
    }
}