package indigo

object CardHelper {

    fun cardsHaveSameSuit(cards: List<Card>) = cardCheck(cards, ::sameSuitPredicate)

    fun cardsHaveSameRank(cards: List<Card>) = cardCheck(cards, ::sameRankPredicate)

    fun getSameSuitCardIndex(cards: List<Card>) = cardCheckIndex(cards, ::sameSuitPredicate)

    fun getSameRankCardIndex(cards: List<Card>) = cardCheckIndex(cards, ::sameRankPredicate)

    fun getSingleCandidateIndex(cards: List<Card>, candidateCard: Card): Int {
        return searchCardIndex(cards, candidateCard, ::sameRankOrSuitPredicate)
    }

    fun getSingleRankCandidateIndex(cards: List<Card>, candidateCard: Card): Int {
        return searchCardIndex(cards, candidateCard, ::sameRankPredicate)
    }

    fun getSingleSuitCandidateIndex(cards: List<Card>, candidateCard: Card): Int {
        return searchCardIndex(cards, candidateCard, ::sameSuitPredicate)
    }

    fun getSameRankCandidates(candidatesCards: List<Card>, tableTopCard: Card): List<Card> {
        return searchCandidates(candidatesCards, tableTopCard, ::sameRankPredicate)
    }

    fun getSameSuitCandidates(candidatesCards: List<Card>, tableTopCard: Card): List<Card> {
        return searchCandidates(candidatesCards, tableTopCard, ::sameSuitPredicate)
    }

    fun getCandidatesCards(cards: List<Card>, candidate: Card): List<Card> {
        return searchCandidates(cards, candidate, ::sameRankOrSuitPredicate)
    }

    private fun searchCandidates(cards: List<Card>, candidate: Card, predicate: (Card, Card) -> Boolean): List<Card> {
        val candidates = mutableListOf<Card>()
        for (card in cards) {
            if (predicate(card, candidate)) candidates.add(card)
        }
        return candidates
    }

    private fun searchCardIndex(cards: List<Card>, candidateCard: Card, predicate: (Card, Card) -> Boolean): Int {
        for (index in cards.indices) {
            if(predicate(cards[index], candidateCard)) return index
        }
        return -1
    }

    private fun cardCheckIndex(cards: List<Card>, predicate: (Card, Card) -> Boolean): Int {
        var index = -1
        val cardRange = cards.indices
        cardTop@ for (cardTop in cardRange) {
            for (cardBottom in cardRange) {
                if (cardTop == cardBottom) continue
                if (predicate(cards[cardTop],cards[cardBottom])) {
                    index = cardTop
                    break@cardTop
                }
            }
        }
        return index
    }

    private fun cardCheck(cards: List<Card>, predicate: (Card, Card) -> Boolean): Boolean {
        for (cardTop in cards) {
            for (cardBottom in cards) {
                if (cardTop == cardBottom) continue
                if (predicate(cardTop, cardBottom)) {
                    return true
                }
            }
        }
        return false
    }

    private fun sameSuitPredicate(card1: Card, card2: Card) = card1.equalsSuit(card2)

    private fun sameRankPredicate(card1: Card, card2: Card) = card1.equalsRank(card2)

    private fun sameRankOrSuitPredicate(card1: Card, card2: Card) = card1.equalsRankOrSuit(card2)

}