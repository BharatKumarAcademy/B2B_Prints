package com.login2print.b2bprintz.supportClasses;

import android.content.Context;
import android.widget.Toast;

public class Validators {




    public String validateDefault(String fieldName, String fieldValue){
        String response="NoError";
            if(fieldValue.length()==0) {
                response = "Enter "+fieldName+"?";
            }

            return  response;
    }

    public String validatePhone(String fieldValue){
        String response="NoError";

        if(fieldValue.length()==0){
            response = "Enter Mobile Number?";
        }else if(fieldValue.length()<10){
            response = "Enter 10 Digit Mobile Number?";
        } else if("012345".contains(fieldValue.substring(0,1))){
            response = "Enter Valid Mobile Number?";
        }

        return  response;
    }

    public String validateEmail(String fieldValue){
        String response="NoError";
        if(fieldValue.length()==0){
            response = "Enter Email?";
        }else if (!fieldValue.matches("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+")) {
            response = "Enter Valid Email Address?";
        }

        return  response;
    }
}
