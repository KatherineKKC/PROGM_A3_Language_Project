package com.katherinekurokawa.definitiveprojecy.Difu

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.katherinekurokawa.definitiveprojecy.entities.Language

class LanguageDifutil(
    private val oldList: List<Language>,
    private val newList: List<Language>) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
      return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idLanguage == newList[newItemPosition].idLanguage
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition] == newList[newItemPosition]
    }

}