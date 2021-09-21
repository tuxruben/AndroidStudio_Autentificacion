package com.example.autentificacion;

public class usuarios {

    String id;
    String name;
    String email;
    String email_verified_at;
    String created_at;
    String updated_at;

    public usuarios(   ) {
        this.name=null;
        this.id=null;
        this.email=null;
        this.email_verified_at=null;
        this.created_at=null;
        this.updated_at=null;



    }
    public   String getId(){
        return id;

    }
    public   String getNombre(){
        return name;

    }
    public   String getEmail(){
        return email;

    }
    public   String getEmail_verified_at(){
        return email_verified_at;

    }
    public   String getCreated_at(){


        return created_at;
    }
    public   String getUpdated_at(){


        return updated_at;
    }

}