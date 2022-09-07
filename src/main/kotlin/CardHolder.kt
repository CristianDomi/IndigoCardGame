package indigo

open class CardHolder(protected val cards: MutableList<Card> = mutableListOf()) {

    fun addCards(cardsToAdd: List<Card>) {
        cards.addAll(cardsToAdd)
    }

    fun totalCards() = cards.size

    open fun getAllCards() = cards.toList()

}