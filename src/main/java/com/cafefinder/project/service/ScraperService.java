package com.cafefinder.project.service;

import com.cafefinder.project.model.ResponseDTO;
import java.util.Set;
//set is used when we want a collection of objects where duplicates are not allowed
public abstract class ScraperService {
    public abstract Set<ResponseDTO> getCafes();
    //getcafes is the method name
    //so basically we are making a set of the resposne dto's
    //<responsedto> s a genetric type
}