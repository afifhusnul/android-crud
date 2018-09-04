package test1.android.com.test1.Models;

import com.google.gson.annotations.SerializedName;

public class MasterStock {

    @SerializedName("id_ticker")
    private String id;
    @SerializedName("nm_ticker")
    private String tickerName;

    public MasterStock(){};

    public MasterStock(String id, String tickerName) {
        this.id = id;
        this.tickerName = tickerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(String tickerName) {
        this.tickerName = tickerName;
    }
}
