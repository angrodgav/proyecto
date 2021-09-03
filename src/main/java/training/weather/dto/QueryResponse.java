package training.weather.dto;

public class QueryResponse {

    private String title;
    private Long woeid;

    public String getTitle() {
        return title;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public Long getWoeid() {
        return woeid;
    }
    public void setWoeid(final Long woeid) {
        this.woeid = woeid;
    }
}
