package com.cafefinder.project.service;

import com.cafefinder.project.model.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup; //jsoup is the library to connect webpage and parse its html
import org.jsoup.nodes.Document; //the document represents and entire html page
import org.jsoup.nodes.Element; //element is 1 html elecment
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScraperServiceImpl extends ScraperService {
    //@value helps us read a value from the application.properties
    @Value("#{'${website.urls:}'.split(',')}")
    List<String> urls;//WE CREATE A LIST OF URLS

    @Override //OVERRIDE THE PARENT CLASS METHOD
    public Set<ResponseDTO> getCafes(){
        Set<ResponseDTO> responseDTOS = new HashSet<>();
        //basically create a set of all the scraped cafes

        if (urls == null || urls.isEmpty()) {
            return responseDTOS;
        }

        //loop through all the urls in the set
        for (String url : urls) {
            String trimmedUrl = url.trim();
            if(trimmedUrl.contains("tripadvisor.in")){
                extractDataFromTripAdvisor(responseDTOS, trimmedUrl);
            }else if(trimmedUrl.contains("tripoto.com")){
                extractDataFromTripoto(responseDTOS, trimmedUrl);
            }else if (trimmedUrl.contains("zomato.com")) {
                extractDataFromZomato(responseDTOS, trimmedUrl);
            }
            //check for the which url and then call the correcct function
        }
        //after the scraping it returns the set
        return responseDTOS;
    }

    private void extractDataFromTripAdvisor(Set<ResponseDTO> responseDTO, String url){
        try {
            Document document = connectWithUserAgent(url);//connects with webpage

            Elements cafeLinks = document.select("a[href*=/Restaurant_Review-]");
            //it checks the html document for the imp parts
            //find all the <a> tags whose href contains /restaurent_review
            for (Element link : cafeLinks) {
                //loop trhough the href links we found
                ResponseDTO responseDTOS = new ResponseDTO();
                String title = link.text();
                String href = link.absUrl("href");

                if (StringUtils.isNotEmpty(title) && StringUtils.isNotEmpty(href)) {
                    responseDTOS.setTitle(title.trim());
                    responseDTOS.setUrl(href);
                    responseDTO.add(responseDTOS);
                }
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void extractDataFromTripoto(Set<ResponseDTO> responseDTOS, String url) {
        try {
            Document document = connectWithUserAgent(url);
            Elements headings = document.select("h2, h3");

            for (Element heading : headings) {
                String title = heading.text();
                if (StringUtils.isNotEmpty(title) && title.length() < 100) {
                    ResponseDTO responseDTO = new ResponseDTO();
                    responseDTO.setTitle(title.trim());
                    responseDTO.setUrl(url);
                    responseDTOS.add(responseDTO);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //the same is used for all the 3 just the conditions of elements change
    //this is found by inspecting the html of the page
    private void extractDataFromZomato(Set<ResponseDTO> responseDTOS, String url) {
        try {
            Document document = connectWithUserAgent(url);
            Elements restaurantLinks = document.select("a[href*=/mumbai/]");

            for (Element link : restaurantLinks) {
                String title = link.attr("title");
                String href = link.absUrl("href");

                if (StringUtils.isNotEmpty(title)) {
                    ResponseDTO responseDTO = new ResponseDTO();
                    responseDTO.setTitle(title.trim());
                    responseDTO.setUrl(href);
                    responseDTOS.add(responseDTO);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Document connectWithUserAgent(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .referrer("https://www.google.com")
                .followRedirects(true)
                .timeout(15000)
                .get();
    }
}