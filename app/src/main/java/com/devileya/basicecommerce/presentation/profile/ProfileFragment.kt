package com.devileya.basicecommerce.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.devileya.basicecommerce.BR
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.databinding.ProfileFragmentBinding
import kotlinx.android.synthetic.main.profile_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
