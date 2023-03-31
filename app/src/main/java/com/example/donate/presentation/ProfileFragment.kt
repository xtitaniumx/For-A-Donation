package com.example.donate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donate.R
import com.example.donate.databinding.FragmentProfileBinding
import com.example.donate.presentation.adapter.ProfileOptionAdapter
import com.example.donate.presentation.model.ProfileOptionItem
import com.example.donate.presentation.vm.ProfileViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(), ProfileOptionAdapter.OnClickListener {
    private val vm by viewModel<ProfileViewModel>()
    private lateinit var binding: FragmentProfileBinding

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getUser()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        val divider = MaterialDividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        listProfileOptions.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(divider)
            adapter = ProfileOptionAdapter(
                listOf(
                    ProfileOptionItem(
                        optionImage = R.drawable.ic_profile,
                        optionName = "Настройки профиля",
                        optionDesc = ""
                    ),
                    ProfileOptionItem(
                        optionImage = R.drawable.ic_family,
                        optionName = "Настройки семьи",
                        optionDesc = ""
                    ),
                    ProfileOptionItem(
                        optionImage = R.drawable.ic_premium,
                        optionName = "Премиум подписка",
                        optionDesc = "Активирована / Не активирована"
                    )
                ),
                this@ProfileFragment
            )
            setHasFixedSize(true)
        }

        vm.userLive.observe(viewLifecycleOwner) { user ->
            textProfileName.text = user?.name
        }

        buttonLogOut.setOnClickListener {
            
        }
    }

    override fun onProfileOptionClick(item: ProfileOptionItem) {
        Toast.makeText(requireActivity(), item.optionName, Toast.LENGTH_SHORT).show()
    }
}