import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardMovement extends MouseAdapter {
    private Card prevCard = null;
    private Card movedCard = null;
    private boolean sourceIsFinalDeck = false;
    private boolean putBackOnDeck = true;
    private boolean checkForWin = false;
    private boolean gameOver = true;
    private Point start = null;
    private Point stop = null;
    private Card card = null;

    private CardDeck source = null;
    private CardDeck deckk = null;
    private final CardDeck transferStack = new CardDeck(false);
    /**
     * Determines if a move from the source card to the destination card is valid on the play stack.
     *
     * @param source the source card
     * @param dest the destination card
     * @return true if the move is valid, false otherwise
     */
    private boolean validPlayStackMove(Card source, Card dest) {
        int s_val = source.getValue().ordinal();
        int d_val = dest.getValue().ordinal();
        Type s_suit = source.getSuit();
        Type d_suit = dest.getSuit();
        if ((s_val + 1) == d_val) {
            switch (s_suit) {
                case SPADES:
                    if (d_suit != Type.HEARTS && d_suit != Type.DIAMONDS)
                        return false;
                    else
                        return true;
                case CLUBS:
                    if (d_suit != Type.HEARTS && d_suit != Type.DIAMONDS)
                        return false;
                    else
                        return true;
                case HEARTS:
                    if (d_suit != Type.SPADES && d_suit != Type.CLUBS)
                        return false;
                    else
                        return true;
                case DIAMONDS:
                    if (d_suit != Type.SPADES && d_suit != Type.CLUBS)
                        return false;
                    else
                        return true;
            }
            return false;
        } else
            return false;
    }
    /**
     * Determines if a move from the source card to the destination card is valid on the final stack.
     *
     * @param source the source card
     * @param dest the destination card
     * @return true if the move is valid, false otherwise
     */
    private boolean validFinalStackMove(Card source, Card dest) {
        int s_val = source.getValue().ordinal();
        int d_val = dest.getValue().ordinal();
        Type s_suit = source.getSuit();
        Type d_suit = dest.getSuit();
        if (s_val == (d_val + 1))
        {
            if (s_suit == d_suit)
                return true;
            else
                return false;
        } else
            return false;
    }
    /**
     * Handles the mouse pressed event.
     *
     * @param e the mouse event
     */
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        boolean stopSearch = false;
        Solitaire.statusBox.setText("");
        transferStack.makeEmpty();

        for (int x = 0; x < Solitaire.NUM_PLAY_DECKS; x++) {
            if (stopSearch)
                break;
            source = Solitaire.playCardStack[x];
            for (Component ca : source.getComponents()) {
                Card c = (Card) ca;
                if (c.getFaceStatus() && source.contains(start)) {
                    transferStack.putFirst(c);
                }
                if (c.contains(start) && source.contains(start) && c.getFaceStatus()) {
                    card = c;
                    stopSearch = true;
                    System.out.println("Transfer Size: " + transferStack.showSize());
                    break;
                }
            }

        }
        if (Solitaire.newCardButton.contains(start) && Solitaire.deck.showSize() > 0) {
            if (putBackOnDeck && prevCard != null) {
                System.out.println("Putting back on show stack: ");
                prevCard.getValue();
                prevCard.getSuit();
                Solitaire.deck.putFirst(prevCard);
            }

            System.out.print("poping deck ");
            Solitaire.deck.showSize();
            if (prevCard != null)
                Solitaire.table.remove(prevCard);
            Card c = Solitaire.deck.pop().setFaceup();
            Solitaire.table.add(Solitaire.moveCard(c, Solitaire.SHOW_POS.x, Solitaire.SHOW_POS.y));
            c.repaint();
            Solitaire.table.repaint();
            prevCard = c;
        }


        if (Solitaire.newCardPlace.contains(start) && prevCard != null) {
            movedCard = prevCard;
        }
        for (int x = 0; x < Solitaire.NUM_FINAL_DECKS; x++) {

            if (Solitaire.final_cards[x].contains(start)) {
                source = Solitaire.final_cards[x];
                card = source.getLast();
                transferStack.putFirst(card);
                sourceIsFinalDeck = true;
                break;
            }
        }
        putBackOnDeck = true;

    }

    public void mouseReleased(MouseEvent e) {
        stop = e.getPoint();
        boolean validMoveMade = false;
        if (movedCard != null) {
            for (int x = 0; x < Solitaire.NUM_PLAY_DECKS; x++) {
                deckk = Solitaire.playCardStack[x];
                if (deckk.empty() && movedCard != null && deckk.contains(stop)
                        && movedCard.getValue() == Value.KING) {
                    System.out.print("moving new card to empty spot ");
                    movedCard.setXY(deckk.getXY());
                    Solitaire.table.remove(prevCard);
                    deckk.putFirst(movedCard);
                    Solitaire.table.repaint();
                    movedCard = null;
                    putBackOnDeck = false;
                    Solitaire.setScore(5);
                    validMoveMade = true;
                    break;
                }
                if (movedCard != null && deckk.contains(stop) && !deckk.empty() && deckk.getFirst().getFaceStatus()
                        && validPlayStackMove(movedCard, deckk.getFirst())) {
                    System.out.print("moving new card ");
                    movedCard.setXY(deckk.getFirst().getXY());
                    Solitaire.table.remove(prevCard);
                    deckk.putFirst(movedCard);
                    Solitaire.table.repaint();
                    movedCard = null;
                    putBackOnDeck = false;
                    Solitaire.setScore(5);
                    validMoveMade = true;
                    break;
                }
            }
            for (int x = 0; x < Solitaire.NUM_FINAL_DECKS; x++) {
                deckk = Solitaire.final_cards[x];
                if (deckk.empty() && deckk.contains(stop)) {
                    if (movedCard.getValue() == Value.ACE) {
                        deckk.push(movedCard);
                        Solitaire.table.remove(prevCard);
                        deckk.repaint();
                        Solitaire.table.repaint();
                        movedCard = null;
                        putBackOnDeck = false;
                        Solitaire.setScore(10);
                        validMoveMade = true;
                        break;
                    }
                }
                if (!deckk.empty() && deckk.contains(stop) && validFinalStackMove(movedCard, deckk.getLast())) {
                    System.out.println("Destin" + deckk.showSize());
                    deckk.push(movedCard);
                    Solitaire.table.remove(prevCard);
                    deckk.repaint();
                    Solitaire.table.repaint();
                    movedCard = null;
                    putBackOnDeck = false;
                    checkForWin = true;
                    Solitaire.setScore(10);
                    validMoveMade = true;
                    break;
                }
            }
        if (card != null && source != null) {
            for (int x = 0; x < Solitaire.NUM_PLAY_DECKS; x++) {
                deckk = Solitaire.playCardStack[x];
                if (card.getFaceStatus() == true && deckk.contains(stop) && source != deckk && !deckk.empty()
                        && validPlayStackMove(card, deckk.getFirst()) && transferStack.showSize() == 1) {
                    Card c = null;
                    if (sourceIsFinalDeck)
                        c = source.pop();
                    else
                        c = source.popFirst();

                    c.repaint();
                    if (source.getFirst() != null) {
                        Card temp = source.getFirst().setFaceup();
                        temp.repaint();
                        source.repaint();
                    }

                    deckk.setXY(deckk.getXY().x, deckk.getXY().y);
                    deckk.putFirst(c);

                    deckk.repaint();

                    Solitaire.table.repaint();

                    System.out.print("Destination ");
                    deckk.showSize();
                    if (sourceIsFinalDeck)
                        Solitaire.setScore(15);
                    else
                        Solitaire.setScore(10);
                    validMoveMade = true;
                    break;
                } else if (deckk.empty() && card.getValue() == Value.KING && transferStack.showSize() == 1) {
                    Card c = null;
                    if (sourceIsFinalDeck)
                        c = source.pop();
                    else
                        c = source.popFirst();

                    c.repaint();
                    if (source.getFirst() != null) {
                        Card temp = source.getFirst().setFaceup();
                        temp.repaint();
                        source.repaint();
                    }

                    deckk.setXY(deckk.getXY().x, deckk.getXY().y);
                    deckk.putFirst(c);

                    deckk.repaint();

                    Solitaire.table.repaint();

                    System.out.print("Destination ");
                    deckk.showSize();
                    Solitaire.setScore(5);
                    validMoveMade = true;
                    break;
                }
                if (deckk.empty() && deckk.contains(stop) && !transferStack.empty()
                        && transferStack.getFirst().getValue() == Value.KING) {
                    System.out.println("King To Empty Stack Transfer");
                    while (!transferStack.empty()) {
                        System.out.println("popping from transfer: " + transferStack.getFirst().getValue());
                        deckk.putFirst(transferStack.popFirst());
                        source.popFirst();
                    }
                    if (source.getFirst() != null) {
                        Card temp = source.getFirst().setFaceup();
                        temp.repaint();
                        source.repaint();
                    }

                    deckk.setXY(deckk.getXY().x, deckk.getXY().y);
                    deckk.repaint();

                    Solitaire.table.repaint();
                    Solitaire.setScore(5);
                    validMoveMade = true;
                    break;
                }
                if (deckk.contains(stop) && !transferStack.empty() && source.contains(start)
                        && validPlayStackMove(transferStack.getFirst(), deckk.getFirst())) {
                    System.out.println("Regular Stack Transfer");
                    while (!transferStack.empty()) {
                        System.out.println("popping from transfer: " + transferStack.getFirst().getValue());
                        deckk.putFirst(transferStack.popFirst());
                        source.popFirst();
                    }
                    if (source.getFirst() != null) {
                        Card temp = source.getFirst().setFaceup();
                        temp.repaint();
                        source.repaint();
                    }

                    deckk.setXY(deckk.getXY().x, deckk.getXY().y);
                    deckk.repaint();

                    Solitaire.table.repaint();
                    Solitaire.setScore(5);
                    validMoveMade = true;
                    break;
                }
            }
            for (int x = 0; x < Solitaire.NUM_FINAL_DECKS; x++) {
                deckk = Solitaire.final_cards[x];

                if (card.getFaceStatus() == true && source != null && deckk.contains(stop) && source != deckk) {
                    if (deckk.empty())
                    {
                        if (card.getValue() == Value.ACE) {
                            Card c = source.popFirst();
                            c.repaint();
                            if (source.getFirst() != null) {

                                Card temp = source.getFirst().setFaceup();
                                temp.repaint();
                                source.repaint();
                            }

                            deckk.setXY(deckk.getXY().x, deckk.getXY().y);
                            deckk.push(c);

                            deckk.repaint();

                            Solitaire.table.repaint();

                            System.out.print("Destination ");
                            deckk.showSize();
                            card = null;
                            Solitaire.setScore(10);
                            validMoveMade = true;
                            break;
                        }
                    } else if (validFinalStackMove(card, deckk.getLast())) {
                        Card c = source.popFirst();
                        c.repaint();
                        if (source.getFirst() != null) {

                            Card temp = source.getFirst().setFaceup();
                            temp.repaint();
                            source.repaint();
                        }

                        deckk.setXY(deckk.getXY().x, deckk.getXY().y);
                        deckk.push(c);

                        deckk.repaint();

                        Solitaire.table.repaint();

                        System.out.print("Destination ");
                        deckk.showSize();
                        card = null;
                        checkForWin = true;
                        Solitaire.setScore(10);
                        validMoveMade = true;
                        break;
                    }
                }

            }
        }
        if (!validMoveMade && deckk != null && card != null) {
            Solitaire.statusBox.setText("That Is Not A Valid Move");
        }
        if (checkForWin) {
            boolean gameNotOver = false;
            for (int x = 0; x < Solitaire.NUM_FINAL_DECKS; x++) {
                deckk = Solitaire.final_cards[x];
                if (deckk.showSize() != 13) {
                    gameNotOver = true;
                    break;
                }
            }
            if (!gameNotOver)
                gameOver = true;
        }

        if (checkForWin && gameOver) {
            JOptionPane.showMessageDialog(Solitaire.table, "Congratulations! You've Won!");
            Solitaire.statusBox.setText("Game Over!");
        }
        start = null;
        stop = null;
        source = null;
        deckk = null;
        card = null;
        sourceIsFinalDeck = false;
        checkForWin = false;
        gameOver = false;
    }
}
}
