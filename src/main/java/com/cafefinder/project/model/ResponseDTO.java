package com.cafefinder.project.model;
import java.util.Objects;
//to import java's built in utility class i need to to create proper hash code
public class ResponseDTO
    //dto= data transfer object - used to transfer data in different layers of the application
{
    private String title;
    private String url;

    public ResponseDTO(){
    }
    public ResponseDTO(String title, String url)
    {
        this.title = title;
        this.url = url;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    @Override
    public boolean equals(Object o)
    {
        //here we are checking for duplicate data
        //if the 2 responses are same we equate them
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDTO responseDTO = (ResponseDTO) o;
        return Objects.equals(title,responseDTO.title)
                && Objects.equals(url,responseDTO.url);
        //every class inherits a equals()method and thus we are overriding it
    }
    //get useed funcitons are the getter methods since the variables titles and url
    //are private if other classes need to access them they will do so via the getter aand setter method
    //getter to access
    //setter to change or modify them

    @Override
    public int hashCode()
    {
        return Objects.hash(url, title);
    }
    //this function createss hash code based on url and title
    // java uses hashcode() to locate objects
    //equal objects have the same hashcode

    @Override
    public String toString(){
        return "ResponseDTO{"+
                "Title=" + title +
                ", URL=" + url +
                '}';
    }
    //we convert the object back to string so that its easier to understand

}
// this class is a java object designed to hold the title and the url data
// and transfer that data from backend to another layer
//json api reposne