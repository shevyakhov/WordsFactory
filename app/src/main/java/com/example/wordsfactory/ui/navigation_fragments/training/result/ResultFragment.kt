package com.example.wordsfactory.ui.navigation_fragments.training.result

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.StatsWidget
import com.example.wordsfactory.databinding.FragmentResultBinding
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.repository.Injection


@Suppress("UNCHECKED_CAST")
class ResultFragment : Fragment() {
    private lateinit var resultViewModel: ResultViewModel

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultViewModel = ViewModelProvider(
            this,
            Injection.provideFactoryResult(requireContext())
        )[ResultViewModel::class.java]

        val changedList: List<WordEntity> = requireArguments().get(CHANGED_LIST) as List<WordEntity>
        val result = requireArguments().get(RESULT) as Pair<Int, Int>

        resultViewModel.updateDb(changedList)

        showTrainingResult(result)
        updateWidget()

        binding.againButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_trainingFragment)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showTrainingResult(result: Pair<Int, Int>) {
        binding.correctNumberText.text = getString(R.string.correct) + result.first
        binding.incorrectNumberText.text =
            getString(R.string.incorrect) + (result.second - result.first)
    }

    private fun updateWidget() {

        val statistics = resultViewModel.getStatistics()
        val rememberWordsText = "${statistics.first} ${getString(R.string.widgetWordsText)}"
        val dictionaryWordsText = "${statistics.second} ${getString(R.string.widgetWordsText)}"

        val remoteViews = RemoteViews(context?.packageName, R.layout.stats_widget)
        remoteViews.setTextViewText(R.id.appwidget_text_remember_stats, rememberWordsText)
        remoteViews.setTextViewText(R.id.appwidget_text_my_dictionary_stats, dictionaryWordsText)

        val appWidget = context?.let { ComponentName(it, StatsWidget::class.java) }
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(appWidget, remoteViews)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var CHANGED_LIST: String = "CHANGED_LIST"
        var RESULT: String = "result"
    }

}