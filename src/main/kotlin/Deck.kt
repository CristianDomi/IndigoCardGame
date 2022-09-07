package indigo

object Deck {

    const val MAX_VALUE = 52
    private val deck = mutableListOf<Card>()

    init {
        setDeckOfCards()
    }

    fun getCards(number: Int): MutableList<Card> {
        val takenDeck = deck.take(number)
        deck.removeAll(takenDeck)
        return takenDeck.toMutableList()
    }

    fun shuffle() {
        deck.shuffle()
    }

    private fun setDeckOfCards() {
        for (suit in Suit.values()) {
            for (rank in Rank.values()) {
                deck.add(Card(rank, suit))
            }
        }
    }

}