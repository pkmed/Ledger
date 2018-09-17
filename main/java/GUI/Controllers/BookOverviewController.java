package GUI.Controllers;

import GUI.Constants.BookOverviewCommands;
import GUI.Windows.EntryCreateForm;
import Logic.BookListLogic;
import Logic.BookOverviewLogic;

import javax.swing.*;
import java.awt.event.*;

public class BookOverviewController implements ActionListener, MouseListener,WindowListener {

    private EntryCreateForm entryForm;

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case BookOverviewCommands.BTN_FUNC_ADD_ENTRY:
                entryForm = new EntryCreateForm(this, BookOverviewCommands.BTN_FUNC_CREATE_ENTRY);
                break;
            case BookOverviewCommands.BTN_FUNC_REMOVE_ENTRY:
                BookOverviewLogic.removeEntry();
                break;
            case BookOverviewCommands.BTN_FUNC_EDIT_ENTRY:
                entryForm = new EntryCreateForm(this, BookOverviewCommands.BTN_FUNC_EDIT_ENTRY_FORM);
                entryForm.setEntry(BookOverviewLogic.getSelectedEntry().split(";")[0]);
                entryForm.setAmount(BookOverviewLogic.getSelectedEntry().split(";")[1]);
                break;
            case BookOverviewCommands.BTN_FUNC_CREATE_ENTRY:
                BookOverviewLogic.addEntry(entryForm.getValues());
                entryForm.dispose();
                break;
            case BookOverviewCommands.BTN_FUNC_EDIT_ENTRY_FORM:
                BookOverviewLogic.editEntry(entryForm.getValues());
                entryForm.dispose();
                break;
            case BookOverviewCommands.BTN_FUNC_BACK_TO_LIST:
                BookOverviewLogic.backToList();
                break;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            actionPerformed(new ActionEvent(new JPanel(),0,BookOverviewCommands.BTN_FUNC_EDIT_ENTRY));
        } else if(e.getClickCount()==1){
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
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
