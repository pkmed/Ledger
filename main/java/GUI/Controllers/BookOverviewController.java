package GUI.Controllers;

import GUI.Constants.BookOverviewCommands;
import GUI.Windows.EntryCreateForm;
import Logic.BookOverviewLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BookOverviewController implements ActionListener, MouseListener {

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
}
