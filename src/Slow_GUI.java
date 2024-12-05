import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Slow_GUI extends JFrame {
    private JTextField textField;
    private JButton startButton;
    private JButton cancelButton;
    private JTextArea outputArea;
    private JLabel timeLabel;
    private volatile boolean isCancelled = false; // Flag for cancellation
    private ExecutorService executor;
    private ConcurrentSkipListSet<Integer> primes = new ConcurrentSkipListSet<>();

    public Slow_GUI() {
        // Set up the JFrame
        setTitle("Prime Numbers Finder");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a JPanel for layout management
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create components
        textField = new JTextField("Enter a number...");
        startButton = new JButton("Find Primes");
        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false); // Disable cancel button initially
        outputArea = new JTextArea();
        outputArea.setEditable(false); // Make output area read-only
        JScrollPane scrollPane = new JScrollPane(outputArea);
        timeLabel = new JLabel("Time Elapsed: 0 ms");

        // Add action listener to the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int max = Integer.parseInt(textField.getText().trim());
                    if (max < 0) {
                        JOptionPane.showMessageDialog(Slow_GUI.this, "Please enter a positive integer.");
                        return;
                    }
                    startButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    isCancelled = false;

                    // Start the prime number search
                    startPrimeSearch(max);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Slow_GUI.this, "Please enter a valid integer.");
                }
            }
        });

        // Add action listener to the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCancelled = true; // Set the cancellation flag
                if (executor != null) {
                    executor.shutdownNow(); // Cancel all running tasks
                }
                startButton.setEnabled(true);
                cancelButton.setEnabled(false);
            }
        });

        // Add components to the panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(textField, BorderLayout.CENTER);
        topPanel.add(timeLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the panel to the JFrame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Starts the prime number search using multiple threads.
     */
    private void startPrimeSearch(int max) {
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> tasks = new ArrayList<>();
        primes.clear(); // Clear previous primes
        outputArea.setText("");
        timeLabel.setText("Time Elapsed: 0 ms");
        long startTime = System.currentTimeMillis();

        int numThreads = Runtime.getRuntime().availableProcessors();
        int range = max / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * range + 1;
            int end = (i == numThreads - 1) ? max : start + range - 1;

            tasks.add(executor.submit(() -> {
                for (int num = start; num <= end; num++) {
                    if (isCancelled) {
                        break;
                    }
                    if (isPrime(num)) {
                        primes.add(num); // Add to the thread-safe set
                    }
                }
            }));
        }

        // Periodically update the output area with the count of primes
        Timer timer = new Timer(1000, e -> {
            if (isCancelled || executor.isShutdown()) {
                ((Timer) e.getSource()).stop(); // Stop updates when cancelled or completed
            } else {
                SwingUtilities.invokeLater(() -> {
                    outputArea.setText("Primes found so far: " + primes.size());
                });
            }
        });
        timer.start();

        // Wait for all threads to finish
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (Future<?> task : tasks) {
                    task.get();
                }
                return null;
            }

            @Override
            protected void done() {
                long endTime = System.currentTimeMillis();
                timeLabel.setText("Time Elapsed: " + (endTime - startTime) + " ms");
                if (isCancelled) {
                    outputArea.append("\nSearch cancelled. Partial results displayed.\n");
                } else {
                    SwingUtilities.invokeLater(() -> {
                        outputArea.setText("Total Primes Found: " + primes.size() + "\n");
                        outputArea.append("Prime Numbers:\n");
                        outputArea.append(String.join(", ", primes.stream().map(String::valueOf).toList()));
                        outputArea.append("\nSearch completed.\n");
                    });
                }
                startButton.setEnabled(true);
                cancelButton.setEnabled(false);
                executor.shutdown();
            }
        }.execute();
    }

    /**
     * Helper method to check if a number is prime.
     */
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Slow_GUI());
    }
}
