package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact {
    public static final String SEPARATEUR = ";";

    private String nom;
    private String prenom;
    private String numero;
    private String mail;
    private Date dateNaissance;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom.toUpperCase();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) throws ParseException {
        Pattern pat = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher test = pat.matcher(numero);
        if (test.matches()) {
            this.numero = numero;
        } else {
            throw new ParseException("Format du numéro incorrect.", 0);
        }
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws ParseException {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher test = pat.matcher(mail);
        if (test.matches()) {
            this.mail = mail;
        } else {
            throw new ParseException("Format du mail incorrect.", 0);
        }
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) throws ParseException {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        this.dateNaissance = dtf.parse(dateNaissance);
    }

    public void enregistrer() throws IOException {
        
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
        try {

            pw.println(this.toString());

        } finally {
            pw.close();
        }
    }

    public static ArrayList<Contact> lister() throws IOException {
        ArrayList<Contact> list = new ArrayList<>();
        BufferedReader buf = new BufferedReader(new FileReader("contacts.csv"));
        try {
            String ligne = buf.readLine();
            while (ligne != null) {
                String[] tab = ligne.split(SEPARATEUR);
                Contact c = new Contact();
                c.setNom(tab[0]);
                c.setPrenom(tab[1]);
                c.setMail(tab[2]);
                c.setNumero(tab[3]);
                c.setDateNaissance(tab[4]);
                list.add(c);
                ligne = buf.readLine();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur de lecture sur le fichier");
        } finally {
            buf.close();
        }
        return list;
    }

    @Override
    public String toString() {
        // return this.getNom() + ";" + this.getPrenom();*
        StringBuilder build = new StringBuilder();
        build.append(getNom());
        build.append(SEPARATEUR);
        build.append(getPrenom());
        build.append(SEPARATEUR);
        build.append(getMail());
        build.append(SEPARATEUR);
        build.append(getNumero());
        build.append(SEPARATEUR);
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        build.append(dtf.format(getDateNaissance()));
        return build.toString();
    }

    public static void ecrire(ArrayList<Contact> liste) {
    }
    public static void clearFile() {
        try {
            FileWriter writer = new FileWriter("getId.csv", false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saver(ArrayList<Contact> liste) {
    }

    public int getId() {
        return 0;
    }

    public int getName(){
        return 0;
    }
    public int getDate(){
        return 0;
    }







    
}
