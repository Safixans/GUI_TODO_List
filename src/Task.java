public class Task {
    private int id;
    private String name;
    private boolean status;

    // Constructor for creating a new task (without id, as it's auto-generated in the database)
    public Task(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    // Constructor with id, useful for updating and deleting tasks
    public Task(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // Default constructor

    public Task() {
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

