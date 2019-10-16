package com.western.game.center.westerngamecenter.User_Constant;


public class Convert {

    private long Money ;

    private int Num ;

    private long Time ;

    public static final String TAG ="===>" ;

    public Convert(long value , int num , boolean flag) {
        if (flag){
            this.Money = value;
        }else {
            this.Time = value;
        }

        this.Num  = num ;
    }



    public long result_time (){
        switch (this.Num){
            case  1 :

                return   (this.Money * 60) / 8000 ;

            case 2 :
               return   (this.Money * 60)/ 10000 ;

            case 3 :
                return  (this.Money * 60)/ 12000 ;

            case 4 :
                return   (this.Money * 60)/14000 ;
        }
       return  0 ;
    }


    public long result_money (){

        switch (this.Num){

            case 1 :
                return (this.Time * 8000) / 60 ;

            case 2 :
                return (this.Time * 10000) / 60 ;

            case 3 :
                return (this.Time * 12000) / 60 ;

            case 4 :
                return (this.Time * 14000) / 60 ;

            default:
                return (this.Time * 8000) / 60 ;
        }
    }
}
