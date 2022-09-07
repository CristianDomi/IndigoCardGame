package indigo

data class Card(val rank : Rank, val suit : Suit) {

    fun equalsSuit(other: Card) = suit == other.suit

    fun equalsRank(other: Card) = rank == other.rank

    fun equalsRankOrSuit(other: Card) = equalsRank(other) || equalsSuit(other)

    override fun toString(): String {
        return "${rank.rankName}${suit.suitSymbol}"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Card) {
            if (other.rank == rank && other.suit == suit) return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = rank.hashCode()
        result = 31 * result + suit.hashCode()
        return result
    }

}