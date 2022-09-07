package indigo

object Printer {

    fun printTableCards(table: Table) {
        val totalTableCards = table.totalCards()
        if (totalTableCards == 0) {
            println("No cards on the table")
        } else {
            println("$totalTableCards cards on the table, and the top card is ${table.getTopCard()}")
        }
    }

    fun printCards(cards: List<Card>) {
        for (card in cards) {
            print("$card ")
        }
        println()
    }

    fun printCardsInHand(cards: List<Card>) {
        print("Cards in hand: ")
        for (index in cards.indices) {
            print("${index+1})${cards[index]} ")
        }
        println()
    }

    fun printSummary(player: Player, ai: Player) {
        println("Score: Player ${player.points()} - Computer ${ai.points()}")
        println("Cards: Player ${player.totalWinedCards()} - Computer ${ai.totalWinedCards()}")
    }

}