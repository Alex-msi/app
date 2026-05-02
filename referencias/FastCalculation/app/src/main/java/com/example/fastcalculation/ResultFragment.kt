package com.example.fastcalculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fastcalculation.Extras.EXTRA_SETTINGS
import com.example.fastcalculation.databinding.FragmentGameBinding
import com.example.fastcalculation.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var fragmentResultBinding: FragmentResultBinding
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(EXTRA_SETTINGS) ?: Settings()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentResultBinding = FragmentResultBinding.inflate(inflater, container, false)

        arguments?.getFloat("POINTS") ?.also { points ->
            fragmentResultBinding.resultTv.text = "Your score: %.1f".format(points)
        }


        fragmentResultBinding.restartGameBt.setOnClickListener {
            (context as OnPlayGame).onPlayGame()
        }
        return fragmentResultBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(settings: Settings) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS, settings)

                }
            }
    }
}