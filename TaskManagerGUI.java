import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Enhanced GUI for the Task Manager with modern styling.
 * Shows tasks in a styled table with color-coded statuses.
 */
public class TaskManagerGUI extends JFrame {
    private ToDoListManager<TaskItem> manager;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    
    // Color palette
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color PANEL_COLOR = Color.WHITE;
    
    public TaskManagerGUI() {
        manager = new ToDoListManager<>();
        setupWindow();
        createComponents();
    }
    
    private void setupWindow() {
        setTitle("✓ Task Manager");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }
    
    private void createComponents() {
        setLayout(new BorderLayout(15, 15));
        
        // Add padding around edges
        ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Table
        add(createTablePanel(), BorderLayout.CENTER);
        
        // Buttons
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Task Manager Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Organize your tasks efficiently!");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(236, 240, 241));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setBackground(PRIMARY_COLOR);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        
        panel.add(textPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Table setup
        String[] columns = {"ID", "Description", "Due Date", "Priority", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        taskTable = new JTable(tableModel);
        
        // Style table
        taskTable.setRowHeight(35);
        taskTable.setFont(new Font("Arial", Font.PLAIN, 13));
        taskTable.setSelectionBackground(new Color(52, 152, 219, 100));
        taskTable.setSelectionForeground(Color.BLACK);
        taskTable.setShowVerticalLines(false);
        taskTable.setIntercellSpacing(new Dimension(0, 1));
        
        // Style header
        JTableHeader header = taskTable.getTableHeader();
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Custom cell renderer for status column with colors
        taskTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                                                                 isSelected, hasFocus, row, column);
                
                String status = value.toString();
                setHorizontalAlignment(CENTER);
                setFont(new Font("Arial", Font.BOLD, 12));
                
                if (!isSelected) {
                    if (status.equals("TODO")) {
                        c.setBackground(new Color(241, 196, 15, 50));
                        setForeground(new Color(183, 149, 11));
                    } else if (status.equals("DONE")) {
                        c.setBackground(new Color(46, 204, 113, 50));
                        setForeground(new Color(34, 153, 84));
                    } else if (status.equals("SCHEDULED")) {
                        c.setBackground(new Color(52, 152, 219, 50));
                        setForeground(new Color(41, 128, 185));
                    }
                }
                
                return c;
            }
        });
        
        // Center align priority column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        taskTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        // Set column widths
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        taskTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Basic CRUD Operations
        JPanel crudPanel = createButtonSection("Task Management", new String[][]{
            {"Add New Task", "add"},
            {"Update Selected", "update"},
            {"Delete Selected", "remove"},
            {"Mark as Complete", "complete"}
        });
        
        // View & Organization
        JPanel viewPanel = createButtonSection("View & Sort", new String[][]{
            {"Search Tasks", "search"},
            {"Sort by Description", "sortDesc"},
            {"Sort by Due Date", "sortDate"},
            {"Sort by Priority", "sortPrio"}
        });
        
        // Workflow & History
        JPanel workflowPanel = createButtonSection("Workflow & History", new String[][]{
            {"Schedule Selected", "schedule"},
            {"Process Scheduled", "process"},
            {"Undo", "undo"},
            {"Refresh", "refresh"}
        });
        
        JPanel allButtonsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        allButtonsPanel.setBackground(BACKGROUND_COLOR);
        allButtonsPanel.add(crudPanel);
        allButtonsPanel.add(viewPanel);
        allButtonsPanel.add(workflowPanel);
        
        mainPanel.add(allButtonsPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createButtonSection(String title, String[][] buttons) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(44, 62, 80));
        
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonsPanel.setBackground(PANEL_COLOR);
        
        for (String[] btn : buttons) {
            buttonsPanel.add(createStyledButton(btn[0], btn[1]));
        }
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, String action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setOpaque(true);  // Important for background color to show
        button.setBorderPainted(false);  // Remove border for cleaner look
        
        // Color based on action type - simpler scheme
        if (action.equals("add")) {
            button.setBackground(SUCCESS_COLOR);  // Green - creating
        } else if (action.equals("remove")) {
            button.setBackground(DANGER_COLOR);  // Red - deleting
        } else if (action.equals("update") || action.equals("complete")) {
            button.setBackground(WARNING_COLOR);  // Yellow/Orange - modifying
        } else {
            button.setBackground(PRIMARY_COLOR);  // Blue - everything else
        }
        
        button.setForeground(Color.WHITE);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = button.getBackground();
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        // Link actions
        button.addActionListener(e -> handleAction(action));
        
        return button;
    }
    
    private void handleAction(String action) {
        switch (action) {
            case "add": addTask(); break;
            case "update": updateTask(); break;
            case "remove": removeTask(); break;
            case "complete": completeTask(); break;
            case "sortDesc": sortByDescription(); break;
            case "sortDate": sortByDueDate(); break;
            case "sortPrio": sortByPriority(); break;
            case "search": searchTask(); break;
            case "schedule": scheduleTask(); break;
            case "process": processNext(); break;
            case "undo": undo(); break;
            case "refresh": refreshTable(); break;
        }
    }
    
    // === Button Actions (same as before) ===
    
    private void addTask() {
        JTextField idField = new JTextField();
        JTextField descField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField priorityField = new JTextField();
        
        Object[] message = {
            "ID:", idField,
            "Description:", descField,
            "Due Date (YYYY-MM-DD):", dateField,
            "Priority:", priorityField
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add Task", 
                     JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String desc = descField.getText();
                String date = dateField.getText();
                int priority = Integer.parseInt(priorityField.getText());
                
                manager.addItem(new TaskItem(id, desc, date, priority));
                refreshTable();
                showSuccessMessage("Task added successfully!");
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid priority number!");
            }
        }
    }
    
    private void updateTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) {
            showErrorMessage("Please select a task first!");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        TaskItem existing = manager.searchById(id);
        
        JTextField descField = new JTextField(existing.getDescription());
        JTextField dateField = new JTextField(existing.getDueDate());
        JTextField priorityField = new JTextField(String.valueOf(existing.getPriority()));
        
        Object[] message = {
            "Description:", descField,
            "Due Date:", dateField,
            "Priority:", priorityField
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Update Task", 
                     JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String desc = descField.getText();
                String date = dateField.getText();
                int priority = Integer.parseInt(priorityField.getText());
                
                manager.updateItem(id, new TaskItem(id, desc, date, priority));
                refreshTable();
                showSuccessMessage("Task updated successfully!");
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid priority number!");
            }
        }
    }
    
    private void removeTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) {
            showErrorMessage("Please select a task first!");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Remove this task?", 
                      "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            manager.removeItem(id);
            refreshTable();
            showSuccessMessage("Task removed successfully!");
        }
    }
    
    private void completeTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) {
            showErrorMessage("Please select a task first!");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        if (manager.completeTask(id)) {
            refreshTable();
            showSuccessMessage("Task marked as DONE!");
        }
    }
    
    private void sortByDescription() {
        manager.sortByDescription();
        refreshTable();
    }
    
    private void sortByDueDate() {
        manager.sortByDueDate();
        refreshTable();
    }
    
    private void sortByPriority() {
        manager.sortByPriority();
        refreshTable();
    }
    
    private void searchTask() {
        String keyword = JOptionPane.showInputDialog(this, "Enter search keyword:");
        if (keyword != null && !keyword.isEmpty()) {
            TaskList<TaskItem> results = manager.searchByDescription(keyword);
            if (results.size() == 0) {
                showErrorMessage("No tasks found!");
            } else {
                String message = "Found " + results.size() + " task(s):\n\n";
                for (int i = 0; i < results.size(); i++) {
                    message += "• " + results.get(i).getDescription() + "\n";
                }
                JOptionPane.showMessageDialog(this, message, "Search Results", 
                                            JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void scheduleTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) {
            showErrorMessage("Please select a task first!");
            return;
        }
        
        String id = (String) tableModel.getValueAt(row, 0);
        if (manager.scheduleTask(id)) {
            refreshTable();
            showSuccessMessage("Task scheduled!");
        }
    }
    
    private void processNext() {
        TaskItem task = manager.processNextScheduledTask();
        if (task != null) {
            refreshTable();
            showSuccessMessage("Processed: " + task.getDescription());
        } else {
            showErrorMessage("No scheduled tasks!");
        }
    }
    
    private void undo() {
        manager.undo();
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        TaskList<TaskItem> tasks = manager.getAllTasks();
        for (int i = 0; i < tasks.size(); i++) {
            TaskItem task = tasks.get(i);
            tableModel.addRow(new Object[]{
                task.getId(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
            });
        }
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", 
                                    JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManagerGUI gui = new TaskManagerGUI();
            gui.setVisible(true);
        });
    }
}