package GUI.Controllers;

import GUI.Constants.BooksListCommands;
import GUI.Windows.ExportForm;
import Logic.BookListLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BooksListController implements ActionListener, MouseListener {
    private ExportForm exportForm;

    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case BooksListCommands.BTN_FUNC_ADD_BOOK:
                BookListLogic.addBook(JOptionPane.showInputDialog("Type book name"));
                break;
            case BooksListCommands.BTN_FUNC_DELETE_BOOK:
                BookListLogic.deleteBook();
                break;
            case BooksListCommands.BTN_FUNC_OPEN_BOOK:
                BookListLogic.openBook();
                break;
            case BooksListCommands.BTN_FUNC_EXPORT_BOOK:
                exportForm = new ExportForm(this,BooksListCommands.BTN_FUNC_EXPORT_FORM_EXPORT);
                break;
            case BooksListCommands.BTN_FUNC_EXPORT_FORM_DIR_CHOOSE:
                exportForm.showDirChooser();
                break;
            case BooksListCommands.BTN_FUNC_EXPORT_FORM_EXPORT:
                BookListLogic.exportBook(BookListLogic.getSelectedBook(), exportForm.getExportType(), exportForm.getSavePath());
                exportForm.dispose();
                break;
        }
    }
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            actionPerformed(new ActionEvent(new JPanel(),0,BooksListCommands.BTN_FUNC_OPEN_BOOK));
        }
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
}
