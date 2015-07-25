package com.example.pascalso.tester;

/**
 * Created by Pascal So on 7/24/2015.
 */

import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.service.exception.ServiceException;
import com.amazonaws.mturk.util.PropertiesClientConfig;

public class HomeworkRequest {

    private RequesterService service;
    private String title= "Math Question";
    private String description = "Solve the math question shown";
    private int numAssignments = 3;
    private double reward = 0.05;

    public HomeworkRequest(){
        service = new RequesterService(new PropertiesClientConfig());
    }

    public void createHomeworkRequest(){
        try {
            HIT hit = service.createHIT(
                    title, description, reward, RequesterService.getBasicFreeTextQuestion(
                            "Can you solve this math question?"), numAssignments);

            System.out.println("Created HIT: " + hit.getHITId());
            System.out.println("HIT location: ");
            System.out.println(service.getWebsiteURL() + "/mturk/preview?groupId=" + hit.getHITTypeId());
        }
        catch(ServiceException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args){
        HomeworkRequest app = new HomeworkRequest();
        app.createHomeworkRequest();
    }
}
