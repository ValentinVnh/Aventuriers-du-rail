package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {

    private Label nomJoueur;
    private ChangeListener<IJoueur> listenerJoueurChange;
    private VBox destinationJoueurCourant;
    private VBox carteJoueurCourant;
    private VBox testVbox; // Was Added

    public VueJoueurCourant() {
        this.nomJoueur = new Label();
        this.carteJoueurCourant = new VBox();
        this.destinationJoueurCourant = new VBox();

        getChildren().add(nomJoueur);
        getChildren().add(carteJoueurCourant);
        getChildren().add(destinationJoueurCourant);

        carteJoueurCourant.setStyle("-fx-background-color: RED;");
        destinationJoueurCourant.setStyle("-fx-background-color: GREEN;");
    }

    public Label trouveLabelCarte(IJoueur i) {
        Label tlc = new Label();
        for (Node node : carteJoueurCourant.getChildren()) {
            tlc = (Label) node;
            if (tlc.equals(new Label(i.getNom()))) {
                break;
            }
        }
        return tlc;
    }

    public Label trouveLabelDest(IJoueur i) {
        Label tlc = new Label();
        for (Node node : destinationJoueurCourant.getChildren()) {
            tlc = (Label) node;
            if (tlc.equals(new Label(i.getNom()))) {
                break;
            }
        }
        return tlc;
    }


    public void creerBindings() {
        listenerJoueurChange = (observable, oldValue, newValue) -> {
            Label carteDuJC = new Label();
            Label destinationDuJC = new Label();

            Platform.runLater(() -> {
                nomJoueur.setText(newValue.getNom());

                carteDuJC.setText(newValue.getCartesWagon().toString());
                destinationDuJC.setText(newValue.getDestinations().toString());


                carteJoueurCourant.getChildren().remove(trouveLabelCarte(oldValue));
                carteJoueurCourant.getChildren().add(carteDuJC);

                destinationJoueurCourant.getChildren().remove(trouveLabelDest(oldValue));
                destinationJoueurCourant.getChildren().add(destinationDuJC);

                System.out.println(nomJoueur.getText() + " -> " + carteDuJC.getText() + " -> " + destinationDuJC.getText()); // à supprimer
            });

        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurChange);
    }
}

