package mysiexperience;

/**
 *
 * @author z004kptc
 */
import javax.swing.*;

public class CountdownTimer extends JFrame {
    private JLabel timerLabel;

    public CountdownTimer() {
        setTitle("Countdown Timer");
        setSize(200, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        timerLabel = new JLabel("3");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timerLabel);

        // Start the countdown timer
        startCountdown();
    }

    private void startCountdown() {
        // Create a new thread to handle the countdown
        Thread countdownThread = new Thread(() -> {
            try {
                // Start from 3 and decrement the value every second
                for (final int[] i = {3}; i[0] > 0; i[0]--) {
                    // Update the timer label with the current countdown value
                    SwingUtilities.invokeLater(() -> timerLabel.setText(Integer.toString(i[0])));

                    // Pause execution for 1 second
                    Thread.sleep(1000);
                }

                // After the countdown finishes, close the window
                dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start the countdown thread
        countdownThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CountdownTimer timer = new CountdownTimer();
            timer.setVisible(true);
        });
    }
}