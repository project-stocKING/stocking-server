package Indexes;

import Tools.Signal;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Beata on 2016-04-10.
 */
public class IndexResult implements Serializable {

    private String indexName;
    private Signal result;
    private Date date;

    public IndexResult(String indexName, Signal result, Date date) {
        this.indexName = indexName;
        this.result = result;
        this.date = date;
    }

    public String getIndexName() {
        return indexName;
    }

    public Signal getResult() {
        return result;
    }

    public Date getDate() {
        return date;
    }
}
