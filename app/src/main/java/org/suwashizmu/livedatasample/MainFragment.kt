package org.suwashizmu.livedatasample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.suwashizmu.livedatasample.databinding.MainFragmentBinding

/**
 * Created by KEKE on 2019/05/19.
 */
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setupObserver()
        mainViewModel.loadData()
    }

    private fun setupObserver() {
        mainViewModel.prefectures.observe(viewLifecycleOwner, Observer {
            binding.spinner.adapter =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, it).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
        })

        //spinnerの位置を復元,これを入れないと画面回転時に0になる
        mainViewModel.spinnerPosition.observe(viewLifecycleOwner, Observer {
            binding.spinner.setSelection(it)
        })
    }
}