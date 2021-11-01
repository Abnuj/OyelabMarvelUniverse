package com.oyelabs.marvel.universe.Repositories

import android.graphics.Paint
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.oyelabs.marvel.universe.Retrofit.RetrofitInstance
import com.oyelabs.marvel.universe.Utils
import org.json.JSONObject
import com.oyelabs.marvel.universe.DataClasses.CharacterData

class Repository {
    val characterList = mutableListOf<CharacterData>()

    suspend fun getAllCharacterList(offSet: Int = 1): Map<String, Any> {
        val response = RetrofitInstance.marvelAPI.getAllCharacters(
            offSet,
            Utils.publicKey,
            Utils.hash,
            Utils.ts
        )
        Log.d("Data", "response is :$response ")
        if (response.isSuccessful) {
            val dataString: String = Gson().toJson(response.body())
            val dataObject: JSONObject = JSONObject(dataString)
            fetchCharacterDataFromJson(dataObject)
            Log.d("Data", "data is :$dataObject ")
        }
        // marvel api send data with limit of 20 se we add 20 for our nextPageNumber
        val map = mapOf<String, Any>(Pair("dataList", characterList),
            Pair("nextPageNumber",Utils.CurrentOffSet))

        return map
    }

    private fun fetchCharacterDataFromJson(dataObject: JSONObject) {
        val resultList = dataObject.getJSONArray("results")
        for (i in 0..resultList.length() - 1) {
            val currentData = resultList.getJSONObject(i)
            val id = currentData.getInt("id")
            val name = currentData.getString("name")
            val thumbnail = currentData.getJSONObject("thumbnail").getString("path")
            val description = currentData.getString("description")
            characterList.add(CharacterData(id, name, description, thumbnail))
        }
    }
}

class ExamplePagingSource(
    val repository: Repository,
) : PagingSource<Int, CharacterData>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CharacterData> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 0
            val response: Map<String, Any> =
                repository.getAllCharacterList(nextPageNumber)
            return LoadResult.Page(
                data = response.get("dataList") as List<CharacterData>,
                prevKey = null, // Only paging forward.
                nextKey = response.get("nextPageNumber") as Int
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            Log.d("TAG", "we have exception:${e.message} ")
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }
}
