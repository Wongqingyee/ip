package TaskFlow;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Text dialog;
    @FXML
    private Circle displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setFill(new ImagePattern(img));
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * To set the text alignment for the user dialog box.
     *
     * @param pos The text alignment.
     */
    private void setTextAlignment(TextAlignment pos) {
        dialog.setTextAlignment(pos);
    }

    /**
     * To get the user dialog box.
     *
     * @param text The user input message.
     * @param img  The user image.
     * @return The dialog box.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var userDialog = new DialogBox(text, img);
        return userDialog;
    }

    /**
     * To get the chatbot dialog box.
     *
     * @param text The response to the user input.
     * @param img  The chatbot image.
     * @return The dialog box.
     */
    public static DialogBox getTaskieDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.setTextAlignment(TextAlignment.LEFT);
        return db;
    }
}
