import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Solitaire {
    public static final int TABLE_HEIGHT = Card.CARD_HEIGHT * 4;
    public static final int TABLE_WIDTH = (Card.CARD_WIDTH * 7) + 100;
    public static final int NUM_FINAL_DECKS = 4;
    public static final int NUM_PLAY_DECKS = 7;
    public static final Point DECK_POS = new Point(5, 5);
    public static final Point SHOW_POS = new Point(DECK_POS.x + Card.CARD_WIDTH + 5, DECK_POS.y);
    public static final Point FINAL_POS = new Point(SHOW_POS.x + Card.CARD_WIDTH + 150, DECK_POS.y);
    public static final Point PLAY_POS = new Point(DECK_POS.x, FINAL_POS.y + Card.CARD_HEIGHT + 30);

    public static FinalDeck[] final_cards;
    public static CardDeck[] playCardStack;
    public static final Card newCardPlace = new Card();
    public static CardDeck deck;
    public static final JFrame frame = new JFrame("Solitaire");
    protected static final JPanel table = new JPanel();
    private static JButton newGameButton = new JButton("New Game");
    private static JButton toggleTimerButton = new JButton("Pause Timer");
    private static JTextField scoreBox = new JTextField();
    private static JTextField timeBox = new JTextField();
    public static JTextField statusBox = new JTextField();
    public static final Card newCardButton = new Card();

    private static java.util.Timer timer = new Timer();
    private static ScoreClock scoreClock = new ScoreClock();

    private static boolean timeRunning = false;
    private static int score = 0;
    private static int time = 0;
    /**
     * Moves a card to an absolute location within a component.
     *
     * @param c the card to be moved
     * @param x the x-coordinate for the card
     * @param y the y-coordinate for the card
     * @return the card that was moved
     */
    protected static Card moveCard(Card c, int x, int y) {
        c.setBounds(new Rectangle(new Point(x, y), new Dimension(Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + 10)));
        c.setXY(new Point(x, y));
        return c;
    }
    /**
     * Updates the score based on gameplay actions.
     *
     * @param score1 the change in score to be applied
     */
    protected static void setScore(int score1) {
        Solitaire.score += score1;
        String newScore = "Score: " + Solitaire.score;
        scoreBox.setText(newScore);
        scoreBox.repaint();
    }
    /**
     * Updates the timer by incrementing the time by one second.
     * Every 10 seconds, the score is decreased by 2 points.
     */
    protected static void updateTimer() {
        Solitaire.time += 1;
        if (Solitaire.time % 10 == 0) {
            setScore(-2);
        }
        String time = "Seconds: " + Solitaire.time;
        timeBox.setText(time);
        timeBox.repaint();
    }
    /**
     * Starts the game timer.
     * The timer updates every second.
     */

    protected static void startTimer() {
        scoreClock = new ScoreClock();
        timer.scheduleAtFixedRate(scoreClock, 1000, 1000);
        timeRunning = true;
    }
    /**
     * Toggles the timer between running and paused states.
     */
    protected static void toggleTimer() {
        if (timeRunning && scoreClock != null) {
            scoreClock.cancel();
            timeRunning = false;
        } else {
            startTimer();
        }
    }
    /**
     * Inner class representing the game timer.
     * Updates the timer every second.
     */
    private static class ScoreClock extends TimerTask {
        @Override
        public void run() {
            updateTimer();
        }
    }
    /**
     * ActionListener for the New Game button.
     * Starts a new game when the button is pressed.
     */
    private static class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playNewGame();
        }

    }
    /**
     * ActionListener for the Toggle Timer button.
     * Toggles the timer when the button is pressed.
     */
    private static class ToggleTimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            toggleTimer();
            if (!timeRunning) {
                toggleTimerButton.setText("Start Timer");
            } else {
                toggleTimerButton.setText("Pause Timer");
            }
        }
    }
    /**
     * Initializes and starts a new game.
     * Sets up the deck, shuffles it, and deals cards to the tableau and foundation stacks.
     */
    public static void playNewGame() {
        deck = new CardDeck(true);
        deck.shuffle();
        table.removeAll();
        if (playCardStack != null && final_cards != null) {
            for (int x = 0; x < NUM_PLAY_DECKS; x++) {
                playCardStack[x].makeEmpty();
            }
            for (int x = 0; x < NUM_FINAL_DECKS; x++) {
                final_cards[x].makeEmpty();
            }
        }
        final_cards = new FinalDeck[NUM_FINAL_DECKS];
        for (int x = 0; x < NUM_FINAL_DECKS; x++) {
            final_cards[x] = new FinalDeck();

            final_cards[x].setXY((FINAL_POS.x + (x * Card.CARD_WIDTH)) + 10, FINAL_POS.y);
            table.add(final_cards[x]);

        }
        table.add(moveCard(newCardButton, DECK_POS.x, DECK_POS.y));
        playCardStack = new CardDeck[NUM_PLAY_DECKS];
        for (int x = 0; x < NUM_PLAY_DECKS; x++) {
            playCardStack[x] = new CardDeck(false);
            playCardStack[x].setXY((DECK_POS.x + (x * (Card.CARD_WIDTH + 10))), PLAY_POS.y);

            table.add(playCardStack[x]);
        }
        for (int x = 0; x < NUM_PLAY_DECKS; x++) {
            int hld = 0;
            Card c = deck.pop().setFaceup();
            playCardStack[x].putFirst(c);

            for (int y = x + 1; y < NUM_PLAY_DECKS; y++) {
                playCardStack[y].putFirst(c = deck.pop());
            }
        }
        time = 0;
        newGameButton.addActionListener(new NewGameListener());
        newGameButton.setBounds(0, TABLE_HEIGHT - 70, 120, 30);
        scoreBox.setBounds(120, TABLE_HEIGHT - 70, 120, 30);
        scoreBox.setText("Score: 0");
        scoreBox.setEditable(false);
        scoreBox.setOpaque(false);
        timeBox.setBounds(240, TABLE_HEIGHT - 70, 120, 30);
        timeBox.setText("Seconds: 0");
        timeBox.setEditable(false);
        timeBox.setOpaque(false);
        frame.setIconImage(new ImageIcon("solitaire-nobg.png").getImage());
        startTimer();
        toggleTimerButton.setBounds(360, TABLE_HEIGHT - 70, 125, 30);
        toggleTimerButton.addActionListener(new ToggleTimerListener());
        statusBox.setBounds(485, TABLE_HEIGHT - 70, 180, 30);
        statusBox.setEditable(false);
        statusBox.setOpaque(false);
        table.add(statusBox);
        table.add(toggleTimerButton);
        table.add(timeBox);
        table.add(newGameButton);
        table.add(scoreBox);
        table.repaint();
    }
}
