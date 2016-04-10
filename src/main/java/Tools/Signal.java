package Tools;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by Beata on 2016-04-10.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = SignalSerializer.class)
public enum Signal implements Serializable{

    buy("buy"), sell("sell");

    private String name;

    Signal (final String n) {
        this.name = n;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
