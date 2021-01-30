package com.example.anchorbooks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anchorbooks.model.BookItem

class FirstFragment : Fragment(), BookAdapter.PassBookData {

    private val myViewModel: BookViewModel by activityViewModels()
    private lateinit var myBookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myBookAdapter= BookAdapter(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    /*
     * Relaciona el recycler view con el adaptador
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myBookAdapter

        myViewModel.allBooks.observe(viewLifecycleOwner, {
            Log.d("Books recieved", it.toString())
            myBookAdapter.updateAdapter(it)
        })

    }

    /*
     * Navega de la vista de listado a la de detalles, llevándose el id del libro seleccionado
     */
    override fun passItemInfo(book: BookItem) {
        val bookSelectedId = book.id
        myViewModel.bookSelected(bookSelectedId)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}