import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class CardDeck extends JComponent {
    protected final int numCards = 52;
    protected ArrayList<Card> a;
    protected boolean playStack = false;
    protected int SPREAD = 18;
    protected int _x = 0;
    protected int _y = 0;

    public CardDeck(boolean isDeck) {
        int f = 1;
        this.setLayout(null);
        a = new ArrayList<>();
        if (isDeck) {
            for (Type suit : Type.values()) {
                for (Value value : Value.values()) {
                    a.add(new Card(suit, value));
                }
            }
        } else {
            playStack = true;
        }
    }
    /**
     * Checks if the deck is empty.
     *
     * @return true if the deck is empty, false otherwise
     */
    public boolean empty() {
        if (a.isEmpty())
            return true;
        else
            return false;
    }
    /**
     * Adds a card to the top of the deck.
     *
     * @param c the card to add
     */
    public void putFirst(Card c) {
        a.add(0, c);
    }
    /**
     * Returns the first card in the deck without removing it.
     *
     * @return the first card, or null if the deck is empty
     */
    public Card getFirst() {
        if (!this.empty()) {
            return a.get(0);
        } else
            return null;
    }
    /**
     * Returns the last card in the deck without removing it.
     *
     * @return the last card, or null if the deck is empty
     */
    public Card getLast() {
        if (!this.empty()) {
            return a.get(a.size() - 1);
        } else
            return null;
    }
    /**
     * Removes and returns the first card in the deck.
     *
     * @return the first card, or null if the deck is empty
     */
    public Card popFirst() {
        if (!this.empty()) {
            Card c = this.getFirst();
            a.remove(0);
            return c;
        } else
            return null;

    }

    public void push(Card c) {

        a.add(c);
    }
    public Card pop() {
        if (!this.empty()) {
            Card c = a.get(a.size() - 1);
            a.remove(a.size() - 1);
            return c;
        } else
            return null;
    }
    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle() {
        ArrayList<Card> v = new ArrayList<Card>();
        while (!this.empty()) {
            v.add(this.pop());
        }
        while (!v.isEmpty()) {
            Card c = v.get((int) (Math.random() * v.size()));
            this.push(c);
            v.remove(c);
        }

    }
    /**
     * Returns the number of cards in the deck.
     *
     * @return the number of cards in the deck
     */
    public int showSize() {
        System.out.println("Deck Size: " + a.size());
        return a.size();
    }


    public void makeEmpty() {
        while (!this.empty()) {
            this.popFirst();
        }
    }
    /**
     * Checks if a point is within the bounds of the deck.
     *
     * @param p the point to check
     * @return true if the point is within the bounds, false otherwise
     */
    @Override
    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(_x, _y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT * 3);
        return (rect.contains(p));
    }

    public void setXY(int x, int y) {
        _x = x;
        _y = y;
        setBounds(_x, _y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT * 3);
    }

    public Point getXY() {
        return new Point(_x, _y);
    }
    /**
     * Paints the deck component.
     *
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (playStack) {
            removeAll();
            ListIterator<Card> iter = a.listIterator();
            Point prev = new Point();
            Point prevWhereAmI = new Point();
            if (iter.hasNext()) {
                Card c = iter.next();
                prev = new Point();
                add(Solitaire.moveCard(c, prev.x, prev.y));
                c.setWhereAmI(getXY());
                prevWhereAmI = getXY();
            } else {
                removeAll();
            }

            for (; iter.hasNext(); ) {
                Card c = iter.next();
                c.setXY(new Point(prev.x, prev.y + SPREAD));
                add(Solitaire.moveCard(c, prev.x, prev.y + SPREAD));
                prev = c.getXY();
                c.setWhereAmI(new Point(prevWhereAmI.x, prevWhereAmI.y + SPREAD));
                prevWhereAmI = c.getWhereAmI();
            }
        }
    }
}
