import javax.swing.SwingUtilities;

/**
 * Main driver class to launch the Task Manager GUI application.
 * 
 * This separates the entry point from the GUI implementation,
 * making the project structure cleaner and easier to maintain.
 */
public class Main {
    public static void main(String[] args) {
        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            TaskManagerGUI gui = new TaskManagerGUI();
            gui.setVisible(true);
        });
    }
}