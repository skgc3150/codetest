/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val binding= FragmentOneBinding.bind(view)

        val viewModel= context?.let { OneViewModel(it) }

        val layoutManager= LinearLayoutManager(context)
        val dividerItemDecoration=
            DividerItemDecoration(context, layoutManager.orientation)
        val adapter= CustomAdapter(object : CustomAdapter.OnItemClickListener{
            override fun itemClick(item: item){
                gotoRepositoryFragment(item)
            }
        })

        //検索ワード入力
        binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    if (viewModel != null) {
                        editText.text.toString().let {
                            viewModel.searchResults(it).apply{
                                adapter.submitList(this)
                            }
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        //検索候補表示
        binding.recyclerView.also{
            it.layoutManager= layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter= adapter
        }
    }

    //フラグメントの移動
    fun gotoRepositoryFragment(item: item)
    {
        val action= OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item= item)
        findNavController().navigate(action)
    }
}

val diff_util= object: DiffUtil.ItemCallback<item>(){
    override fun areItemsTheSame(oldItem: item, newItem: item): Boolean
    {
        return oldItem.name== newItem.name
    }

    override fun areContentsTheSame(oldItem: item, newItem: item): Boolean
    {
        return oldItem== newItem
    }

}