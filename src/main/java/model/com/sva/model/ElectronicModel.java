package com.sva.model;

public class ElectronicModel
{

    private String electronicName;

    private String pictruePath;

    private String message;

    private String moviePath;

    private String id;

    private MapsModel maps;
    
    private StoreModel store;
    
    private AreaModel area;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }


    public String getElectronicName()
    {
        return electronicName;
    }

    public void setElectronicName(String electronicName)
    {
        this.electronicName = electronicName;
    }

    public String getPictruePath()
    {
        return pictruePath;
    }

    public void setPictruePath(String pictruePath)
    {
        this.pictruePath = pictruePath;
    }

    public String getMoviePath()
    {
        return moviePath;
    }

    public void setMoviePath(String moviePath)
    {
        this.moviePath = moviePath;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the maps
     */
    public MapsModel getMaps() {
        return maps;
    }

    /**
     * @param maps the maps to set
     */
    public void setMaps(MapsModel maps) {
        this.maps = maps;
    }

    /**
     * @return the store
     */
    public StoreModel getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(StoreModel store) {
        this.store = store;
    }

    /**
     * @return the area
     */
    public AreaModel getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(AreaModel area) {
        this.area = area;
    }
    
}
