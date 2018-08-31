package GUI.Controllers;

import GUI.Constants.BooksListCommands;
import Logic.SimpleLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BooksListController implements ActionListener, MouseListener {
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case BooksListCommands.BTN_FUNC_ADD_BOOK:
                SimpleLogic.addBook(JOptionPane.showInputDialog("Type book name"));
                break;
            case BooksListCommands.BTN_FUNC_DELETE_BOOK:
                SimpleLogic.deleteBook();
                break;
            case BooksListCommands.BTN_FUNC_OPEN_BOOK:
                SimpleLogic.openBook();
                break;
            case BooksListCommands.BTN_FUNC_EXPORT_BOOK:
                JOptionPane.showMessageDialog(null,"export check");
                break;
        }
    }
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            SimpleLogic.openBook();
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
