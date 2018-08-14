package com.hmomeni.filterablerecyclerview

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable

class MainViewModel : ViewModel() {

    private val originalPosts = listOf(
            Post(1, "Ubi est castus animalis?", "Albus solems ducunt ad plasmator. Amors trabem in brigantium! Nutrix experimentums, tanquam camerarius ventus."),
            Post(2, "Always spiritually gain the crystal power.", "When one traps conclusion and attitude, one is able to fear surrender.Yes, there is order, it empowers with bliss."),
            Post(3, "Place the truffels in a casserole, and rub ultimately with whole teriyaki.", "Place the tuna in a pan, and mix carefully muddy with melted carrots lassi. Per guest prepare twenty tablespoons of eggs lassi with warmed ramen for dessert."),
            Post(4, "Why does the scabbard fall?", "Jolly halitosis lead to the amnesty. The pants whines beauty like a rainy moon. When the wind screams for puerto rico, all suns hail rough, clear seashells. Scallywags sing from malarias like salty ships."),
            Post(5, "Impressively translate a ferengi.", "The tragedy is an interstellar nanomachine. Wobble wihtout starlight travel, and we won’t promise a space. Meet wihtout nuclear flux, and we won’t transform a sonic shower. When the crewmate views for deep space, all starships travel intelligent, cloudy transporters.")
    )

    val filteredPosts: MutableList<Post> = mutableListOf()
    val oldFilteredPosts: MutableList<Post> = mutableListOf()

    init {
        oldFilteredPosts.addAll(originalPosts)
    }

    fun search(query: String): Completable = Completable.create {
        val wanted = originalPosts.filter {
            it.title.contains(query) || it.content.contains(query)
        }.toList()

        filteredPosts.clear()
        filteredPosts.addAll(wanted)
        it.onComplete()
    }


}