package com.example.autentificacion;

public class data {

        String id;
        String nombre;
        String resumen;
        String npagina;
        String edicion;
        String autor;
        String precio;
        public data(   ) {
            this.nombre=null;
            this.id=null;
            this.resumen=null;
            this.npagina=null;
            this.edicion=null;
            this.precio=null;
            this.autor=null;



        }
        public   String getId(){
            return id;

        }
        public   String getNombre(){
            return nombre;

        }
        public   String getResumen(){
            return resumen;

        }
        public   String getNpagina(){
            return npagina;

        }
        public   String getEdicion(){


            return edicion;
        }
        public   String getAutor(){


            return autor;
        }
        public   String getPrecio(){
            return precio;

        }
    }