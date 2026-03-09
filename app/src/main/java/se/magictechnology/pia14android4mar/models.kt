package se.magictechnology.pia14android4mar

import kotlinx.serialization.Serializable


@Serializable
data class TrelloBoard(val name : String, val desc : String)

@Serializable
data class TrelloList(val id : String, val name : String)

@Serializable
data class TrelloCard(val id : String, var name : String, var desc: String)

