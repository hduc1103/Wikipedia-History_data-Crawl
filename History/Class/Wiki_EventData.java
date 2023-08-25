package History.Class;

public class Wiki_EventData {
    private String name;
    private String time;
    private String dynastyName;
    private String description;

    public Wiki_EventData(String name, String time, String dynastyName, String description) {
        this.name = name;
        this.time = time;
        this.dynastyName = dynastyName;
        this.description = description;
    }

    public Wiki_EventData() {

    }

    public Wiki_EventData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDynastyName() {
        return dynastyName;
    }

    public void setDynastyName(String dynastyName) {
        this.dynastyName = dynastyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
