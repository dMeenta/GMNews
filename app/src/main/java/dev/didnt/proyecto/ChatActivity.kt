package dev.didnt.proyecto

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.didnt.proyecto.adaptadores.MessageAdapter
import dev.didnt.proyecto.entidad.Message

class ChatActivity : AppCompatActivity() {

    private lateinit var rvMessages:RecyclerView
    private lateinit var btnSalirChat:ImageButton
    private lateinit var lblChatNombre:TextView
    private lateinit var inputMessage:EditText
    private lateinit var btnSendMessage:ImageButton
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var dbRef: DatabaseReference

    var receiverRoom:String? = null
    var senderRoom:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()

        var nombre = intent.getStringExtra("nombre")
        var receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        lblChatNombre.text = nombre

        btnSalirChat.setOnClickListener {
            finish()
        }

        dbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}

            })

        btnSendMessage.setOnClickListener {
            val message = inputMessage.text.toString()

            val messageObject = Message(message, senderUid)

            dbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            inputMessage.setText("")
        }
    }

    private fun asignarReferencias(){
        rvMessages = findViewById(R.id.rvChat)
        btnSalirChat = findViewById(R.id.btnSalirChat)
        lblChatNombre = findViewById(R.id.lblChatNombre)
        inputMessage = findViewById(R.id.inputMessage)
        btnSendMessage = findViewById(R.id.btnSend)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        dbRef = FirebaseDatabase.getInstance().reference
        rvMessages.layoutManager = LinearLayoutManager(this)
        rvMessages.adapter = messageAdapter
    }
}