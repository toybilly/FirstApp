package com.marianevieira.firstapp.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marianevieira.firstapp.databinding.FragmentPessoaBinding
import com.marianevieira.firstapp.service.model.Pessoa
import com.marianevieira.firstapp.viewmodel.PessoaViewModel
import java.time.LocalDateTime


class PessoaFragment : Fragment() {
    private val viewModel: PessoaViewModel by viewModels()
    private var _binding: FragmentPessoaBinding? = null
    private val binding: FragmentPessoaBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPessoaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.getPessoa(it.getInt("pessoaId"))
        }

        binding.btnEnviar.setOnClickListener {
            val nome = binding.edtNome.editableText.toString()
            val anoNascimento = binding.edtAnoNascimento.editableText.toString()
            var sexo = ""
            var faixa = ""

            if (nome != "" && anoNascimento != "" && binding.rbMasculino.isChecked || binding.rbFeminino.isChecked) {

                if (binding.rbMasculino.isChecked){
                    sexo = "Masculino"
                } else {
                    sexo = "Feminino"
                }

                val anoAtual = LocalDateTime.now().year
                val idade = anoAtual - anoNascimento.toInt()


                if  (idade <= 12){
                    faixa = "infantil"
                } else if( idade <= 17) {
                    faixa = "adolescente"
                } else if(idade <=64){
                    faixa = "adulto"
                } else {
                    faixa = "idoso"
                }

                val pessoa = Pessoa(
                    nome = nome,
                    idade = idade,
                    sexo = sexo,
                    faixa = faixa
                )

                viewModel.pessoa.value?.let {
                    pessoa.id = it.id
                    viewModel.update(pessoa)
                }?: run {
                    viewModel.insert(pessoa)
                }



                binding.edtNome.editableText.clear()
                binding.edtAnoNascimento.editableText.clear()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Digite seus dados", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnDeletar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusao de pessoa")
                .setMessage("Você realmente deseja excluir")
                .setPositiveButton("Sim") { _,_ ->
                    viewModel.delete(viewModel.pessoa.value?.id ?: 0)
                    findNavController().navigateUp()
                }

                .setNegativeButton("Não"){ _,_ ->}
                .show()
        }


        viewModel.pessoa.observe(viewLifecycleOwner){pessoa ->
            binding.edtNome.setText(pessoa.nome)
            binding.edtAnoNascimento.setText((LocalDateTime.now().year - pessoa.idade).toString())

            if (pessoa.sexo == "Masculino"){
                binding.rbMasculino.isChecked = true
            } else {
                binding.rbFeminino.isChecked = true
            }

            binding.btnDeletar.visibility = View.VISIBLE
        }
    }
}