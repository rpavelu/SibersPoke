package com.example.siberspoke

import androidx.lifecycle.Transformations.map
import com.example.siberspoke.screens.pokeinfo.data.PokemonInfo
import com.example.siberspoke.screens.pokeinfo.data.network.PokeInfoResponse
import com.example.siberspoke.screens.pokelist.data.Pokemon
import com.example.siberspoke.screens.pokelist.data.network.PokeListResponse

interface PokemonsNetworkConverter {
    fun convertList(response: PokeListResponse): MutableList<Pokemon>
    fun convertInfo(response: PokeInfoResponse): PokemonInfo
}

private const val IMAGE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
private const val URL_DIVIDER = '/'
private const val IMAGE_SUFFIX = ".png"

class PokemonsNetworkConverterImpl : PokemonsNetworkConverter {

    override fun convertInfo(response: PokeInfoResponse): PokemonInfo =
        PokemonInfo(response.height, response.weight, response.types[0].type.name)

//        response.types.map { typesList ->
//            val mTypesList = typesList.type
//            mTypesList.map { typesTypeList ->
//                val mName = typesTypeList.name
//            }
//            PokemonInfo(response.height, response.weight, mName)
//        }

    override fun convertList(response: PokeListResponse): MutableList<Pokemon> =
        response.results.map { dtoList ->
            val pokemonId = dtoList.url.split(URL_DIVIDER)
                .filterNot {
                    it.isBlank()
                }.last()

            Pokemon(
                dtoList.name,
                IMAGE_URL + pokemonId + IMAGE_SUFFIX
            )
        }.toMutableList()
}