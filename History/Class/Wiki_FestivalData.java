package History.Class;

public class Wiki_FestivalData {
    private String name;
    private String time;
    private String place;
    private String character;
    private String firstheld;

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getFirstheld() {
        return firstheld;
    }

    public void setFirstheld(String firstheld) {
        this.firstheld = firstheld;
    }

    public Wiki_FestivalData(String name, String time, String place, String character, String firstheld) {
        this.name = name;
        this.time = time;
        this.place = place;
        this.character = character;
        this.firstheld = firstheld;
    }

}
