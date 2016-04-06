package Models;

import java.util.ArrayList;

/**
 * Created by Michał on 2016-04-06.
 */
public class IndexInformation {
    private String name;
    private ArrayList<String> parameters;

    public IndexInformation(){
        name = "";
        parameters = new ArrayList<String>();
    }

    public IndexInformation(String name)
    {
        this.name = name;
        parameters = new ArrayList<String>();
    }

    public void addParameter(String parameter){
        parameters.add(parameter);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getParameters(){
        return parameters;
    }

    @Override
    public String toString() {
        return
                "Name='" + name + '\'' +
                        ", parameters=" + parameters.toString();

    }


}
