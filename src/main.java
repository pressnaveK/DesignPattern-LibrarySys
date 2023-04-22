import java.util.Date;
import java.util.Scanner;

enum Type {
    S_TOTAL,
    S_AVAILABLE,
    S_BORROW,
    S_OVERDUE,
    F_ADD,
    F_REMOVE,
    F_BORROW,
    F_RETURN,
    F_TEST_OVERDUE,
    EXIT
}

public final class main {
    libraryBooks <String> available = new libraryBooks<>();  // < isbn <book_title , author> >
    libraryBooks <String> total = new libraryBooks<>();      // < isbn <book_title , author> >
    libraryBooks <String> borrow = new libraryBooks<>("ISBN" , "Book_info <Title , Borrower>");     // < isbn <book_title , borrower> >
    libraryBooks <String> overdue = new libraryBooks<>("ISBN" , "Book_info <Title , Borrower>");    // < isbn <book_title , borrower> >

    libraryBooks <Date> borrowInfo = new libraryBooks<>("ISBN" , "Borrow_info <date , due ,return>"); // < isbn < borrow_date , due_date> >

    //Add a book to the library
    public void addbook(String isbn,String bookTitle, String author){
        if(available.getIsAvailable(isbn)){
            return;
        }
        available.insert(isbn,bookTitle,author);
        total.insert(isbn,bookTitle,author);

    }
    //Remove a book from the Library 
    public void removeBook(String isbn){
        try{
            total.delete(isbn);
            try{
                available.delete(isbn);
                System.out.println("Book Romoved Successfully...");
            }catch(Exception e){
                System.out.println("Book Romoved Successfully...");
            }

        }catch(Exception e){
            System.out.println("Invalid ISBN");
        }
    }
    //Borrow a book from the library
    public void BorrowBook(String isbn , String borrower , long day){
        try{
            String title = available.getRecord(isbn).get(0);
            borrow.insert(isbn, title ,borrower);
            day *=  24 * 60 * 60 * 1000;
            Date date = new Date();
            day += date.getTime();
            Date due = new Date(day);
            Date rEturn = null;
            borrowInfo.insert(isbn, date, due ,rEturn);
            available.delete(isbn);

        }catch(Exception e){
            System.out.println("Book is not available...");
        }

    }
    //Test whether OverDued
    public void testOverDue(){
        int count = 0;
        for(String isbn: borrowInfo.getBooks().keySet()){

            Date day = new Date();
            Date due = borrowInfo.getBooks().get(isbn).get(1);
            boolean isOverDued = day.after(due) || day.equals(due);
            if(isOverDued){
                String title = borrow.getRecord(isbn).get(0);
                String author = borrow.getRecord(isbn).get(1);
                overdue.insert(isbn,title,author);
                borrow.delete(isbn);
                count += 1;
            }
        }
        System.out.println(String.format("%d records overdued", count));

    }
    //Return a book to the library
    public void ReturnBook (String isbn){
        try{
            
            try{
                testOverDue();
                overdue.delete(isbn);
                System.out.println("Returned OverDued...");
            }catch(Exception e){
            }
            borrow.delete(isbn);
            Date day = new Date();
            borrowInfo.getRecord(isbn).set(2,day);
            String title = total.getRecord(isbn).get(0);
            String author = total.getRecord(isbn).get(1);
            available.insert(isbn, title,author);
            System.out.println("Returned Sucessfully...");

        }catch(Exception e){
            System.out.println("Invalid ISBN...");
        }
    }



    //Show the results
    public <DataType> void show(libraryBooks<DataType> books){
        books.show();    
    }

    //Run SystemS
    public static void main(String[] args) throws Exception {
        main libraryOpration = new main();
        boolean isLooping = true;
        while(isLooping){
            Scanner input = new Scanner(System.in);
            System.out.println("Choose operation to continue...\n S_TOTAL \n S_AVAILABLE \n S_BORROW \n S_OVERDUE \n F_ADD \n F_REMOVE \n F_BORROW \n F_RETURN \n F_TEST_OVERDUE \n EXIT");
            String text = input.nextLine();
            System.out.println("\n");
            try{
                Type operation = Type.valueOf(text);
                switch(operation){
                    case S_TOTAL :
                        libraryOpration.show(libraryOpration.total);
                        break;
                    case S_AVAILABLE :
                        libraryOpration.show(libraryOpration.available);
                        break;
                    case S_BORROW :
                        libraryOpration.show(libraryOpration.borrow);
                        libraryOpration.show(libraryOpration.borrowInfo);
                        break;
                    case S_OVERDUE :
                        libraryOpration.show(libraryOpration.overdue);
                        break;
                    case F_ADD :
                        System.out.println("Enter ISBN , TITLE , AUTHOR respectively................................................................:");
                        String isbn = input.nextLine();
                        String title = input.nextLine();
                        String author = input.nextLine();
                        libraryOpration.addbook(isbn, title, author);
                        break;
                    case F_REMOVE:
                        System.out.println("Enter ISBN ..............................................................................................:");
                        String isbn_r = input.nextLine();
                        libraryOpration.removeBook(isbn_r);
                        break;
                    case F_BORROW:
                        System.out.println("Enter ISBN , BORROWER , DAYS respectively................................................................:");
                        String isbn_b = input.nextLine();
                        String borrower = input.nextLine();
                        String day = input.nextLine();
                        Long d = Long.parseLong(day);
                        libraryOpration.BorrowBook(isbn_b, borrower, d);
                        break;
                    case F_RETURN:
                        System.out.println("Enter ISBN ..............................................................................................:");
                        String isbn_rt = input.nextLine();
                        libraryOpration.removeBook(isbn_rt);
                        break; 
                    case F_TEST_OVERDUE:
                        libraryOpration.testOverDue(); 
                        break;  
                    default:
                        isLooping = false;
                        break;
    
                }
    

            }catch(Exception e){
                break;
            }

        }

        
    }
}
