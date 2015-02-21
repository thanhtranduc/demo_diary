package com.thanhtd.diaryApp.data;

import java.util.HashMap;
import java.util.Map;

/**
 * This holder class allow to store any instance of specific class as an singleton instance.
 * Do NOT used this holder for application state maintain, only for reference of any singleton instance.
 * <p/>
 * User: a
 * Time: 11:30 AM
 */
public class SingletonHolder
{
// ------------------------------ FIELDS ------------------------------

    private static SingletonHolder instance;

    private Map<Class, Object> singletonMap = new HashMap<Class, Object>();


    public <T> T get(Class<T> clazz)
    {
        return (T) singletonMap.get(clazz);
    }

    public void add(Object singleton)
    {
        add(singleton, false);
    }

    public void add(Object singleton, boolean forceUpdate)
    {
        if (!singletonMap.containsKey(singleton.getClass()) || forceUpdate)
        {
            singletonMap.put(singleton.getClass(), singleton);
        }
    }

    public void add(Object singleton, Class type)
    {
        if (!singletonMap.containsKey(singleton.getClass()))
        {
            singletonMap.put(type, singleton);
        }
    }

    public void add(Object singleton, Class type, boolean forceUpdate)
    {
        if (!singletonMap.containsKey(singleton.getClass()) || forceUpdate)
        {
            singletonMap.put(type, singleton);
        }
    }

    public static SingletonHolder getInstance()
    {
        if (instance == null)
        {
            instance = new SingletonHolder();
        }
        return instance;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private SingletonHolder()
    {
        // Default constructor is set as private, so no instance
        // could be make, guarantee the singleton instance
        // exist within the application.
    }

    public void remove(Class clazz)
    {
        if (singletonMap.get(clazz) != null)
        {
            singletonMap.remove(clazz);
        }
    }
}
