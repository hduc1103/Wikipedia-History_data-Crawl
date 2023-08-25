package History.Class;

public class Wiki_TourismData {
    private String name;
    private String location;
    private String type;
    private String recogi_year;
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecogi_year() {
        return recogi_year;
    }

    public void setRecogi_year(String recogi_year) {
        this.recogi_year = recogi_year;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Wiki_TourismData() {
    }

    public Wiki_TourismData(String name, String location, String type, String recogi_year, String note) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.recogi_year = recogi_year;
        this.note = note;
    }

}