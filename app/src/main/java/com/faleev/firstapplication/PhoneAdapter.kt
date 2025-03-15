package com.faleev.firstapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhoneAdapter : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {
    private var mPhoneList: ArrayList<PhoneModel> = ArrayList()

    fun setupPhones(phoneList: ArrayList<PhoneModel>) {
        mPhoneList.clear()
        mPhoneList.addAll(phoneList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return PhoneViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(mPhoneList[position])
    }

    override fun getItemCount(): Int = mPhoneList.size

    inner class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPhoneName: TextView = itemView.findViewById(R.id.tv_phone_name)
        private val tvCameraRating: TextView = itemView.findViewById(R.id.tv_camera_rating)

        fun bind(phone: PhoneModel) {
            tvPhoneName.text = phone.phoneName
            tvCameraRating.text = "Рейтинг камеры: ${phone.cameraRating}"
        }
    }
}