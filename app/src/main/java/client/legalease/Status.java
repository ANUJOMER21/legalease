package client.legalease;

public class Status {
    public String getStatus(int Status){
        String paymentst="" ;
        if(Status==( 1 )){
            paymentst= "Offer Created" ;
        }
        else if(Status==( 2 )){
            paymentst= "Offer Rejected" ;
        }
        else if(Status==( 3 )){
            paymentst= "Offer Accepted" ;
        }
        else if(Status==( 4 )){
            paymentst= "Payment Pending" ;
        }
        else if(Status==( 5 )){
            paymentst= "Payment Failed" ;
        }
        else if(Status==( 6 )){
            paymentst= "In Progress" ;
        }
        else if(Status==( 7 )){
            paymentst= "Work Submited" ;
        }
        else if(Status==( 8 )){
            paymentst= "Work Completed" ;
        }
return  paymentst;

    }
    public String getStatus(String Status){
        String paymentst="" ;
        if(Status .equals( "1" )){
            paymentst= "Offer Created" ;
        }
        else if(Status .equals( "2" )){
            paymentst= "Offer Rejected" ;
        }
        else if(Status .equals( "3" )){
            paymentst= "Offer Accepted" ;
        }
        else if(Status .equals( "4" )){
            paymentst= "Payment Pending" ;
        }
        else if(Status .equals( "5" )){
            paymentst= "Payment Failed" ;
        }
        else if(Status .equals( "6" )){
            paymentst= "In Progress" ;
        }
        else if(Status .equals( "7" )){
            paymentst= "Work Submited" ;
        }
        else if(Status .equals( "8" )){
            paymentst= "Work Completed" ;
        }
        return  paymentst;

    }

}
