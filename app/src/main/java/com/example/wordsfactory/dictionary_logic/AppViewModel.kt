package com.example.wordsfactory.dictionary_logic

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wordsfactory.dictionary_logic.database.UserEntity
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppViewModel(private val repository: AppRepository) : ViewModel() {
    val observable: Subject<List<WordResponse>> = PublishSubject.create()

    /*pass word to observable*/
    fun searchNet(query: String) {
        val call = repository.searchNet(query)
        call.enqueue(object : Callback<List<WordResponse>> {
            override fun onResponse(
                call: Call<List<WordResponse>>,
                response: Response<List<WordResponse>>
            ) {
                val value = response.body()
                if (value != null) observable.onNext(value)
            }

            override fun onFailure(call: Call<List<WordResponse>>, t: Throwable) {
                Log.d(
                    "ex",
                    "Retrofit Exception -> " + if (t.message != null) t.message else "---"
                )
            }

        })
    }

    /*check if any user is created*/
    fun checkForUser(): Boolean {
        Log.e("user ", repository.searchUser().toString())
        return repository.searchUser()?.isEmpty() ?: true
    }

    /*save UserEntity to db*/
    fun saveUser(userEntity: UserEntity) {
        repository.saveUser(userEntity)
    }

    /*save WordEntity to db*/
    fun saveToDb(item: WordEntity) {
        repository.saveToDb(item)
    }

    /*check if word was saved*/
    fun checkDbForWord(item: String): WordEntity? {
        return repository.searchDb(item)
    }
}



