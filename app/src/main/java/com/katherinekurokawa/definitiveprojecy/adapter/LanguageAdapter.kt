package com.katherinekurokawa.definitiveprojecy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.katherinekurokawa.definitiveprojecy.Difu.LanguageDifutil
import com.katherinekurokawa.definitiveprojecy.databinding.ItemLanguagesBinding
import com.katherinekurokawa.definitiveprojecy.entities.Language

class LanguageAdapter(
    private var listLanguage: MutableList<Language>,
    private val onItemLongClick: (String) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLanguagesBinding.inflate(inflater, parent, false)
        return LanguageHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        val language = listLanguage[position]
        holder.binding.tvNameLanguage.text = language.name

        holder.binding.root.setOnLongClickListener {
            onItemLongClick(language.name)
            true
        }

        holder.binding.root.setOnClickListener{
            onItemLongClick(language.name)
            true
        }

    }

    override fun getItemCount(): Int {
        return listLanguage.size
    }


    inner class LanguageHolder(val binding: ItemLanguagesBinding) :
        RecyclerView.ViewHolder(binding.root) {}


    //FUNCIONES PARA EL ADAPTER


    fun submitLanguage(newList: List<Language>) {
        val diffCallback = LanguageDifutil(listLanguage, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // Actualizamos la lista y notificamos al RecyclerView
        listLanguage.clear()
        listLanguage.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeLanguage(nameLanguage: String) {
        val positionLanguage = listLanguage.indexOfFirst { it.name == nameLanguage }
        if (positionLanguage != -1) {
            listLanguage.removeAt(positionLanguage)
            notifyItemRemoved(positionLanguage)
        }
    }


}