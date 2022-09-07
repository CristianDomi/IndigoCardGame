package indigo

class Player(val type: TYPE) : CardHolder() {

    private val winedCards = mutableListOf<Card>()
    private var points = 0

    fun getCard(index: Int?) = if(index != null) cards.removeAt(index) else null

    fun addWinedCards(cards: List<Card>) {
        winedCards.addAll(cards)
    }

    fun isAI() = type == TYPE.AI

    fun getName() = if (isAI()) "Computer" else "Player"

    fun points() = points

    fun addPoints(points: Int) {
        this.points += points
    }

    fun totalWinedCards() = winedCards.size

    enum class TYPE{
        PLAYER, AI
    }

}