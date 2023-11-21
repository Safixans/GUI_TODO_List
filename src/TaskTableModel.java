import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private final List<Task> tasks;
    private final String[] columnNames = {"ID", "Task Name", "Status"};

    public TaskTableModel(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = tasks.get(rowIndex);
        switch (columnIndex) {
            case 0: return task.getId();
            case 1: return task.getTask_name();
            case 2: return task.isStatus();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Making the status column (checkbox) editable
        return columnIndex == 2;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            Task task = tasks.get(rowIndex);
            task.setStatus((Boolean) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);

            // Update task in the database
            // Consider doing this in a separate thread to avoid blocking the EDT
            new TaskDAO().updateTask(task);
        }
    }



}

