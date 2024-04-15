package com.marianevieira.firstapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.marianevieira.firstapp.R
import com.marianevieira.firstapp.databinding.FragmentAllPessoasBinding
import com.marianevieira.firstapp.view.adapter.PessoaAdapter
import com.marianevieira.firstapp.viewmodel.AllPessoasViewModel

class AllPessoasFragment : Fragment() {

    // Chamar a view Model
    private val viewModel: AllPessoasViewModel by viewModels()

   // chamar o adapter
    private lateinit var adapter: PessoaAdapter

    //Criar o binding
    private var _binding: FragmentAllPessoasBinding? = null

    private val binding: FragmentAllPessoasBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Configurar o binding
        _binding = FragmentAllPessoasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Quando clicar em algum item da lista
        adapter = PessoaAdapter(viewModel.pessoaList.value){

        }

        //configurar o recycle
        val recycler = binding.rvPessoas
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        //observa para adicionar um item na lista quando for adicionado
        viewModel.pessoaList.observe(viewLifecycleOwner){
            adapter.updatePessoas(it)
        }

        //navegar para a tela de cadastro de pessoa
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.pessoaFragment)
        }
        // Carregar as pessoas cadastradas
        viewModel.loadPessoas()
    }
}