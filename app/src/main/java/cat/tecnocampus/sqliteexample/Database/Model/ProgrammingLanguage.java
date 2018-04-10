package cat.tecnocampus.sqliteexample.Database.Model;

public class ProgrammingLanguage {


    //Database attributes
    public static final String TABLE_NAME = "language";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LANGUAGE = "language";
    public static final String COLUMN_DIFFICULTY = "difficulty";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LANGUAGE + " TEXT,"
                    + COLUMN_DIFFICULTY + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    //Class attributes
    private int id;
    private String name;
    private String difficulty;
    private String description;
    private String timestamp;

    public ProgrammingLanguage(){}

    public ProgrammingLanguage(int id,String name, String difficulty, String description, String timestamp) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


