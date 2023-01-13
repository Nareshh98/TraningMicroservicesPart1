package com.microservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; 
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.microservices.model.CafeInfo;
import com.microservices.model.CafeReview;
import com.microservices.repository.CafeInfoRepository; 
  

  
@Service
@EnableAutoConfiguration
public class CafeInfoService {
    @Autowired
    CafeInfoRepository cafeInfoRepository;

    @Autowired
    RestTemplate restTemplate; 
 // RestTemplate restTemplate = new RestTemplate();
  
    public CafeInfo addCafe(CafeInfo cafeInfo)    {
        System.out.println("new cafeInfo details inside addCafe service is:"+cafeInfo.getCafeName()+" "+cafeInfo.getCafeDesc());
        System.out.println("repo object:"+cafeInfoRepository);
        return cafeInfoRepository.save(cafeInfo);
    }

    public List<CafeInfo> getCafes() {
        return cafeInfoRepository.findAll(); //return all CafeInfo objects
    } 
  
    public Optional<CafeInfo> getCafe(Integer cafeId) {
        return cafeInfoRepository.findById(cafeId); //return only 1 cafeInfo object based on the cafeId
    }

    @SuppressWarnings("unchecked")
    public ModelAndView getAll(Integer cafeId)
    {
        ModelAndView mv=new ModelAndView();
        mv.addObject("cafeInfo",this.getCafe(cafeId).get());
       
        //Adding cafe detials into ModelAndView

        final String uri = "http://localhost:8096/getReview/"+""+cafeId; 
             
        List<CafeReview> result = restTemplate.getForObject(uri, List.class);
        
        // restTemplate is same RestApi to make call
        // in restTemplate Object is method 
        // getting ReView more than one  so we use list as return type
        // and Storing in List and converting to String 
        // adding to ModelAndView
        
        System.out.println("details of results:"+result.toString());

        mv.addObject("cafeReview",result);

        mv.setViewName("cafeAll");  // View page 
        return mv;


    } 
  
     
  
}
