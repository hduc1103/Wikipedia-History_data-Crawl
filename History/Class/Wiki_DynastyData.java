package History.Class;

public class Wiki_DynastyData {
    private String name;
    private String start_time;
    private String end_time;

    public Wiki_DynastyData() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Wiki_DynastyData(String name, String start_time, String end_time) {
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}