
package Admin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ObjectOutputStream;

public class PDFController implements Initializable {

    DataInputStream din;
    DataOutputStream dout;
    
    String report_type;
    
    @FXML
    private ImageView test;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        report_type=AdminPageController.identifier;
        System.out.println(report_type);
        try {
            Socket socket = new Socket("127.0.0.1",11999);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(report_type);

            ArrayList<String> user =new ArrayList<>() ;
            try {
                user=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SeeUController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Document document = new Document();
            PdfPTable table = new PdfPTable(5);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table.addCell("First Name");
            table.addCell("Last Name");
            table.addCell("Username");
            table.addCell("Usertype");
            table.addCell("Bank Account");
            
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells(); 
            for (int j=0;j<cells.length;j++){
               cells[j].setBackgroundColor(BaseColor.GRAY);
            }
            
            for (String string : user) {
                String[] s=string.split("~");
                
                table.addCell(s[0]);
                table.addCell(s[1]);
                table.addCell(s[2]);
                table.addCell(s[3]);
                table.addCell(s[4]);
            }
            
            try {
                if (report_type.equalsIgnoreCase("current")) {
                    PdfWriter.getInstance(document, new FileOutputStream("Z:/CurrentUsersReport.pdf"));
                } else {
                    PdfWriter.getInstance(document, new FileOutputStream("Z:/AllTimeUsersReport.pdf"));
                }
                
                document.open();
                document.add(table);
                document.close();
            } catch (DocumentException ex) {
                Logger.getLogger(PDFController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }catch (IOException ex) {
            Logger.getLogger(PDFController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    
}
