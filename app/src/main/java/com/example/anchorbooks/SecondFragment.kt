package com.example.anchorbooks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SecondFragment : Fragment() {

    private val myViewModel: BookViewModel by activityViewModels()
    private lateinit var bookTitle: String
    private var bookId = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
         * Carga en el fragment_second, los detalles del libro seleccionado
         */
        myViewModel.bookSelection.observe(viewLifecycleOwner, { id ->

            bookId = id
            Log.d("id", id.toString())

            myViewModel.getOneBookDetails(bookId).observe(viewLifecycleOwner, {

                it?.let {

                    val imageSelected = view.findViewById<ImageView>(R.id.imageDetail)
                    Glide.with(this).load(it.imageLink).fitCenter().into(imageSelected)

                    val title = view.findViewById<TextView>(R.id.tvTitle)
                    val author = view.findViewById<TextView>(R.id.tvAuthor)
                    val country = view.findViewById<TextView>(R.id.tvCountry)
                    val language = view.findViewById<TextView>(R.id.tvLanguage)

                    title.text = it.title
                    author.text = it.author
                    country.text = it.country
                    language.text = it.language

                    bookTitle = it.title

                }
            })
        })

        /*
         * Construye un mensaje de correo electrónico al presionar el botón (ic_dialog_email)
         */
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val address = getString(R.string.email_address)
            val subject = getString(R.string.email_subject) +
                    " " + bookTitle +
                    " id " + bookId
            val text = getString(R.string.email_text1) +
                    " " + bookTitle +
                    " " + getString(R.string.email_text2) +
                    " " + bookId +
                    " " + getString(R.string.email_text3)
            composeEmail(address, subject, text)

        }

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }

    /*
     * Ejecuta la acción de abrir el cliente de correo con el mensaje precargado
     */
    private fun composeEmail(address: String, subject: String, text: String) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, address)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "e.message", Toast.LENGTH_LONG).show()
        }

    }
}