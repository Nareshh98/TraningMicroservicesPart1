package com.microservices.controller;

import java.util.List;
import java.util.Optional; 
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.microservices.model.CafeInfo;
import com.microservices.services.CafeInfoService; 
  

  
@Controller                    // return type View
@EnableAutoConfiguration
public class CafeInfoController {

    @Autowired
    CafeInfoService cafeInfoService; 
  
    
    @GetMapping("/")
    public String index() {
        return "index";        // logical name "index" gets mapped to physical view -> /templates/index.html
    }

    @PostMapping("/addCafe")
    public String addCafe(@ModelAttribute("cafeName") String cafeName,@ModelAttribute("cafeDesc")String cafeDesc) {
        System.out.println("Inside the controller addCafe post method");
        CafeInfo cafeInfo=new CafeInfo(cafeName,cafeDesc);
        System.out.println("details of cafeInfo is:"+cafeInfo.getCafeName()+"  "+cafeInfo.getCafeDesc());

        CafeInfo savedCafeInfo=cafeInfoService.addCafe(cafeInfo); //control goes to the service class-addCafe method
        if (savedCafeInfo!=null)
        {
            return "success"; //templates/success.html
        }
        else
        {
            return "fail"; //templates/fail.html
        }

    }
    
    //@ModelAttribute will collect the data form htmlPage and it will stor in variable

    @GetMapping("/getCafes")
    @ResponseBody
    public List<CafeInfo> getCafes() {
        return cafeInfoService.getCafes();        
    }
  
    @GetMapping("/getCafe/{cafeId}")
    @ResponseBody
    public Optional<CafeInfo> getCafe(@PathVariable("cafeId")Integer cafeId) {
        return cafeInfoService.getCafe(cafeId);        
    }

      // @ResponseBody will  convert java object to json formate

    @GetMapping("/count")
    @ResponseBody
    public String getCafeCount() {
        return String.valueOf(cafeInfoService.getCafes().size());        
    }


    @GetMapping("getAll/{cafeId}")
    public ModelAndView getAll(@PathVariable("cafeId")Integer cafeId)
    {
        return cafeInfoService.getAll(cafeId);
        
    }

  
}

