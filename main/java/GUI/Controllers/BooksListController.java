package GUI.Controllers;

import GUI.Constants.BooksListCommands;
import GUI.Windows.BookCreationForm;
import GUI.Windows.ExportForm;
import Logic.BookListLogic;

import javax.swing.*;
import java.awt.event.*;

public class BooksListController implements ActionListener, MouseListener, WindowListener {
    private ExportForm exportForm;
    private BookCreationForm creationForm;

    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case BooksListCommands.BTN_FUNC_ADD_BOOK:
                creationForm = new BookCreationForm(this,BooksListCommands.BTN_FUNC_CREATE_BOOK);
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
            case BooksListCommands.BTN_FUNC_CREATE_BOOK:
                BookListLogic.addBook(creationForm.getValues()[0],creationForm.getValues()[1]);
                creationForm.dispose();
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
    @Override
    public void windowOpened(WindowEvent e) {

    }
    @Override
    public void windowClosing(WindowEvent e) {
        BookListLogic.saveBooks();
    }
    @Override
    public void windowClosed(WindowEvent e) {

    }
    @Override
    public void windowIconified(WindowEvent e) {

    }
    @Override
    public void windowDeiconified(WindowEvent e) {

    }
    @Override
    public void windowActivated(WindowEvent e) {

    }
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
