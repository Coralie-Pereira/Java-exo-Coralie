
import java.io.*;
import java.text.ParseException;
import java.util.*;
import model.Contact;


public class App {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        afficherMenu();
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1":
                    ajouterContact();
                    break;
                case "2":
                    listerContact("byName");
                    break;
                case "3":
                    editContact("Update");
                    break;
                case "4":
                    editContact("Delete");
                    break;
                
                case "5":
                listerContact("byDate");
                    break;    
                case "6":
                ListerContactByName();
                    break;             
                case "q":
                    scan.close();
                    return;
                default:
                    System.out.println("Boulet!!!!");
                    break;
            }
            afficherMenu();
        }
    }
    public static void afficherMenu() {
        
        ArrayList<String> menus = new ArrayList<>();
        menus.add("-- MENU --");
        menus.add("1- Ajouter un contact");
        menus.add("2- Lister les contacts par nom");
        menus.add("3- Modifier les contacts");
        menus.add("4-Supprimer contact");
        menus.add("5-Trier contact par date");
        menus.add("6-Rechercher contact sur nom");
        menus.add("q- Quitter");
        for (String s : menus) {
            System.out.println(s);
        }
    }
    private static void listerContact(String sortType) {
        // Contact c = new Contact();
        try {
            ArrayList<Contact> liste = Contact.lister();

            Collections.sort(liste, new Comparator<Contact>() {
                @Override
                public int compare(Contact c1, Contact c2) {
                    if (sortType=="byDate"){
                        return c1.getDateNaissance().compareTo(c2.getDateNaissance());
                    }else{
                        return c1.getNom().compareTo(c2.getNom());
                    }
                    
                }
            });

            for (Contact contact : liste) {
                System.out.println(contact.getPrenom() + " " + contact.getNom()+ " " + contact.getDateNaissance());
            }
        } catch (IOException e) {
            System.out.println("Erreur avec le fichier");
        }

    }




    private static void ajouterContact() {

        Contact c = new Contact();
        System.out.println("Saisir le nom:");
        c.setNom(scan.nextLine());
        System.out.println("Saisir le prénom:");
        c.setPrenom(scan.nextLine());

        do {
            try {
                System.out.println("Saisir le téléphone:");
                c.setNumero(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir le mail:");
                c.setMail(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir la date de naissance:");
                c.setDateNaissance(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Error, try again!");
            }
        } while (true);

        try {
            c.enregistrer();
            System.out.println("Contact enregistré.");
        } catch (IOException e) {
            System.out.println("Erreur à l'enregistrement");
        }

    }


    private static void editContact(String type) {
        try {
            int line = 0 ;
            System.out.println("Saisissez le numero de téléphone que vous voulez modifier.");
            ArrayList<Contact> liste = Contact.lister();
            String numberToEdit = scan.nextLine();

            for (Contact contact : liste) {
                line++;
                if(Objects.equals(type, "Update")){
                    if (contact.getNumero().equals(numberToEdit)) {
                        System.out.println("Vous modifiez les informations de " + contact.getNom() + " " + contact.getPrenom() + " " + line + ".");

                        System.out.println("Saisir le nom");
                        contact.setNom(scan.nextLine());

                        System.out.println("Saisir le prenom");
                        contact.setPrenom(scan.nextLine());

                        System.out.println("Saisir le numero");
                        contact.setNumero(scan.nextLine());

                        System.out.println("Saisir le mail");
                        contact.setMail(scan.nextLine());

                        System.out.println("Saisir la date de naissance");
                        contact.setDateNaissance(scan.nextLine());

                        //Contact.clearFile();
                        //contact.enregistrer();
                        if(removeOneData("contacts.csv",line) ){
                            contact.enregistrer();
                        }
                }else{
                        removeOneData("contacts.csv",line);
                }
                }else{
                    //System.out.println("Personne dans la liste n'a ce numéro de téléphone.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean removeOneData(String filePath,int deleteLine){

        int line = 0 ;
        String currentLine;
        String tempFile = "temp.csv";
        File older = new File(filePath);
        File newer = new File(tempFile);


        try {

            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tempFile, true)));

            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((currentLine = br.readLine()) != null){
                line++;
                if(deleteLine != line){
                    pw.println(currentLine);
                }

            }

            pw.flush();
            pw.close();
            br.close();

            older.delete();
            File dump = new File(filePath);
            newer.renameTo(dump);

            return true;

        }catch (Exception e){


            return false;
        }

    }

    public class ContactList {

        private List<Contact> contacts;

        public void removeContact(int index) {
            if (index < 0 || index >= contacts.size()) {
                throw new IndexOutOfBoundsException("Index is out of range!");
            }
            contacts.remove(index);
        }

      
    }


    private static void ListerContactByName() {
        try {
           
            System.out.println("Votre contact");
            ArrayList<Contact> liste = Contact.lister();
            String nameToEdit = scan.nextLine();

            for (Contact contact : liste) {
              
              
                    if (contact.getNom().equals(nameToEdit)) {
                        System.out.println(contact.getPrenom() + " " + contact.getNom()+ " " + contact.getDateNaissance());
                }else{
                    System.out.println(nameToEdit + "N'existe pas" );
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
