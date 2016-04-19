package Indexes;

public abstract class Indicator
{
    final String name;

    public Indicator(String name)
    {
        this.name=name;
    }

    public String getName()
    {
        return name;
    }
}