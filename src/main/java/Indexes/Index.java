package Indexes;

public abstract class Index
{
    final String name;

    public Index(String name)
    {
        this.name=name;
    }

    public String getName()
    {
        return name;
    }
}