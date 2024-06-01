import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

class Card extends JPanel {


    private Type type;

    private Value _value;

    private Boolean _faceup;

    private Point _location;

    private Point whereAmI;

    private int x;
    private int y;

    private final int x_offset = 10;
    private final int y_offset = 20;
    private final int new_x_offset = x_offset + (CARD_WIDTH - 30);
    final static public int CARD_HEIGHT = 150;

    final static public int CARD_WIDTH = 100;

    final static public int CORNER_ANGLE = 25;

    Card(Type suit, Value value) {
        type = suit;
        _value = value;
        _faceup = false;
        _location = new Point();
        x = 0;
        y = 0;
        _location.x = x;
        _location.y = y;
        whereAmI = new Point();
    }

    Card() {
        type = Type.CLUBS;
        _value = Value.ACE;
        _faceup = false;
        _location = new Point();
        x = 0;
        y = 0;
        _location.x = x;
        _location.y = y;
        whereAmI = new Point();
    }
    public Type getSuit() {
        switch (type) {
            case HEARTS:
                System.out.println("Hearts");
                break;
            case DIAMONDS:
                System.out.println("Diamonds");
                break;
            case SPADES:
                System.out.println("Spades");
                break;
            case CLUBS:
                System.out.println("Clubs");
                break;
        }
        return type;
    }

    public Value getValue() {
        switch (_value) {
            case ACE:
                System.out.println(" Ace");
                break;
            case TWO:
                System.out.println(" 2");
                break;
            case THREE:
                System.out.println(" 3");
                break;
            case FOUR:
                System.out.println(" 4");
                break;
            case FIVE:
                System.out.println(" 5");
                break;
            case SIX:
                System.out.println(" 6");
                break;
            case SEVEN:
                System.out.println(" 7");
                break;
            case EIGHT:
                System.out.println(" 8");
                break;
            case NINE:
                System.out.println(" 9");
                break;
            case TEN:
                System.out.println(" 10");
                break;
            case JACK:
                System.out.println(" Jack");
                break;
            case QUEEN:
                System.out.println(" Queen");
                break;
            case KING:
                System.out.println(" King");
                break;
        }
        return _value;
    }

    public void setWhereAmI(Point p) {
        whereAmI = p;
    }

    public Point getWhereAmI() {
        return whereAmI;
    }

    public Point getXY() {
        return new Point(x, y);
    }

    public Boolean getFaceStatus() {
        return _faceup;
    }

    public void setXY(Point p) {
        x = p.x;
        y = p.y;

    }

    public void setSuit(Type suit) {
        type = suit;
    }

    public void setValue(Value value) {
        _value = value;
    }

    public Card setFaceup() {
        _faceup = true;
        return this;
    }

    public Card setFacedown() {
        _faceup = false;
        return this;
    }
    /**
     * Determines if a point is within the bounds of the card.
     *
     * @param p the point to check
     * @return true if the point is within the bounds of the card, false otherwise
     */
    @Override
    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(whereAmI.x, whereAmI.y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        return (rect.contains(p));
    }
    /**
     * Draws the type on the card.
     *
     * @param g     the Graphics2D object
     * @param suit  the suit to draw
     * @param color the color to use for the suit
     */
    private void drawSuit(Graphics2D g, String suit, Color color) {
        g.setColor(color);
        g.drawString(suit, _location.x + x_offset, _location.y + y_offset);
        g.drawString(suit, _location.x + x_offset, _location.y + CARD_HEIGHT - 5);
    }
    /**
     * Draws the value on the card.
     *
     * @param g     the Graphics2D object
     * @param value the value to draw
     */
    private void drawValue(Graphics2D g, String value) {
        g.drawString(value, _location.x + new_x_offset, _location.y + y_offset);
        g.drawString(value, _location.x + new_x_offset, _location.y + y_offset + CARD_HEIGHT - 25);
    }
    /**
     * Paints the card component.
     *
     * @param g the Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RoundRectangle2D rect2 = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
                CORNER_ANGLE, CORNER_ANGLE);
        g2d.setColor(Color.WHITE);
        g2d.fill(rect2);
        g2d.setColor(Color.black);
        g2d.draw(rect2);
        if (_faceup) {
            switch (type) {
                case HEARTS:
                    drawSuit(g2d, "Hearts", Color.RED);
                    break;
                case DIAMONDS:
                    drawSuit(g2d, "Diamonds", Color.RED);
                    break;
                case SPADES:
                    drawSuit(g2d, "Spades", Color.BLACK);
                    break;
                case CLUBS:
                    drawSuit(g2d, "Clubs", Color.BLACK);
                    break;
            }
            int new_x_offset = x_offset + (CARD_WIDTH - 30);
            switch (_value) {
                case ACE:
                    drawValue(g2d, "A");
                    break;
                case TWO:
                    drawValue(g2d, "2");
                    break;
                case THREE:
                    drawValue(g2d, "3");
                    break;
                case FOUR:
                    drawValue(g2d, "4");
                    break;
                case FIVE:
                    drawValue(g2d, "5");
                    break;
                case SIX:
                    drawValue(g2d, "6");
                    break;
                case SEVEN:
                    drawValue(g2d, "7");
                    break;
                case EIGHT:
                    drawValue(g2d, "8");
                    break;
                case NINE:
                    drawValue(g2d, "9");
                    break;
                case TEN:
                    drawValue(g2d, "10");
                    break;
                case JACK:
                    drawValue(g2d, "J");
                    break;
                case QUEEN:
                    drawValue(g2d, "Q");
                    break;
                case KING:
                    drawValue(g2d, "K");
                    break;
            }
        } else {
            RoundRectangle2D rect = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
                    CORNER_ANGLE, CORNER_ANGLE);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fill(rect);
            g2d.setColor(Color.black);
            g2d.draw(rect);
        }

    }

}