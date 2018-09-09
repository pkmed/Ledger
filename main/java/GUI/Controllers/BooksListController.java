package GUI.Controllers;

import GUI.Constants.BooksListCommands;
import Logic.BookListLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BooksListController implements ActionListener, MouseListener {
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
                BookListLogic.exportBook("","");
                break;
        }
    }
    public void mouseClicked(MouseEvent e) {
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
