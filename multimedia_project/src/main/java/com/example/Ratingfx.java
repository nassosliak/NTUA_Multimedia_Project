package com.example;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
public class Ratingfx extends HBox {
    private int maxRating;
    private int currentRating;

    public Ratingfx(int maxRating) {
        this.maxRating = maxRating;
        this.currentRating = 0;
        createStars();
    }

    private void createStars() {
        getChildren().clear();
        for (int i = 1; i <= maxRating; i++) {
            int rating =i;
            ImageView star = createStarImageView(i <= currentRating);
            star.setOnMouseClicked(event -> handleStarClick(rating));
            getChildren().add(star);
        }
    }

    private ImageView createStarImageView(boolean filled) {
        String starImage = filled ? "resources/star_FILL1_wght400_GRAD0_opsz24.png" : "resources/star_FILL0_wght200_GRAD0_opsz24.png";
        Image image = new Image(getClass().getResourceAsStream(starImage));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    
        return imageView;
    }
    

    private void handleStarClick(int clickedRating) {
        setRating(clickedRating);
    }

    public int getCurrentRating() {
        return currentRating;
    }

    public void setRating(int rating) {
        if (rating >= 0 && rating <= maxRating) {
            currentRating = rating;
            createStars();
        }
    }
}
