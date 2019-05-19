# LiveDataのサンプル

## LifecycleのReleaseNote


https://developer.android.com/jetpack/androidx/releases/lifecycle

## Two-way attributes
https://developer.android.com/topic/libraries/data-binding/two-way#two-way-attributes


# Note

## Spinnerの復元がうまくいかない

以下のようにTwoBindingしてくれると思いきや  
画面回転時にPositionが0になるバグがあった

おそらく画面回転時にAdapterを再設定しているため  
その時にsetSelection(0)がコールされ  
livaDataの値が設定される？  



```main_fragment.xml

        <androidx.appcompat.widget.AppCompatSpinner
                android:id="@+id/spinner"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"

                app:layout_constraintStart_toStartOf="@id/guideline"
                app:layout_constraintTop_toBottomOf="@+id/lastNameInputLayout"

                android:selectedItemPosition="@={viewModel.spinnerPosition}"

                android:layout_marginTop="16dp"/>

```

fragment内でsetSelectionを呼ぶことで正常に動作した  

```MainFragment.kt
mainViewModel.spinnerPosition.observe(viewLifecycleOwner, Observer {
            binding.spinner.setSelection(it)
        })
```

## MediatorLiveData

`addSource(LiveData,Observer)`で他のLiveDataを購読することができる  
送信ボタンは全ての入力を完了させれば押せるようにするなどの  
複数にまたがった条件をLiveDataで表現することが可能✌️

MediatorLiveDataへの値への更新処理の書き方が  
少し分かりづらいかも  

Observer定義して`MediatorLiveData.value = someObj`を呼べばOK!