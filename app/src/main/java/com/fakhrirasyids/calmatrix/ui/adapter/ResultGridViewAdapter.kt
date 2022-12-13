package com.fakhrirasyids.calmatrix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.fakhrirasyids.calmatrix.R
import com.fakhrirasyids.calmatrix.databinding.GridViewItemBinding
import com.fakhrirasyids.calmatrix.databinding.ResultGridViewItemBinding
import com.google.android.material.textfield.TextInputLayout

class ResultGridViewAdapter(private val context: Context, private var matrix: Array<Array<Float>>) :
    BaseAdapter() {
    private lateinit var holder: ViewHolder

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (convertView == null) {
            val binding =
                ResultGridViewItemBinding.inflate(
                    LayoutInflater.from(parent?.context),
                    parent,
                    false
                )
            holder = ViewHolder(binding)
            holder.view = binding.root
            holder.matrix = matrix
            (holder.view as TextInputLayout).tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        return holder.view
    }

    override fun getItem(position: Int): Any {
        return matrix[position % matrix.size][position - (position - position % matrix.size)]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return matrix.size * matrix[0].size
    }

    private class ViewHolder(binding: ResultGridViewItemBinding) {
        var view: View? = null
        lateinit var matrix: Array<Array<Float>>
        private var binding: ResultGridViewItemBinding? = binding

        init {
            view = binding.root
            this.binding = binding

        }
    }

}