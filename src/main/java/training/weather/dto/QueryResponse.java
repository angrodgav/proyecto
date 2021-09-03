package training.weather.dto;

public class QueryResponse {

    private String title;
    private Integer woeid;

    public String getTitle() {
        return title;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public Integer getWoeid() {
        return woeid;
    }
    public void setWoeid(final Integer woeid) {
        this.woeid = woeid;
    }
}
