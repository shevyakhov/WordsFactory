package com.example.wordsfactory.fragments

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfactory.R
import com.example.wordsfactory.adapters.WordAdapter
import com.example.wordsfactory.adapters.WordItem
import com.example.wordsfactory.databinding.FragmentDictionaryBinding
import com.example.wordsfactory.dictionary_logic.AppViewModel
import com.example.wordsfactory.dictionary_logic.Injection
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import java.io.IOException


class DictionaryFragment : Fragment() {
    private var flag: Boolean = true
    private val mediaPlayer = MediaPlayer()

    private var currentWord = WordEntity(
        word = "",
        transcription = "",
        sound = "",
        partOfSpeech = "",
        meanings = listOf()
    )
    private val adapter = WordAdapter()


    private lateinit var appViewModel: AppViewModel
    private lateinit var fragmentDictionaryBinding: FragmentDictionaryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDictionaryBinding = FragmentDictionaryBinding.inflate(layoutInflater)
        return fragmentDictionaryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel = ViewModelProvider(this, Injection.provideFactory(requireContext()))
            .get(AppViewModel::class.java)

        hideAllViews()
        initViews()
        subscribeToObservable()
    }

    private fun initViews() {
        setRecyclerView()
        setAudioPlayer()
        setSearchBar()
        setClickListeners()
    }

    private fun setClickListeners() {
        fragmentDictionaryBinding.botButton.setOnClickListener {
            val wordEntity = appViewModel.checkDbForWord(currentWord.word)
            if (wordEntity == null) {
                appViewModel.saveToDb(currentWord)
                Toast.makeText(context, getString(R.string.wordIsSaved), Toast.LENGTH_SHORT).show()
            } else Toast.makeText(context, getString(R.string.wordIsAlready), Toast.LENGTH_SHORT)
                .show()
        }
        fragmentDictionaryBinding.listener.setOnClickListener {
            if (currentWord.sound != getString(R.string.none)) {
                try {
                    mediaPlayer.start()
                } catch (e: Exception) {
                    Log.e("e", e.message.toString())
                    mediaPlayer.release()
                    mediaPlayer.start()
                }
            } else
                Toast.makeText(context, getString(R.string.noSound), Toast.LENGTH_SHORT).show()

        }
    }

    /*search net if word is not saved in room db*/
    private fun setSearchBar() {
        fragmentDictionaryBinding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val i = appViewModel.checkDbForWord(query)
                    if (i == null)
                        appViewModel.searchNet(query)
                    else bindResponse(i)

                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
    }

    private fun setRecyclerView() {
        fragmentDictionaryBinding.meaningHolder.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        fragmentDictionaryBinding.meaningHolder.adapter = adapter
    }

    private fun setAudioPlayer() {
        mediaPlayer.setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
    }

    private fun hideAllViews() {
        fragmentDictionaryBinding.blankBack.visibility = View.VISIBLE
        with(fragmentDictionaryBinding) {
            partOfSpeech.visibility = View.INVISIBLE
            listener.visibility = View.INVISIBLE
            wordName.visibility = View.INVISIBLE
            transcription.visibility = View.INVISIBLE
            meaningHolder.visibility = View.INVISIBLE
            partOfSpeechAnswer.visibility = View.INVISIBLE
            meanings.visibility = View.INVISIBLE
            botButton.visibility = View.INVISIBLE
        }
    }

    private fun showAllViews() {
        fragmentDictionaryBinding.blankBack.visibility = View.INVISIBLE
        with(fragmentDictionaryBinding) {
            partOfSpeech.visibility = View.VISIBLE
            listener.visibility = View.VISIBLE
            wordName.visibility = View.VISIBLE
            transcription.visibility = View.VISIBLE
            meaningHolder.visibility = View.VISIBLE
            partOfSpeechAnswer.visibility = View.VISIBLE
            meanings.visibility = View.VISIBLE
            botButton.visibility = View.VISIBLE
        }
    }

    private fun subscribeToObservable() {
        appViewModel.observable.subscribe {
            if (it.isEmpty()) {
                Toast.makeText(context, getString(R.string.fakeWord), Toast.LENGTH_SHORT).show()
            } else {
                try {
                    bindResponse(it.first())
                    if (flag) {
                        showAllViews()
                        flag = false
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!flag) {
            showAllViews()
            bindResponse(currentWord)
        }
    }

    private fun bindResponse(response: WordResponse) {
        val word = WordEntity(
            word = response.word,
            meanings = getDefinitions(response),
            sound = setAudio(response),
            transcription = setPhonetic(response),
            partOfSpeech = response.meanings[0].partOfSpeech
        )
        bindResponse(word)
    }

    private fun bindResponse(word: WordEntity) {
        showAllViews()
        bindWordInfo(word)
        setCurrentWord(word)
    }

    private fun bindWordInfo(word: WordEntity) {
        with(fragmentDictionaryBinding) {
            setMediaPlayer(word)
            wordName.text = word.word
            transcription.text = word.transcription
            partOfSpeechAnswer.text = word.partOfSpeech
            adapter.initList(word.meanings)
        }
    }

    private fun setPhonetic(word: WordResponse): String {
        return word.phonetic // don't fix - android studio is wrong
    }

    private fun setAudio(response: WordResponse): String {
        return if (response.phonetics.isNotEmpty()) {
            response.phonetics[0].audio
        } else {
            getString(R.string.none)
        }
    }

    private fun setCurrentWord(word: WordEntity) {
        currentWord.meanings = word.meanings
        currentWord.sound = word.sound
        currentWord.word = word.word
        currentWord.transcription = word.transcription
        currentWord.partOfSpeech = word.partOfSpeech
    }

    private fun setMediaPlayer(word: WordEntity) {
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(word.sound)
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            Log.e("e", e.message.toString())
        }
    }

    private fun getDefinitions(response: WordResponse): List<WordItem> {
        val listing = mutableListOf<WordItem>()
        for (i in response.meanings) {
            val word = WordItem(
                i.definitions[0].definition, i.definitions[0].example
            )
            listing.add(
                word
            )
        }
        return listing
    }
}
