package com.example.wordsfactory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfactory.adapters.WordAdapter
import com.example.wordsfactory.adapters.WordItem
import com.example.wordsfactory.databinding.FragmentDictionaryBinding
import com.example.wordsfactory.dictionary_logic.AppViewModel
import com.example.wordsfactory.dictionary_logic.Injection
import com.example.wordsfactory.dictionary_logic.database.AppDatabase
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.database.WordResponse


class DictionaryFragment : Fragment() {
    var flag: Boolean = true
    private var currentWord = WordEntity(word = "", transcription = "", sound = "", partOfSpeech = "", meanings = listOf())
    private val adapter = WordAdapter()

    companion object {
        @JvmStatic
        fun newInstance() =
            DictionaryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private lateinit var vm: AppViewModel
    private lateinit var mDatabase: AppDatabase
    private lateinit var binding: FragmentDictionaryBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this, Injection.provideFactory(requireContext()))
            .get(AppViewModel::class.java)
        doBinding()
        checkObservable()
    }

    private fun doBinding() {

        binding.meaningHolder.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.meaningHolder.adapter = adapter

        hideAll()
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    vm.searchNet(p0)
                    if (flag) {
                        binding.blankBack.visibility = View.INVISIBLE
                        showAll()
                        flag = false
                    }

                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
        binding.botButton.setOnClickListener {
           val i = vm.checkDbForWord(currentWord.word)
           /* vm.saveToDb(currentWord)*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDictionaryBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun hideAll() {
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

    private fun showAll() {
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


    private fun checkObservable() {
        vm.observable.subscribe {
            bindResponse(it)
        }
    }

    private fun bindResponse(list: List<WordResponse>) {
        with(binding) {
            listener /* todo sound*/

            wordName.text = list[0].word
            transcription.text = list[0].phonetic
            partOfSpeechAnswer.text = list[0].meanings[0].partOfSpeech
            var listing = mutableListOf<WordItem>()
            for (i in list[0].meanings) {
                Log.e("def", "${i.definitions[0].definition} - ${i.definitions[0].example}")
                val word = WordItem(
                    i.definitions[0].definition, i.definitions[0].example
                )
                listing.add(
                    word
                )
                adapter.initList(listing)

                currentWord.meanings = listing
                currentWord.sound = list[0].phonetics[0].audio
                currentWord.word = list[0].word
                currentWord.transcription = list[0].phonetic
                currentWord.partOfSpeech = list[0].meanings[0].partOfSpeech


            }
        }
    }
}