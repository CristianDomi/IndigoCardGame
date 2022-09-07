package indigo

object Score {

    private val highCards = listOf(Rank.ACE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING)

    fun totalScore(cards: List<Card>): Int {
        var total = 0
        for (card in cards) {
            if (card.rank in highCards) total++
        }
        return total
    }



}