package com.example.wordsfactory.ui.navigation_fragments.dictionary

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
import com.example.wordsfactory.databinding.FragmentDictionaryBinding
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse
import com.example.wordsfactory.dictionary_logic.repository.Injection
import com.example.wordsfactory.dictionary_logic.repository.app_viewmodel.AppViewModel
import com.example.wordsfactory.ui.navigation_fragments.dictionary.adapter.WordAdapter
import java.io.IOException


class DictionaryFragment : Fragment() {
    private var flag: Boolean = true
    private val mediaPlayer = MediaPlayer()
    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!
    private val adapter = WordAdapter()


    // TODO: update shared prefs when word added
    // TODO: support all words parsing from json
    private lateinit var appViewModel: AppViewModel
    private lateinit var dictionaryViewModel: DictionaryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel = ViewModelProvider(this, Injection.provideFactory(requireContext()))
            .get(AppViewModel::class.java)
        dictionaryViewModel = ViewModelProvider(this)[DictionaryViewModel::class.java]

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
        binding.botButton.setOnClickListener {
            val wordEntity = appViewModel.checkDbForWord(dictionaryViewModel.getCurrentWordName())
            if (wordEntity == null) {
                appViewModel.saveToDb(dictionaryViewModel.getCurrentWordObject())
                Toast.makeText(context, getString(R.string.wordIsSaved), Toast.LENGTH_SHORT).show()
            } else Toast.makeText(context, getString(R.string.wordIsAlready), Toast.LENGTH_SHORT)
                .show()
        }
        binding.listener.setOnClickListener {
            if (dictionaryViewModel.getCurrentWordSound() != getString(R.string.none)) {
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
        binding.searchBar.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
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
        binding.meaningHolder.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.meaningHolder.adapter = adapter
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
        binding.blankBack.visibility = View.VISIBLE
        with(binding) {
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
        binding.blankBack.visibility = View.INVISIBLE
        with(binding) {
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
            bindResponse(dictionaryViewModel.getCurrentWordObject())
        }
    }

    private fun bindResponse(response: WordResponse) {
        val word = dictionaryViewModel.responseToWordEntity(response)
        bindResponse(word)
    }

    private fun bindResponse(word: WordEntity) {
        showAllViews()
        bindWordInfo(word)
        dictionaryViewModel.setCurrentWordInfo(word)
    }

    private fun bindWordInfo(word: WordEntity) {
        with(binding) {
            setMediaPlayer(word)
            wordName.text = word.word
            transcription.text = word.transcription
            partOfSpeechAnswer.text = word.partOfSpeech
            adapter.initList(word.meanings)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
