package com.marianevieira.firstapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.marianevieira.firstapp.R
import com.marianevieira.firstapp.databinding.FragmentPessoaDetailBinding
import com.marianevieira.firstapp.viewmodel.PessoaViewModel
import java.time.LocalDateTime


class PessoaDetailFragment : Fragment() {

    //Chamar a viewmodel para pegar os dados
    private val viewModel: PessoaViewModel by viewModels()

    // Criar o binding para pegar os elementos da tela
    private var _binding: FragmentPessoaDetailBinding? = null

    private val binding: FragmentPessoaDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Configurar o binding
        _binding = FragmentPessoaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Chamar a função onViewCreated onde vamos implementar o codigo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pegar o id da pessoa que foi enviado pela AllPessoaFragment
        // Setar a pessoa para ser carregada
        arguments?.let {
            viewModel.getPessoa(it.getInt("pessoaId"))
        }

        // Carregar as informações da pessoa selecionada
        viewModel.pessoa.observe(viewLifecycleOwner) { pessoa ->
            binding.tvNome.setText("Nome: " + pessoa.nome)
            binding.tvIdade.setText("Idade: " + pessoa.idade)
            binding.tvFaixa.setText("Faixa Etária: " + pessoa.faixa)

            if (pessoa.sexo == "Feminino") {
                binding.imgFeminino.visibility = View.VISIBLE
                binding.imgMasculino.visibility = View.GONE
            } else {
                binding.imgFeminino.visibility = View.GONE
                binding.imgMasculino.visibility = View.VISIBLE
            }


        }
    }
}