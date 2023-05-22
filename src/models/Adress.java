package models;

public class Adress {
    private String CEP;
    private String typeOfAdress;
    private String complementOfAdress;


    public Adress(AdressRecord adressRecord) {
        this.CEP = adressRecord.cep();
        // if(!adressRecord.complemento().equals(null)){ -> will throw a NullPointerException
        if(!(adressRecord.complemento() == null)){
            this.complementOfAdress = adressRecord.complemento();
        }
        else {
            this.complementOfAdress = "N/A";
        }
        this.typeOfAdress = adressRecord.logradouro();
    }

    @Override
    public String toString() {

        return """
                Adress: %s
                CEP: %s
                Complement: %s
                
                """.formatted(typeOfAdress, CEP, complementOfAdress);
    }
}
