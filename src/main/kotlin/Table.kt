package indigo

class Table : CardHolder(){

    var playedCards = 0

    fun getInitialCards(): List<Card> {
        return cards.take(4)
    }

    fun getTopCard() = cards.last()

    fun addCard(card: Card) {
        cards.add(card)
    }

    fun takeAllCards(): List<Card> {
        val cards = super.getAllCards()
        super.cards.clear()
        return cards
    }

}