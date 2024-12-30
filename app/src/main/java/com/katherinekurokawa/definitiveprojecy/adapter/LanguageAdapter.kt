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
    var listLanguage: MutableList<Language>,
    private val onItemLongClick: (String) -> Unit,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageHolder>() {


    //--------------------------------------------METODOS IMPLEMENTADOS-----------------------------------------------------//

    //1. Inflamos la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLanguagesBinding.inflate(inflater, parent, false)
        return LanguageHolder(binding)
    }

    // 2. Conectar los elementos
    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        val language = listLanguage[position]
        holder.binding.tvNameLanguage.text = language.name
        //Acción para elimminar el lenguaje con click largo
        holder.binding.root.setOnLongClickListener {
            onItemLongClick(language.name)
            true
        }
        //Acción para modificar el lenguaje con un click
        holder.binding.root.setOnClickListener {
            onItemClick(language.name)
            true
        }
    }

    override fun getItemCount(): Int {
        return listLanguage.size
    }


    //--------------------------------------------FUNCIONES-----------------------------------------------------//
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

    fun removeAllLanguage() {
        listLanguage.clear()
        notifyDataSetChanged()
    }

    //--------------------------------------------HOLDER-----------------------------------------------------//
    inner class LanguageHolder(val binding: ItemLanguagesBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}