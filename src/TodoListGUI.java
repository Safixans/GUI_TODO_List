import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TodoListGUI extends JFrame {
    private TaskDAO taskDAO;
    private JTable table;
    private JTextField taskNameField;
    private JCheckBox taskStatusField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public TodoListGUI() {
        try {
            taskDAO = new TaskDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("To-Do List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table
        table = new JTable();
        refreshTasksView();

        // Controls panel
        JPanel controlsPanel = new JPanel();
        taskNameField = new JTextField(20);
        taskStatusField = new JCheckBox("Completed");
        addButton = new JButton("Add Task");
        updateButton = new JButton("Update Task");
        deleteButton = new JButton("Delete Task");

        controlsPanel.add(taskNameField);
        controlsPanel.add(taskStatusField);
        controlsPanel.add(addButton);
        controlsPanel.add(updateButton);
        controlsPanel.add(deleteButton);

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTask();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });
    }


  /*  private void refreshTasksView() {
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            TaskTableModel model = new TaskTableModel(tasks);
            table.setModel(model);

            // Set the cell renderer for each column
            CenterTableCellRenderer centerRenderer = new CenterTableCellRenderer();
            for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
                table.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
            }

            // Existing code for setting up checkbox renderer...
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/



    private void refreshTasksView() {
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            TaskTableModel model = new TaskTableModel(tasks);
            table.setModel(model);

            CenterTableCellRenderer centerRenderer = new CenterTableCellRenderer();
            for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
                table.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTask() {
        String name = taskNameField.getText();
        boolean status = taskStatusField.isSelected();

        try {
            taskDAO.addTask(new Task(name, status));
            refreshTasksView();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
            String name = taskNameField.getText();
            boolean status = taskStatusField.isSelected();

            try {
                taskDAO.updateTask(new Task(id, name, status));
                refreshTasksView();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

            try {
                taskDAO.deleteTask(id);
                refreshTasksView();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TodoListGUI().setVisible(true);
        });
    }
}
