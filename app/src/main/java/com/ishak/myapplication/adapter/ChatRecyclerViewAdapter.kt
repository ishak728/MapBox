package com.ishak.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ishak.myapplication.R
import com.ishak.myapplication.model.Chat


class ChatRecyclerViewAdapter: RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatHolder>() {


    private val viewSentType=1
    private val viewreceivedType=2

    class ChatHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }


    private val diffUtil=object : DiffUtil.ItemCallback<Chat>(){

        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {

            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {

            return oldItem==newItem
        }

    }


    private val recylerListDiffer= AsyncListDiffer(this,diffUtil)

    var chats:List<Chat>
        get() =recylerListDiffer.currentList
        set(value)=recylerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat=chats.get(position)

        if(chat.user== FirebaseAuth.getInstance().currentUser?.email.toString()){


            return viewSentType
        }
        else{
            return viewreceivedType
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {


        if(viewType==viewreceivedType){
            val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_left,parent,false)

            return ChatHolder(view)
        }else{
            val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_right,parent,false)

            return ChatHolder(view)

        }

    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val textView=holder.itemView.findViewById<TextView>(R.id.chatRecyclerViewTextView)
        textView.text="${chats.get(position).text}"


    }

    override fun getItemCount(): Int {

        println("+++++++++++++++++chat sixe"+chats.size)
        return chats.size
    }
}