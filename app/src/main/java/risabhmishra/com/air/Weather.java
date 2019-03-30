package risabhmishra.com.air;

public class Weather {
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    String day;
    String min_temp;
    String max_temp;
    String img_url;


    public Weather(String day, String min_temp, String max_temp, String img_url) {
        this.day = day;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.img_url = img_url;
    }

}
