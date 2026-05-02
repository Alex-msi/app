package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.HabilidadeDao

class HabilidadeRepository(private val habilidadeDao: HabilidadeDao) {
    fun getByEixo(eixoCodigo: String) = habilidadeDao.getByEixo(eixoCodigo)
    fun getByCompetencia(competenciaCodigo: String) = habilidadeDao.getByCompetencia(competenciaCodigo)
    fun search(busca: String) = habilidadeDao.searchByCodigoOrDescricao(busca)
}