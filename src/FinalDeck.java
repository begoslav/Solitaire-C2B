import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FinalDeck extends CardDeck {
    public FinalDeck() {
        super(false);
    }

    @Override
    public void setXY(int x, int y) {
        _x = x;
        _y = y;
        setBounds(x, y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + 10);
    }
    /**
     * Checks if the given point is within the bounds of the card stack.
     *
     * @param p the point to be checked
     * @return true if the point is within the bounds, false otherwise
     */
    @Override
    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(_x, _y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + 10);
        return (rect.contains(p));
    }
    /**
     * Custom paint component method to render the card stack.
     * If the stack is not empty, it adds the last card to the component.
     * If the stack is empty, it draws a rounded rectangle representing the empty stack.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        removeAll();
        if (!empty()) {
            add(Solitaire.moveCard(this.getLast(), 1, 1));
        } else {
            Graphics2D g2d = (Graphics2D) g;
            RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT,
                    Card.CORNER_ANGLE, Card.CORNER_ANGLE);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fill(rect);
            g2d.setColor(Color.black);
            g2d.draw(rect);
        }

    }
}
