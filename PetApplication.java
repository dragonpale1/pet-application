//CSC 422
//11/1/2020
package petapplication;

import java.util.Scanner;
import java.util.ArrayList;



class Pet
{
    private String name;
    private int age;
    private int id;

    public Pet(String _name, int _age)
    {
        this.name = _name;
        this.age = _age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String _name)
    {
        this.name = _name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int _age)
    {
        this.age = _age;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}




class PetManager extends ArrayList<Pet>
{
    public void AddAndIndexItem(Pet item)
    {
        int itemIndex = this.size();
        item.setId(itemIndex);
        this.add(item);
    }

    public int getSize()
    {
        return this.size();
    }

    public Pet getItem(int index)
    {
        return this.get(index);
    }

    public void removeItem(int index)
    {
        PetManager newList = new PetManager();

        int i = 0;
        for (i = 0; i < this.getSize(); i++)
        {
            if (i != index)
            {
                newList.AddAndIndexItem(this.getItem(i));
            }
        }
        this.clear();
        this.addAll(newList);
    }
}



public class PetApplication
{
    //data is global
    private static PetManager allPets;
    private static Scanner scanner;
    private static String clearDelimiter = null;

    public static void main(String[] args)
    {
        //misc initializations
        allPets = new PetManager();
        // scanner
        scanner = new Scanner(System.in);

        //sample items
//        Pet first = new Pet("Clunky", 4);
//        Pet second = new Pet("Oppa", 3);
//        Pet third = new Pet("Smelly", 2);
//        Pet fourth = new Pet("Smelly", 4);
//        allPets.AddAndIndexItem(first);
//        allPets.AddAndIndexItem(second);
//        allPets.AddAndIndexItem(third);
//        allPets.AddAndIndexItem(fourth);

        //start the UI
        // infinite program loop
        boolean programActive = true;
        System.out.print("Starting Pet Database program.\n");

        while (programActive)
        {
            //menu
            System.out.print("\nWhat would you like to do?"
                    + "\n\t1) View all pets."
                    + "\n\t2) Add more pets."
                    + "\n\t3) Update an existing pet."
                    + "\n\t4) Remove an existing pet."
                    + "\n\t5) Search pets by name."
                    + "\n\t6} Search pets by age."
                    + "\n\t7) Exit program."
                    + "\nYour choice: ");

            int userChoice = scanner.nextInt();

            switch (userChoice)
            {
                case 1:
                    DisplayList(allPets);
                    break;
                case 2:
                    AddPet();
                    break;
                case 3:
                    UpdatePet(allPets);
                    break;
                case 4:
                    RemovePet(allPets);
                    break;
                case 5:
                    SearchPetByName();
                    break;
                case 6:
                    SearchPetByAge();
                    break;
                case 7:
                    programActive = false;
                    System.out.println("Goodbye.\n");
                    break;
            }
        }

        scanner.close();
    }//end main method

    //
    //functions
    //
    
    public static void DisplayList(PetManager list)
    {
        int i;
        GenerateHeaderLine();
        for (i = 0; i < list.getSize(); i++)
        {
            Print(list.getItem(i));
        }
        GenerateCloseLine(i);
    }
    

    public static void Print(Pet item)
    {
        System.out.printf("\n| %3d | %-10s | %4d |", item.getId(), item.getName(), item.getAge());
    }
    

    public static void GenerateHeaderLine()
    {
        System.out.printf("\n+-------------------------+\n");
        System.out.printf("| %3s | %-10s | %4s |", "ID", "Name", "Age");
        System.out.printf("\n+-------------------------+");
    }

    
    public static void GenerateCloseLine(int totalCount)
    {
        System.out.printf("\n+-------------------------+\n");
        System.out.printf("%-1d rows in set\n", totalCount);
    }

    
    public static void AddPet()
    {
        boolean loopIsActive = true;

        int petAge = 0;
        int petCount = 0;
        String userResponse;
        String petName;

        //initiate loop
        while (loopIsActive)
        {
            scanner = new Scanner(System.in);

            //clear last loop values
            petAge = 0;
            petName = "";
            //initiate request
            System.out.print("add pet using format: name <space> age:");

            //get user input
            userResponse = scanner.nextLine();

            //looks like the word "done" triggers the end of the loop
            if (userResponse.compareTo("done") != 0)
            {
                String[] data = userResponse.split(" ");

                petName = data[0];
                petAge = Integer.parseInt(data[1]);

                Pet newPet = new Pet(petName, petAge);
                allPets.AddAndIndexItem(newPet);
            } else
            {
                loopIsActive = false;
            }
        }
    }
    

    public static void SearchPetByName()
    {
        PetManager tempList = new PetManager();
        String searchTermName;

        scanner = new Scanner(System.in);

        //initiate request
        System.out.print("Enter a name to search:");
        //get user input
        searchTermName = scanner.nextLine();

        for (Pet item : allPets)
        {
            String itemName = item.getName();
            if (itemName.trim().toUpperCase().compareTo(searchTermName.trim().toUpperCase()) == 0)
            {
                Pet p = new Pet(item.getName(), item.getAge());
                p.setId(item.getId());
                tempList.add(p);
            }
        }
        DisplayList(tempList);
    }

    
    
    public static void SearchPetByAge()
    {
        PetManager tempList = new PetManager();
        int searchTermInt;

        scanner = new Scanner(System.in);

        //initiate request
        System.out.print("Enter age to search:");
        //get user input
        searchTermInt = scanner.nextInt();

        for (Pet item : allPets)
        {
            int itemAge = item.getAge();
            if (searchTermInt == itemAge)
            {
                Pet p = new Pet(item.getName(), item.getAge());
                p.setId(item.getId());
                tempList.add(p);
            }
        }
        DisplayList(tempList);
    }

    
    
    public static void UpdatePet(PetManager list)
    {
        DisplayList(list);
        scanner = new Scanner(System.in);

        //initiate request
        System.out.print("\nEnter the pet ID you want to update:");
        int id = scanner.nextInt();
        //clear out the enter button
        clearDelimiter = scanner.nextLine();

        Pet petToUpdate = list.get(id);
        System.out.print(petToUpdate.getName());
        System.out.print("\nEnter new name and new age:");

        String userResponse = scanner.nextLine();

        String[] data = userResponse.split(" ");

        petToUpdate.setName(data[0]);
        petToUpdate.setAge(Integer.parseInt(data[1]));
    }

    
    
    public static void RemovePet(PetManager list)
    {
        scanner = new Scanner(System.in);

        //initiate request
        System.out.print("\nEnter the pet ID to remove:");
        int id = scanner.nextInt();
        //clear out the enter button
        clearDelimiter = scanner.nextLine();

        Pet petToRemove = list.get(id);

        list.removeItem(id);

        System.out.print("\n" + petToRemove.getName() + " is removed.");
    }

}
