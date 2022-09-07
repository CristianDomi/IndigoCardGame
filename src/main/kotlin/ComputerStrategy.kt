package indigo

object ComputerStrategy {

    fun onlyOneCardInHand(cards: List<Card>) = cardListHasOne(cards)

    fun onlyOneCandidateCard(cards: List<Card>) =  cardListHasOne(cards)

    fun noCardsInTable(tableCards: List<Card>) = cardListIsEmpty(tableCards)

    fun noCandidateCards(cards: List<Card>) = cardListIsEmpty(cards)

    private fun cardListHasOne(cards: List<Card>) = cards.size == 1

    private fun cardListIsEmpty(cards: List<Card>) = cards.isEmpty()

}