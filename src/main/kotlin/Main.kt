package indigo

import indigo.ComputerStrategy.noCandidateCards
import indigo.ComputerStrategy.noCardsInTable
import indigo.ComputerStrategy.onlyOneCandidateCard
import indigo.ComputerStrategy.onlyOneCardInHand
import indigo.Printer.printCards
import indigo.Printer.printCardsInHand
import indigo.Printer.printSummary
import indigo.Printer.printTableCards

private lateinit var currentPlayer: Player
private val table = Table()
private var lastWinner: Player.TYPE? = null
private var exit = false

/**
 * Cambiar SDK si no se pueden ver el tipo de cartas.
 */
fun main() {

    println("Indigo Card Game")
    Deck.shuffle()

    val player = Player(type = Player.TYPE.PLAYER)
    val ai = Player(type = Player.TYPE.AI)

    dealCards(table)
    dealCards(player)
    dealCards(ai)

    val first = askPlayFirst()

    val playerFirst = first == CORRECT_FIRST

    currentPlayer = if (playerFirst) player else ai

    print("Initial cards on the table: ")
    printCards(table.getInitialCards())

    println()
    printTableCards(table)

    table.playedCards = TABLE_DEAL

    startGame(player, ai)

    if (exit) {
        println("Game Over")
        return
    }

    when (finalPointsWinner(player.totalWinedCards(), ai.totalWinedCards(), playerFirst)) {
        Player.TYPE.PLAYER -> player.addPoints(FINAL_POINTS)
        Player.TYPE.AI -> ai.addPoints(FINAL_POINTS)
    }

    val winedCards = table.takeAllCards()
    when(lastCardsWinner(lastWinner, playerFirst)) {
        Player.TYPE.PLAYER -> addPointsToPlayer(player, winedCards)
        Player.TYPE.AI -> addPointsToPlayer(ai, winedCards)
    }

    printSummary(player, ai)
    println("Game Over")
}

fun startGame(player: Player, ai: Player) {
    game@ while(table.playedCards < Deck.MAX_VALUE) {

        val cardToPlay = getPlayerCard()

        if (cardToPlay == null) {
            exit = true
            break
        }

        if(currentPlayer.isAI()) println("Computer plays $cardToPlay")

        if (addCardToTable(cardToPlay)) {
            println("${currentPlayer.getName()} wins cards")
            val winedCards = table.takeAllCards()
            addPointsToPlayer(currentPlayer, winedCards)
            lastWinner = currentPlayer.type
            printSummary(player, ai)
        }

        if (currentPlayer.getAllCards().isEmpty()) dealCards(currentPlayer)

        currentPlayer = if (currentPlayer.type == Player.TYPE.PLAYER) ai else player

        println()
        printTableCards(table)
        table.playedCards += 1
    }
}

fun askPlayFirst(): String {
    var first: String
    do {
        println("Play first?")
        first = readln().lowercase()
    } while (correctInput(first))
    return first
}

fun correctInput(first: String) = first != CORRECT_FIRST && first != CORRECT_SECOND

fun lastCardsWinner(lastWinner: Player.TYPE?, playerFirst: Boolean): Player.TYPE {
    return when(lastWinner) {
        Player.TYPE.PLAYER -> Player.TYPE.PLAYER
        Player.TYPE.AI -> Player.TYPE.AI
        null -> if(playerFirst) Player.TYPE.PLAYER else Player.TYPE.PLAYER
    }
}

fun finalPointsWinner(playerCards: Int, computerCards: Int, playerFirst: Boolean): Player.TYPE {
    return if (playerCards > computerCards) {
        Player.TYPE.PLAYER
    } else if(playerCards < computerCards) {
        Player.TYPE.AI
    } else {
        if (playerFirst) Player.TYPE.PLAYER else Player.TYPE.AI
    }
}

fun addPointsToPlayer(player: Player, cards: List<Card>) {
    player.addWinedCards(cards)
    player.addPoints(Score.totalScore(cards))
}

fun getPlayerCard(): Card? {
    return when (currentPlayer.type) {
        Player.TYPE.PLAYER -> {
            printCardsInHand(currentPlayer.getAllCards())
            currentPlayer.getCard(askPlayerCard())
        }
        Player.TYPE.AI -> {
            printCards(currentPlayer.getAllCards())
            currentPlayer.getCard(getComputerCard())
        }
    }
}

fun getComputerCard(): Int {
    val cards = currentPlayer.getAllCards()

    if (onlyOneCardInHand(cards)) return cards.lastIndex

    if (noCardsInTable(table.getAllCards())) return getComputerCardEmptyTableNorCandidates(cards)

    val tableTopCard = table.getTopCard()
    val candidatesCards = CardHelper.getCandidatesCards(cards, tableTopCard)

    if (noCandidateCards(candidatesCards)) return getComputerCardEmptyTableNorCandidates(cards)

    if (onlyOneCandidateCard(candidatesCards)) return CardHelper.getSingleCandidateIndex(cards, tableTopCard)

    val candidatesSuit = CardHelper.getSameSuitCandidates(cards, tableTopCard)
    if (candidatesSuit.size > 1) return CardHelper.getSingleSuitCandidateIndex(cards, tableTopCard)

    val candidatesRank = CardHelper.getSameRankCandidates(cards, tableTopCard)
    if (candidatesRank.size > 1) return CardHelper.getSingleRankCandidateIndex(cards, tableTopCard)

    return CardHelper.getSingleCandidateIndex(cards, tableTopCard)
}

fun askPlayerCard(): Int? {
    var cardIndexToPlay: Int?
    val range = 1..currentPlayer.totalCards()
    do {
        println("Choose a card to play (${range.first}-${range.last}):")
        val answer = readln().lowercase()
        if (answer == "exit") {
            cardIndexToPlay = null
            break
        }
        cardIndexToPlay = answer.toIntOrNull() ?: INVALID_NUMBER
    } while (cardIndexToPlay !in range)
    return if (cardIndexToPlay != null) cardIndexToPlay - 1 else null
}

fun addCardToTable(card: Card): Boolean {
    if (table.totalCards() == 0) {
        table.addCard(card)
        return false
    }
    val playerWin = table.getTopCard().equalsRankOrSuit(card)
    table.addCard(card)
    return playerWin
}

fun dealCards(holder: CardHolder) {
    val number = when(holder) {
        is Player -> PLAYER_DEAL
        is Table -> TABLE_DEAL
        else -> INVALID_NUMBER
    }
    holder.addCards(Deck.getCards(number))
}

fun getComputerCardEmptyTableNorCandidates(cards: List<Card>): Int {
    return if (CardHelper.cardsHaveSameSuit(cards)) {
        CardHelper.getSameSuitCardIndex(cards)
    } else if (CardHelper.cardsHaveSameRank(cards)) {
        CardHelper.getSameRankCardIndex(cards)
    } else {
        (cards.indices).random()
    }
}