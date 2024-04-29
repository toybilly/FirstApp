package com.marianevieira.firstapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marianevieira.firstapp.R
import com.marianevieira.firstapp.databinding.ListItemPessoaBinding
import com.marianevieira.firstapp.service.model.Pessoa

class PessoaAdapter(pessoas: List<Pessoa>?, private val clickListListener: (Pessoa) -> Unit) :
    RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>() {

    //Criar uma lista vazia de pessoa
    private var pessoaList: List<Pessoa> = arrayListOf()


    class PessoaViewHolder(private val binding: ListItemPessoaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Carrega as informações da pessoa na lista
        fun bind(pessoa: Pessoa, clickListListener: (Pessoa) -> Unit) {
            binding.tvNome.text = pessoa.nome
            binding.tvIdade.text = pessoa.idade.toString() + "anos"
            binding.tvFaixa.text = pessoa.faixa


            // Metodo 1 para esconder ou mostrar uma imagem
            if (pessoa.sexo == "Feminino") {
                binding.imgFeminino.visibility = View.VISIBLE
                binding.imgMasculino.visibility = View.GONE
            } else{
                binding.imgFeminino.visibility = View.GONE
                binding.imgMasculino.visibility = View.VISIBLE
            }


            //Configura o click de algum item na lista
            binding.root.setOnClickListener {
                clickListListener(pessoa)
            }
        }

//            //metodo 2 para mostrar uma imagem desejada
//            if (pessoa.sexo == "feminino"){
//                binding.imgFeminino.setImageResource((R.drawable.ic_girl))
//            }else {
//                binding.imgFeminino.setImageResource((R.drawable.ic_boy))
//            }
//            binding.root.setOnClickListener {
//                clickListListener(pessoa)
//            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        //Configura o binding da lista
        val listItemPessoaBinding =
            ListItemPessoaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PessoaViewHolder(listItemPessoaBinding)
    }

    override fun getItemCount(): Int {
        return pessoaList.count()
    }

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        holder.bind(pessoaList[position], clickListListener)
    }

    fun updatePessoas(list: List<Pessoa>) {
        pessoaList = list
        notifyDataSetChanged()
    }
}