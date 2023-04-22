import java.util.HashMap;
import java.util.ArrayList;

public class libraryBooks <DataType> {
    private HashMap< String ,ArrayList<DataType> > books = new HashMap<>(); // books < ID , bookInfo>
    private int count;
    private String lbl1;
    private String lbl2;
    

    libraryBooks(){
        this.count = 0;
        this.lbl1 = "ISBN";
        this.lbl2 =  "Book_Info < Title ,Author> "; 
        

    }

    libraryBooks(String lbl1, String lbl2) {
        this.count = 0;
        this.lbl1 = lbl1;
        this.lbl2 = lbl2;
            
    }
    //Getter Books
    public HashMap< String ,ArrayList<DataType> > getBooks(){
        return this.books;
    }

    //Getter Record
    public ArrayList<DataType> getRecord(String isbn){
        return this.books.get(isbn);
    }
    //Getter Availability of ID
    public boolean getIsAvailable(String isbn) {
        boolean IsAvailable = false;
        for (String bookIsbn : this.books.keySet()){
            if(isbn.equals(bookIsbn)){
                IsAvailable = true;
            }
        }
        return IsAvailable;
    }
    //Insert a book into
    public void insert(String isbn,DataType...records ){ //Multiple variable argument for inserting multiple records
        ArrayList<DataType> bookinfo = new ArrayList<>();
        for (int i = 0; i < records.length; i++){
            bookinfo.add(records[i]);

        }
        this.books.put(isbn, bookinfo);
        this.count += 1;
    }
    //Show book list
    public void show() {
        for(String isbn: this.books.keySet()){
            String info = this.lbl1 + " : " + isbn + " "+ this.lbl2 + " :  <";
            for(int index = 0; index < this.books.get(isbn).size(); index++){
                info +=  this.books.get(isbn).get(index) ;
                if (index == this.books.get(isbn).size() - 1){
                    break;
                }
                info += " , ";
            }
            info += ">";
            System.out.println(info);
        }
        System.out.println(String.format("%d Recodes ....", this.count));
    }

    
    public void delete(String isbn) {
        books.remove(isbn);
        this.count -= 1;
        if (this.count < 0){
            this.count = 0;
        }
        
    }
    
}
