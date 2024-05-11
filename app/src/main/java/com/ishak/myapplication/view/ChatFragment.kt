package com.ishak.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.ishak.myapplication.adapter.ChatRecyclerViewAdapter
import com.ishak.myapplication.databinding.FragmentChatBinding
import com.ishak.myapplication.model.Chat


class ChatFragment : Fragment() {


    lateinit var binding:FragmentChatBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore

    private lateinit var adapter: ChatRecyclerViewAdapter
    private var chats= arrayListOf<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
        fireStore=Firebase.firestore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentChatBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= ChatRecyclerViewAdapter()
        binding.recyclerView.adapter=adapter

        binding.recyclerView.layoutManager= LinearLayoutManager(requireContext())




        binding.recyclerViewButton.setOnClickListener {

            val userEmail = auth.currentUser!!.email
            val message = binding.recyclerViewText.text.toString()

            //güncel tarihi alır
            val date = FieldValue.serverTimestamp()
            val image_url = ""
            val saveWithMap = HashMap<String, Any>()
            if (userEmail != null) {
                saveWithMap.put("userEmail", userEmail)
            }
            saveWithMap.put("message", message)
            saveWithMap.put("date", date)
            saveWithMap.put("image_url", image_url)

            if (message != ""){

                fireStore.collection("Chats").add(saveWithMap).addOnSuccessListener {
                    binding.recyclerViewText.setText("")

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                    binding.recyclerViewText.setText("")
                }

            }
            else{
                Toast.makeText(requireContext(),"you can't leave a blank message", Toast.LENGTH_LONG).show()
            }

        }

        fireStore.collection("Chats").orderBy("date",
            Query.Direction.ASCENDING).addSnapshotListener{ value, error->

            if(error!=null){
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
            }
            else{
                if (value != null) {
                    if(value.isEmpty){
                        Toast.makeText(requireContext(),"there is not message",Toast.LENGTH_LONG).show()
                    }
                    else{

                        val documents=value.documents

                        println("---------------doc"+documents.size)
                        chats.clear()
                        for(document in documents){

                            //anahtar değerlerini doğru yazmak gerekiyor.eğer anahtar bulunamazsa null olarak dönüş yapar
                            val message=document.get("message") as String
                            val userEmail=document.get("userEmail") as String
                            val image_url=document.get("image_url") as String
                            val chat=Chat(userEmail,message,image_url)

                            chats.add(chat)
                            //bunu for dışında yapsak daha iyi olur sanırım
                            adapter.chats=chats
                            println("adapter")
                        }

                    }
                    adapter.notifyDataSetChanged()
                }
            }

        }

    }


}